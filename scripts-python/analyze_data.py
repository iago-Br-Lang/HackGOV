#!/usr/bin/env python3
"""Analisa os dados fictícios gerados por `generate_fake_data.py` e produz um
relatório com estatísticas e gráficos sobre os serviços simulados do HackGOV.

Uso:
    python generate_fake_data.py   # gera os CSVs em data/ (rodar uma vez antes)
    python analyze_data.py         # lê os CSVs e gera gráficos + relatório em output/
"""

from __future__ import annotations

from pathlib import Path

import matplotlib

matplotlib.use("Agg")  # gera imagens em arquivo, sem precisar de tela/display

import matplotlib.pyplot as plt
import pandas as pd

DATA_DIR = Path(__file__).parent / "data"
OUTPUT_DIR = Path(__file__).parent / "output"


def load_data() -> tuple[pd.DataFrame, pd.DataFrame, pd.DataFrame, pd.DataFrame]:
    required = ["citizens.csv", "service_requests.csv", "complaints.csv", "fraud_scores.csv"]
    missing = [f for f in required if not (DATA_DIR / f).exists()]
    if missing:
        raise FileNotFoundError(
            f"Arquivos não encontrados em {DATA_DIR}: {missing}.\n"
            "Rode primeiro: python generate_fake_data.py"
        )

    citizens = pd.read_csv(DATA_DIR / "citizens.csv")
    requests = pd.read_csv(DATA_DIR / "service_requests.csv", parse_dates=["data_solicitacao"])
    complaints = pd.read_csv(DATA_DIR / "complaints.csv")
    fraud_scores = pd.read_csv(DATA_DIR / "fraud_scores.csv")
    return citizens, requests, complaints, fraud_scores


def chart_avg_satisfaction_per_service(requests: pd.DataFrame) -> Path:
    avg = requests.groupby("servico_nome")["nota_satisfacao"].mean().sort_values()
    fig, ax = plt.subplots(figsize=(9, 5))
    avg.plot.barh(ax=ax, color="#1351b4")
    ax.set_xlabel("Nota média de satisfação (0-5)")
    ax.set_title("Satisfação média por serviço (dados fictícios)")
    ax.set_xlim(0, 5)
    fig.tight_layout()
    path = OUTPUT_DIR / "satisfacao_por_servico.png"
    fig.savefig(path, dpi=120)
    plt.close(fig)
    return path


def chart_requests_per_category(requests: pd.DataFrame) -> Path:
    counts = requests.groupby("categoria").size().sort_values()
    fig, ax = plt.subplots(figsize=(9, 5))
    counts.plot.barh(ax=ax, color="#168821")
    ax.set_xlabel("Total de solicitações")
    ax.set_title("Volume de solicitações por categoria (dados fictícios)")
    fig.tight_layout()
    path = OUTPUT_DIR / "solicitacoes_por_categoria.png"
    fig.savefig(path, dpi=120)
    plt.close(fig)
    return path


def chart_monthly_trend(requests: pd.DataFrame) -> Path:
    monthly = requests.set_index("data_solicitacao").resample("ME").size()
    fig, ax = plt.subplots(figsize=(9, 5))
    monthly.plot.line(ax=ax, marker="o", color="#c00")
    ax.set_ylabel("Solicitações no mês")
    ax.set_xlabel("Mês")
    ax.set_title("Tendência mensal de solicitações (dados fictícios)")
    fig.tight_layout()
    path = OUTPUT_DIR / "tendencia_mensal.png"
    fig.savefig(path, dpi=120)
    plt.close(fig)
    return path


def chart_fraud_score_distribution(fraud_scores: pd.DataFrame) -> Path:
    fig, ax = plt.subplots(figsize=(9, 5))
    fraud_scores["score"].plot.hist(ax=ax, bins=20, color="#8a1f11", edgecolor="white")
    ax.set_xlabel("Pontuação de risco (0-100)")
    ax.set_ylabel("Número de cidadãos")
    ax.set_title("Distribuição da pontuação antifraude (dados fictícios)")
    fig.tight_layout()
    path = OUTPUT_DIR / "distribuicao_fraude.png"
    fig.savefig(path, dpi=120)
    plt.close(fig)
    return path


