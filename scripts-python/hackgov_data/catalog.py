"""Catálogo fictício de serviços do HackGOV.

Espelha os dados de `src/data.rs` (front-end Rust) e do backend Java em
`backend-java/.../FictitiousDataStore.java`, para que os três "lados" do
projeto usem exatamente os mesmos serviços/categorias fictícios como base.

Nenhum dado aqui é real: nomes, notas, prazos e reclamações são todos
simulados para fins de demonstração/hackathon.
"""

from __future__ import annotations

from dataclasses import dataclass


@dataclass(frozen=True)
class ServiceInfo:
    slug: str
    tag: str
    name: str
    avg_days: int
    rating: float
    complaints: int
    resolved_pct: int
    main_issue: str
    needs_biometrics: bool


SERVICES: list[ServiceInfo] = [
    ServiceInfo(
        slug="assinatura-eletronica",
        tag="Identidade Digital",
        name="Assinatura Eletrônica",
        avg_days=1,
        rating=4.6,
        complaints=320,
        resolved_pct=96,
        main_issue="Arquivo PDF inválido ou corrompido",
        needs_biometrics=False,
    ),
    ServiceInfo(
        slug="consultar-imposto-renda",
        tag="Finanças e Impostos",
        name="Consultar Imposto de Renda",
        avg_days=1,
        rating=4.3,
        complaints=890,
        resolved_pct=94,
        main_issue="Lentidão no sistema em época de pico",
        needs_biometrics=False,
    ),
    ServiceInfo(
        slug="entregar-imposto-renda",
        tag="Finanças e Impostos",
        name="Entregar Imposto de Renda",
        avg_days=3,
        rating=3.9,
        complaints=2100,
        resolved_pct=88,
        main_issue="Dificuldade para preencher os rendimentos",
        needs_biometrics=False,
    ),
    ServiceInfo(
        slug="consultar-restituicao",
        tag="Finanças e Impostos",
        name="Consultar Restituição",
        avg_days=1,
        rating=4.5,
        complaints=410,
        resolved_pct=97,
        main_issue="Valor divergente do esperado",
        needs_biometrics=False,
    ),
    ServiceInfo(
        slug="carteira-trabalho",
        tag="Trabalho e Previdência",
        name="Carteira de Trabalho Digital",
        avg_days=5,
        rating=3.6,
        complaints=3400,
        resolved_pct=79,
        main_issue="Verificação facial não reconhece o rosto",
        needs_biometrics=True,
    ),
    ServiceInfo(
        slug="passaporte",
        tag="Viagens e Turismo",
        name="Solicitar Passaporte",
        avg_days=12,
        rating=3.8,
        complaints=5200,
        resolved_pct=74,
        main_issue="Demora para conseguir horário de atendimento",
        needs_biometrics=False,
    ),
    ServiceInfo(
        slug="cnh-digital",
        tag="Trânsito",
        name="CNH Digital e Segunda Via",
        avg_days=8,
        rating=3.7,
        complaints=4100,
        resolved_pct=81,
        main_issue="Verificação facial não reconhece o rosto",
        needs_biometrics=True,
    ),
]

SERVICES_BY_SLUG: dict[str, ServiceInfo] = {s.slug: s for s in SERVICES}

# Motivos alternativos de reclamação, usados para variar as reclamações geradas
# além do "principal problema" de cada serviço (deixa os dados mais realistas).
ALT_COMPLAINT_REASONS: list[str] = [
    "Atendimento demorou mais que o previsto",
    "Aplicativo travou durante o preenchimento",
    "Falta de clareza nas instruções",
    "Documento pedido não estava na lista de requisitos",
    "Erro ao anexar arquivos",
    "Notificação de status não chegou",
]

ACCOUNT_LEVELS: list[str] = ["Bronze", "Prata", "Ouro"]

BRAZILIAN_STATES: list[str] = [
    "SP", "RJ", "MG", "BA", "PR", "RS", "PE", "CE", "PA", "SC",
    "GO", "MA", "AM", "ES", "PB", "MT", "DF",
]
