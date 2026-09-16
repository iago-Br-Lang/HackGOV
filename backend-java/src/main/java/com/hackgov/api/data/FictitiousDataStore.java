package com.hackgov.api.data;

import com.hackgov.api.model.Alert;
import com.hackgov.api.model.AlertLevel;
import com.hackgov.api.model.AttendancePoint;
import com.hackgov.api.model.Category;
import com.hackgov.api.model.FraudRiskLevel;
import com.hackgov.api.model.FraudSignal;
import com.hackgov.api.model.HelpReason;
import com.hackgov.api.model.LifeArea;
import com.hackgov.api.model.LifeState;
import com.hackgov.api.model.Service;
import com.hackgov.api.model.StatusIndicator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Fonte única de dados fictícios/simulados do HackGOV, espelhando
 * {@code src/data.rs} do front-end em Rust. Nenhum dado aqui é real: não existe
 * integração com sistemas do governo, banco de dados ou serviços externos.
 */
@Component
public class FictitiousDataStore {

    public List<Service> services() {
        return Arrays.asList(
                new Service(
                        "assinatura-eletronica", "✍️", "Identidade Digital", "Assinatura Eletrônica",
                        "~5 min",
                        "Assine documentos oficiais com validade jurídica usando sua conta gov.br, sem precisar imprimir ou reconhecer firma.",
                        new String[]{"assinar documento", "assinatura digital", "assinar contrato", "assinar pdf"},
                        new String[]{"Conta gov.br ativa", "Documento em PDF para assinar"},
                        false,
                        "É como assinar um papel, mas no celular ou computador. Vale legalmente, sem precisar imprimir nada.",
                        4.6f, 1, 320, 96, "Arquivo PDF inválido ou corrompido"),
                new Service(
                        "consultar-imposto-renda", "💰", "Finanças e Impostos", "Consultar Imposto de Renda",
                        "~3 min",
                        "Veja o andamento da sua declaração, mensagens da Receita Federal e eventuais pendências.",
                        new String[]{"imposto de renda", "declaração", "receita federal", "situação da declaração"},
                        new String[]{"Conta gov.br ativa", "CPF regularizado"},
                        false,
                        "Veja se está tudo certo com o Imposto de Renda que você já entregou.",
                        4.3f, 1, 890, 94, "Lentidão no sistema em época de pico"),
                new Service(
                        "entregar-imposto-renda", "📄", "Finanças e Impostos", "Entregar Imposto de Renda",
                        "~20 min",
                        "Preencha e envie sua declaração anual do Imposto de Renda de Pessoa Física diretamente pela plataforma.",
                        new String[]{"declarar imposto de renda", "entregar declaração", "declaração anual", "ir 2026"},
                        new String[]{"Conta gov.br nível Prata ou Ouro", "Informe de rendimentos", "CPF regularizado"},
                        false,
                        "Envie ao governo quanto você ganhou e gastou no ano, para saber se recebe dinheiro de volta ou se deve pagar mais.",
                        3.9f, 3, 2100, 88, "Dificuldade para preencher os rendimentos"),
                new Service(
                        "consultar-restituicao", "🧾", "Finanças e Impostos", "Consultar Restituição",
                        "~2 min",
                        "Consulte o valor e a data de pagamento da sua restituição do Imposto de Renda.",
                        new String[]{"restituição", "restituição de imposto", "quando vou receber", "extrato do ir"},
                        new String[]{"Conta gov.br ativa", "CPF regularizado"},
                        false,
                        "Veja se o governo vai te devolver dinheiro do Imposto de Renda, e quando.",
                        4.5f, 1, 410, 97, "Valor divergente do esperado"),
                new Service(
                        "carteira-trabalho", "🪪", "Trabalho e Previdência", "Carteira de Trabalho Digital",
                        "~10 min",
                        "Acesse seu histórico profissional, contratos de trabalho e solicite a emissão da carteira digital.",
                        new String[]{"carteira de trabalho", "ctps", "carteira digital de trabalho", "vínculos empregatícios"},
                        new String[]{"Conta gov.br nível Prata ou Ouro", "Verificação facial (selfie)"},
                        true,
                        "Veja seus empregos registrados e tire uma carteira de trabalho digital, sem precisar ir a um posto.",
                        3.6f, 5, 3400, 79, "Verificação facial não reconhece o rosto"),
                new Service(
                        "passaporte", "🛂", "Viagens e Turismo", "Solicitar Passaporte",
                        "~15 min",
                        "Agende o atendimento, pague a taxa e acompanhe o andamento da emissão do seu passaporte.",
                        new String[]{"passaporte", "tirar passaporte", "viajar para fora", "viagem internacional"},
                        new String[]{"Conta gov.br ativa", "CPF regularizado", "Pagamento da taxa (GRU)"},
                        false,
                        "Documento para viajar para fora do Brasil. Aqui você paga a taxa e marca o dia para tirar a foto e as digitais.",
                        3.8f, 12, 5200, 74, "Demora para conseguir horário de atendimento"),
                new Service(
                        "cnh-digital", "🚗", "Trânsito", "CNH Digital e Segunda Via",
                        "~8 min",
                        "Consulte sua CNH digital, solicite a segunda via em caso de perda ou roubo, ou verifique a situação da sua habilitação.",
                        new String[]{"carteira de motorista", "cnh", "carteira de habilitação", "segunda via",
                                "perdi minha carteira", "perdi minha cnh", "habilitação"},
                        new String[]{"Conta gov.br nível Prata ou Ouro", "CPF regularizado", "Verificação facial (selfie)"},
                        true,
                        "Sua carteira de motorista no celular. Se perdeu a de papel ou plástico, peça uma segunda via aqui.",
                        3.7f, 8, 4100, 81, "Verificação facial não reconhece o rosto")
        );
    }