def chart_risk_level_pie(fraud_scores: pd.DataFrame) -> Path:
    counts = fraud_scores["nivel_risco"].value_counts()
    fig, ax = plt.subplots(figsize=(6, 6))
    counts.plot.pie(ax=ax, autopct="%1.1f%%", colors=["#168821", "#ffcd07", "#f68a1e", "#8a1f11"])
    ax.set_ylabel("")
    ax.set_title("Distribuição por nível de risco (dados fictícios)")
    fig.tight_layout()
    path = OUTPUT_DIR / "niveis_de_risco.png"
    fig.savefig(path, dpi=120)
    plt.close(fig)
    return path


def build_report(
    citizens: pd.DataFrame,
    requests: pd.DataFrame,
    complaints: pd.DataFrame,
    fraud_scores: pd.DataFrame,
    charts: dict[str, Path],
) -> str:
    per_service = (
        requests.groupby("servico_nome")
        .agg(
            total_solicitacoes=("id", "count"),
            nota_media=("nota_satisfacao", "mean"),
            prazo_medio_dias=("dias_para_concluir", "mean"),
            pct_concluido=("status", lambda s: (s == "Concluído").mean() * 100),
        )
        .round(2)
        .sort_values("total_solicitacoes", ascending=False)
    )

    top_complaint_reasons = complaints["motivo"].value_counts().head(5)
    top_complaint_reasons.index.name = "motivo"
    top_complaint_reasons.name = "total"

    complaints_per_service = (
        complaints.groupby("servico_slug").size().sort_values(ascending=False)
    )
    complaints_per_service.name = "total_reclamacoes"

    avg_score_per_level = citizens.merge(fraud_scores, on="cpf").groupby("nivel_conta")["score"].mean().round(1)

    lines = [
        "# Relatório de análise de dados fictícios — HackGOV",
        "",
        "> Dados 100% simulados, gerados por `generate_fake_data.py` a partir do catálogo",
        "> fictício de serviços do projeto HackGOV. Não representam cidadãos ou eventos reais.",
        "",
        "## Visão geral",
        "",
        f"- Cidadãos fictícios: **{len(citizens)}**",
        f"- Solicitações de serviço: **{len(requests)}**",
        f"- Reclamações: **{len(complaints)}**",
        f"- Pontuações de risco antifraude: **{len(fraud_scores)}**",
        "",
        "## Desempenho por serviço",
        "",
        per_service.to_markdown(),
        "",
        f"![Satisfação por serviço]({charts['satisfaction'].name})",
        "",
        f"![Solicitações por categoria]({charts['category'].name})",
        "",
        "## Tendência de uso ao longo do tempo",
        "",
        f"![Tendência mensal]({charts['trend'].name})",
        "",
        "## Reclamações",
        "",
        "Principais motivos de reclamação:",
        "",
        top_complaint_reasons.to_markdown(),
        "",
        "Reclamações por serviço:",
        "",
        complaints_per_service.to_markdown(),
        "",
        "## Antifraude",
        "",
        f"- Pontuação média de risco: **{fraud_scores['score'].mean():.1f}** (0-100)",
        f"- Mediana: **{fraud_scores['score'].median():.1f}**",
        "",
        "Distribuição por nível de conta gov.br (pontuação média de risco):",
        "",
        avg_score_per_level.to_markdown(),
        "",
        f"![Distribuição da pontuação antifraude]({charts['fraud_dist'].name})",
        "",
        f"![Níveis de risco]({charts['risk_pie'].name})",
        "",
    ]
    return "\n".join(lines)


def main() -> None:
    OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
    citizens, requests, complaints, fraud_scores = load_data()

    print("Gerando gráficos...")
    charts = {
        "satisfaction": chart_avg_satisfaction_per_service(requests),
        "category": chart_requests_per_category(requests),
        "trend": chart_monthly_trend(requests),
        "fraud_dist": chart_fraud_score_distribution(fraud_scores),
        "risk_pie": chart_risk_level_pie(fraud_scores),
    }

    print("Montando relatório...")
    report = build_report(citizens, requests, complaints, fraud_scores, charts)
    report_path = OUTPUT_DIR / "relatorio.md"
    report_path.write_text(report, encoding="utf-8")

    print(f"\nGráficos e relatório salvos em: {OUTPUT_DIR}")
    for chart_path in charts.values():
        print(f"  {chart_path.name}")
    print(f"  {report_path.name}")


if __name__ == "__main__":
    main()
