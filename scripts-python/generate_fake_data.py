#!/usr/bin/env python3
"""Gera dados fictícios (cidadãos, solicitações de serviço, reclamações e
pontuações de risco antifraude) para o projeto HackGOV.

Os dados são 100% simulados e usam o catálogo de serviços fictício definido em
`hackgov_data/catalog.py` (o mesmo catálogo usado pelo front-end Rust e pelo
backend Java) como base estatística para gerar registros individuais
plausíveis: nenhum CPF, nome ou reclamação aqui corresponde a uma pessoa ou
evento real.

Uso:
    python generate_fake_data.py --citizens 800 --seed 42
"""

from __future__ import annotations

import argparse
import random
from datetime import datetime, timedelta
from pathlib import Path

import pandas as pd
from faker import Faker

from hackgov_data.catalog import (
    ACCOUNT_LEVELS,
    ALT_COMPLAINT_REASONS,
    SERVICES,
)

OUTPUT_DIR = Path(__file__).parent / "data"


def generate_citizens(fake: Faker, n: int) -> pd.DataFrame:
    """Gera cidadãos fictícios com CPF, nome, cidade/estado e nível de conta gov.br."""
    rows = []
    for _ in range(n):
        rows.append(
            {
                "cpf": fake.cpf(),
                "nome": fake.name(),
                "data_nascimento": fake.date_of_birth(minimum_age=18, maximum_age=85),
                "cidade": fake.city(),
                "estado": fake.estado_sigla(),
                "nivel_conta": random.choices(
                    ACCOUNT_LEVELS, weights=[0.35, 0.4, 0.25], k=1
                )[0],
                "email": fake.email(),
            }
        )
    return pd.DataFrame(rows)


def _pick_status(resolved_pct: int) -> str:
    """Sorteia o status de uma solicitação, respeitando o `resolved_pct` do serviço."""
    roll = random.random() * 100
    if roll < resolved_pct:
        return "Concluído"
    # o restante se divide entre "em andamento" (ainda dentro do prazo) e "atrasado"
    return random.choices(["Em andamento", "Atrasado"], weights=[0.55, 0.45], k=1)[0]


def generate_service_requests(fake: Faker, citizens: pd.DataFrame) -> pd.DataFrame:
    """Gera solicitações de serviço para cada cidadão, com métricas plausíveis
    a partir das estatísticas fictícias de cada serviço (nota, prazo, % resolvido)."""
    rows = []
    request_id = 1
    today = datetime.now()

    for _, citizen in citizens.iterrows():
        n_requests = random.randint(1, 4)
        chosen = random.sample(SERVICES, k=min(n_requests, len(SERVICES)))
        for service in chosen:
            days_ago = random.randint(1, 365)
            request_date = today - timedelta(days=days_ago)

            duration = max(1, round(random.gauss(service.avg_days, service.avg_days * 0.4 + 0.5)))
            satisfaction = min(5.0, max(1.0, round(random.gauss(service.rating, 0.6), 1)))
            status = _pick_status(service.resolved_pct)

            facial_check_failed = False
            if service.needs_biometrics:
                facial_check_failed = random.random() < 0.15

            rows.append(
                {
                    "id": request_id,
                    "cpf_cidadao": citizen["cpf"],
                    "servico_slug": service.slug,
                    "servico_nome": service.name,
                    "categoria": service.tag,
                    "data_solicitacao": request_date.date().isoformat(),
                    "dias_para_concluir": duration,
                    "status": status,
                    "nota_satisfacao": satisfaction,
                    "verificacao_facial_falhou": facial_check_failed,
                }
            )
            request_id += 1

    return pd.DataFrame(rows)


def generate_complaints(requests: pd.DataFrame) -> pd.DataFrame:
    """Gera reclamações a partir de solicitações malsucedidas ou com nota baixa."""
    rows = []
    complaint_id = 1

    candidates = requests[(requests["status"] != "Concluído") | (requests["nota_satisfacao"] <= 3)]
    for _, req in candidates.iterrows():
        if random.random() > 0.6:
            continue  # nem toda solicitação insatisfatória vira reclamação formal

        from hackgov_data.catalog import SERVICES_BY_SLUG

        service = SERVICES_BY_SLUG[req["servico_slug"]]
        reason = service.main_issue if random.random() < 0.7 else random.choice(ALT_COMPLAINT_REASONS)
        complaint_date = datetime.fromisoformat(req["data_solicitacao"]) + timedelta(
            days=random.randint(0, 5)
        )

        rows.append(
            {
                "id": complaint_id,
                "request_id": req["id"],
                "servico_slug": req["servico_slug"],
                "data": complaint_date.date().isoformat(),
                "motivo": reason,
                "resolvida": random.random() < 0.7,
            }
        )
        complaint_id += 1

    return pd.DataFrame(rows)


def generate_fraud_scores(citizens: pd.DataFrame) -> pd.DataFrame:
    """Gera uma pontuação de risco antifraude (0-100) por cidadão, com distribuição
    concentrada em valores baixos (a maioria dos cidadãos é "normal"), como esperado
    de um sistema antifraude real com poucos falsos positivos."""
    rows = []
    for _, citizen in citizens.iterrows():
        score = min(100, max(0, round(random.betavariate(2, 6) * 100)))

        if score <= 30:
            level = "NORMAL"
        elif score <= 60:
            level = "MONITORING"
        elif score <= 80:
            level = "VERIFICATION"
        else:
            level = "BLOCKED"

        possible_signals = [
            "velocidade_das_acoes",
            "multiplas_contas",
            "inconsistencia_de_dados",
            "sinais_de_automacao",
        ]
        n_signals = 0 if score <= 30 else random.randint(1, min(3, len(possible_signals)))
        triggered = random.sample(possible_signals, k=n_signals)

        rows.append(
            {
                "cpf": citizen["cpf"],
                "score": score,
                "nivel_risco": level,
                "sinais_alerta": ";".join(triggered),
            }
        )
    return pd.DataFrame(rows)


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--citizens", type=int, default=800, help="Número de cidadãos fictícios a gerar")
    parser.add_argument("--seed", type=int, default=42, help="Seed para reprodutibilidade")
    parser.add_argument("--outdir", type=Path, default=OUTPUT_DIR, help="Pasta de saída dos CSVs")
    args = parser.parse_args()

    random.seed(args.seed)
    fake = Faker("pt_BR")
    Faker.seed(args.seed)

    args.outdir.mkdir(parents=True, exist_ok=True)

    print(f"Gerando {args.citizens} cidadãos fictícios...")
    citizens = generate_citizens(fake, args.citizens)

    print("Gerando solicitações de serviço fictícias...")
    requests = generate_service_requests(fake, citizens)

    print("Gerando reclamações fictícias...")
    complaints = generate_complaints(requests)

    print("Gerando pontuações de risco antifraude fictícias...")
    fraud_scores = generate_fraud_scores(citizens)

    citizens.to_csv(args.outdir / "citizens.csv", index=False)
    requests.to_csv(args.outdir / "service_requests.csv", index=False)
    complaints.to_csv(args.outdir / "complaints.csv", index=False)
    fraud_scores.to_csv(args.outdir / "fraud_scores.csv", index=False)

    print(f"\nArquivos gerados em: {args.outdir}")
    print(f"  citizens.csv          -> {len(citizens)} linhas")
    print(f"  service_requests.csv  -> {len(requests)} linhas")
    print(f"  complaints.csv        -> {len(complaints)} linhas")
    print(f"  fraud_scores.csv      -> {len(fraud_scores)} linhas")


if __name__ == "__main__":
    main()