    public Optional<Service> serviceBySlug(String slug) {
        return services().stream()
                .filter(s -> s.getSlug().equalsIgnoreCase(slug))
                .findFirst();
    }

    /**
     * Reconhecimento simples por palavras-chave (sem backend/LLM/IA), igual ao usado
     * pelo assistente do front-end: compara o texto livre com as keywords de cada
     * serviço. Keywords de uma palavra só exigem correspondência exata de palavra;
     * keywords com espaço (frases) usam correspondência por substring.
     */
    public List<Service> matchServices(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }
        String q = query.trim().toLowerCase(Locale.ROOT);
        List<String> words = Arrays.asList(q.split("\\s+"));

        return services().stream()
                .filter(s -> Arrays.stream(s.getKeywords()).anyMatch(k -> {
                    if (k.contains(" ")) {
                        return q.contains(k);
                    }
                    return words.contains(k);
                }))
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Category> categories() {
        return Arrays.asList(
                new Category("🌾", "Agricultura e Pecuária"),
                new Category("🤝", "Assistência Social"),
                new Category("🔬", "Ciência e Tecnologia"),
                new Category("🎓", "Educação e Pesquisa"),
                new Category("🏥", "Saúde e Vigilância Sanitária"),
                new Category("⚖️", "Justiça e Segurança"),
                new Category("🏗️", "Infraestrutura e Trânsito"),
                new Category("🌎", "Meio Ambiente e Clima"),
                new Category("💼", "Trabalho e Previdência"),
                new Category("🏢", "Empresa, Indústria e Comércio"),
                new Category("✈️", "Viagens e Turismo"),
                new Category("🎭", "Cultura, Artes e Esportes")
        );
    }

    public List<Alert> alerts() {
        return Arrays.asList(
                new Alert("⚠️", "Sua CNH vence em 30 dias",
                        "Renove agora para evitar multa e pontos na carteira.", AlertLevel.URGENT),
                new Alert("📋", "Existe uma pendência no seu CPF",
                        "Regularize para não perder acesso a outros serviços.", AlertLevel.WARNING),
                new Alert("💰", "Você pode ter direito a uma restituição",
                        "Consulte o valor disponível do seu Imposto de Renda.", AlertLevel.INFO),
                new Alert("📅", "O prazo da declaração termina em 5 dias",
                        "Entregue o Imposto de Renda até 31/05 para evitar multa.", AlertLevel.WARNING)
        );
    }

    public List<LifeArea> lifeAreas() {
        return Arrays.asList(
                new LifeArea("🪪", "Documentos", "CPF e identidade regulares", LifeState.OK, null),
                new LifeArea("🚗", "CNH e Veículos", "CNH vence em 30 dias", LifeState.ATTENTION, "cnh-digital"),
                new LifeArea("💰", "Impostos", "Declaração de 2026 ainda pendente", LifeState.PENDING, "entregar-imposto-renda"),
                new LifeArea("🧾", "Restituição", "R$ 1.240,00 disponível para saque", LifeState.ATTENTION, "consultar-restituicao"),
                new LifeArea("💼", "Trabalho", "3 vínculos empregatícios registrados", LifeState.OK, "carteira-trabalho"),
                new LifeArea("🏛️", "Benefícios", "Nenhum benefício ativo no momento", LifeState.OK, null),
                new LifeArea("🗳️", "Situação Eleitoral", "Título regular e em dia", LifeState.OK, null),
                new LifeArea("🏥", "Saúde", "Cartão do SUS ativo", LifeState.OK, null),
                new LifeArea("🎓", "Educação", "Nenhum registro acadêmico vinculado", LifeState.OK, null),
                new LifeArea("🛂", "Passaporte", "Nenhum passaporte emitido", LifeState.OK, "passaporte"),
                new LifeArea("✍️", "Assinaturas", "2 documentos assinados este ano", LifeState.OK, "assinatura-eletronica"),
                new LifeArea("👴", "Aposentadoria", "Simulação de tempo de contribuição disponível", LifeState.OK, null)
        );
    }

