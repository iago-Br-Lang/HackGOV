# HackGOV — Scripts Python (dados fictícios + análise)

Scripts em Python que geram uma base de dados fictícia (cidadãos, solicitações de
serviço, reclamações e pontuações de risco antifraude) a partir do mesmo catálogo
de serviços usado pelo front-end Rust ([`../src/data.rs`](../src/data.rs)) e pelo
backend Java ([`../backend-java`](../backend-java)), e depois produzem estatísticas
e gráficos sobre esses dados.

> ⚠️ Todos os dados gerados são **fictícios** (via [Faker](https://faker.readthedocs.io/)):
> nenhum CPF, nome, reclamação ou pontuação de risco corresponde a uma pessoa ou
> evento real.

## Pré-requisitos

- Python 3.10+

## Instalação

```powershell
cd scripts-python
pip install -r requirements.txt
```

## Uso

1. Gerar os dados fictícios (CSV em `data/`):

   ```powershell
   python generate_fake_data.py --citizens 800 --seed 42
   ```

   Parâmetros opcionais:
   - `--citizens N`: quantidade de cidadãos fictícios (padrão: 800)
   - `--seed N`: seed para reprodutibilidade (padrão: 42)
   - `--outdir PATH`: pasta de saída dos CSVs (padrão: `data/`)

2. Analisar os dados e gerar gráficos + relatório (em `output/`):

   ```powershell
   python analyze_data.py
   ```

## Saídas

- `data/citizens.csv` — cidadãos fictícios (CPF, nome, cidade, nível de conta gov.br...)
- `data/service_requests.csv` — solicitações de serviço, com prazo e nota de satisfação
- `data/complaints.csv` — reclamações, derivadas de solicitações malsucedidas
- `data/fraud_scores.csv` — pontuação de risco antifraude (0-100) por cidadão
- `output/relatorio.md` — relatório com tabelas e estatísticas
- `output/*.png` — gráficos (satisfação por serviço, volume por categoria,
  tendência mensal, distribuição de risco antifraude, níveis de risco)

## Estrutura

```
scripts-python/
├── requirements.txt
├── hackgov_data/
│   └── catalog.py          # catálogo fictício de serviços (espelha data.rs)
├── generate_fake_data.py   # gera os CSVs fictícios
├── analyze_data.py         # lê os CSVs e gera gráficos + relatório
├── data/                   # CSVs gerados (não versionar dados grandes)
└── output/                 # gráficos e relatório gerados
```
