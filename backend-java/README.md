# HackGOV API — Backend em Java (Spring Boot)

API REST fictícia, escrita em Java com Spring Boot 3 / Maven, que expõe os mesmos
dados de demonstração usados pelo front-end em Rust/Dioxus do HackGOV
([`../src/data.rs`](../src/data.rs)). Este backend é **independente** do front-end
Rust — ele não é chamado por ele automaticamente — e serve como uma segunda
implementação, em Java, do mesmo catálogo fictício de serviços do gov.br.

> ⚠️ Todos os dados aqui são **fictícios**: não existe integração real com nenhum
> sistema do governo. É um projeto de demonstração/hackathon.

## Pré-requisitos

- JDK 25+ (testado com Eclipse Temurin 25)
- Maven 3.9+ (ou use o wrapper, se adicionado)

## Rodando localmente

```powershell
cd backend-java
mvn spring-boot:run
```

A API sobe em `http://localhost:8081` (porta diferente da usada pelo `dx serve`
do front-end, para permitir rodar os dois ao mesmo tempo).

## Rodando os testes

```powershell
mvn test
```

## Endpoints disponíveis

| Método | Rota                              | Descrição                                                             |
|--------|-----------------------------------|------------------------------------------------------------------------|
| GET    | `/api/services`                   | Lista todos os serviços fictícios                                      |
| GET    | `/api/services/{slug}`            | Detalhe de um serviço por slug (404 se não existir)                    |
| GET    | `/api/services/search?q=texto`    | Busca por texto livre usando as mesmas palavras-chave do assistente     |
| GET    | `/api/categories`                 | Lista de categorias de serviços                                        |
| GET    | `/api/alerts`                     | Alertas simulados do cidadão (prazos, pendências, valores disponíveis) |
| GET    | `/api/my-gov/life-areas`          | Painel "Minha vida no governo"                                         |
| GET    | `/api/attendance-points`          | Postos de atendimento presencial simulados                             |
| GET    | `/api/help-reasons`               | Motivos de bloqueio do botão "Não consigo resolver" e suas soluções    |
| GET    | `/api/fraud/risk`                 | Pontuação de risco antifraude simulada + sinais avaliados               |
| GET    | `/api/fraud/signals`              | Apenas os sinais individuais do antifraude                              |
| GET    | `/api/status`                     | Indicadores de status/transparência da plataforma                      |

## Estrutura

```
backend-java/
├── pom.xml
└── src/
    ├── main/java/com/hackgov/api/
    │   ├── HackGovApiApplication.java   # ponto de entrada
    │   ├── model/                       # DTOs (Service, Category, Alert, ...)
    │   ├── data/FictitiousDataStore.java # dados fictícios (espelha data.rs)
    │   ├── controller/                  # controllers REST por domínio
    │   └── config/CorsConfig.java       # libera CORS para o front-end
    └── test/java/com/hackgov/api/
        └── HackGovApiApplicationTests.java
```