    public List<AttendancePoint> attendancePoints() {
        return Arrays.asList(
                new AttendancePoint("Unidade de Atendimento gov.br — Centro", "Praça da Sé, 100 — Centro",
                        "Seg. a sex., 8h às 17h", 1.2f, true,
                        new String[]{"CNH", "Passaporte", "Carteira de Trabalho"}),
                new AttendancePoint("Poupatempo — Zona Norte", "Av. Norte, 850 — Zona Norte",
                        "Seg. a sáb., 7h às 19h", 4.7f, true,
                        new String[]{"CNH", "Documentos", "Assinatura de documentos"}),
                new AttendancePoint("Correios — Agência Jardim das Flores", "Rua das Flores, 45 — Jardim das Flores",
                        "Seg. a sex., 9h às 18h", 2.5f, false,
                        new String[]{"Passaporte", "Reconhecimento de firma"})
        );
    }

    public List<HelpReason> helpReasons() {
        return Arrays.asList(
                new HelpReason("🔑", "Não consigo entrar",
                        "Confira se está digitando o CPF correto. Se o problema continuar, use \"Esqueci minha senha\" na tela de login."),
                new HelpReason("🔒", "Esqueci minha senha",
                        "Clique em \"Esqueci minha senha\" na tela de login e siga a verificação por e-mail, SMS ou banco credenciado."),
                new HelpReason("🤳", "Reconhecimento facial falhou",
                        "Você pode confirmar sua identidade por banco credenciado, e-mail, telefone ou atendimento presencial — sem precisar da selfie."),
                new HelpReason("📄", "Não tenho o documento pedido",
                        "Veja abaixo o posto de atendimento mais próximo para emitir o documento, ou continue depois: seus dados ficam salvos."),
                new HelpReason("❓", "Não entendi a etapa",
                        "Ative o botão \"Linguagem simples\" na página do serviço para uma explicação mais direta, sem termos técnicos."),
                new HelpReason("🐛", "O sistema apresentou erro",
                        "Tente novamente em alguns minutos. Se o erro continuar, abra um chamado na Central de Ajuda com o horário em que ocorreu."),
                new HelpReason("⏳", "Meu pedido está parado",
                        "Consulte o tempo médio de conclusão na página do serviço. Se já passou do prazo, você pode registrar uma reclamação formal."),
                new HelpReason("🗣️", "Preciso falar com uma pessoa",
                        "Ligue para a Central 0800 000 0000 ou procure um dos postos de atendimento presencial listados abaixo.")
        );
    }

    /**
     * Pontuação de risco simulada (0-100), combinando vários sinais independentes —
     * nunca uma regra única decide sozinha se a conta é suspeita.
     */
    public int fraudRiskScore() {
        return 34;
    }

    /** Classifica a pontuação em uma faixa de ação, como em um sistema antifraude real. */
    public FraudRiskLevel fraudLevel(int score) {
        if (score <= 30) {
            return FraudRiskLevel.NORMAL;
        } else if (score <= 60) {
            return FraudRiskLevel.MONITORING;
        } else if (score <= 80) {
            return FraudRiskLevel.VERIFICATION;
        }
        return FraudRiskLevel.BLOCKED;
    }

    public List<FraudSignal> fraudSignals() {
        return Arrays.asList(
                new FraudSignal("Comportamento de navegação",
                        "Padrão de uso compatível com seu histórico nos últimos 90 dias.", true),
                new FraudSignal("Tentativas de burlar regras",
                        "Nenhuma tentativa de contornar verificação de identidade detectada.", true),
                new FraudSignal("Velocidade das ações",
                        "Ritmo de preenchimento mais rápido que o normal nesta sessão.", false),
                new FraudSignal("Múltiplas contas",
                        "Apenas 1 conta gov.br associada a este CPF.", true),
                new FraudSignal("Consistência dos dados",
                        "Nenhuma divergência entre dados cadastrais e uso da conta.", true),
                new FraudSignal("Histórico de abuso",
                        "Nenhum registro de bloqueio ou penalidade anterior.", true),
                new FraudSignal("Sinais de automação",
                        "Nenhum padrão de acesso automatizado (bot) identificado.", true)
        );
    }

    public List<StatusIndicator> statusIndicators() {
        return Arrays.asList(
                new StatusIndicator("🟢", "Disponibilidade da plataforma", "99.98% nos últimos 30 dias"),
                new StatusIndicator("🛰️", "Incidentes de segurança", "0 nos últimos 90 dias"),
                new StatusIndicator("🔐", "Certificação", "TLS 1.3 + criptografia AES-256"),
                new StatusIndicator("📋", "Auditoria externa", "Última verificação: julho/2026")
        );
    }
}
