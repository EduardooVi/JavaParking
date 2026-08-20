import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Registra entrada, saída e cobrança de um veículo no estacionamento.
 * Responsabilidade (Atividade 3): guardar horaEntrada, horaSaida, valor e
 * status, e calcular o valor final combinando tarifa por tipo de veículo
 * COM o tempo em que o veículo ficou estacionado (alteração pedida na
 * Atividade 6 - Desafio de compreensão).
 */
public class Ticket {

    // Tarifa por hora, de acordo com o tipo do veículo
    private static final double TARIFA_HORA_MOTO = 5.00;
    private static final double TARIFA_HORA_CARRO = 8.00;
    private static final double TARIFA_HORA_CAMINHONETE = 12.00;

    private Veiculo veiculo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private double valor;
    private String status; // "ABERTO" enquanto o veículo está estacionado, "FECHADO" após o pagamento

    public Ticket(Veiculo veiculo) {
        this(veiculo, LocalDateTime.now());
    }

    /**
     * Construtor auxiliar que permite informar uma hora de entrada específica.
     * Útil para simular/testar diferentes tempos de permanência sem precisar
     * esperar o tempo real passar.
     */
    public Ticket(Veiculo veiculo, LocalDateTime horaEntrada) {
        validarVeiculo(veiculo);
        this.veiculo = veiculo;
        this.horaEntrada = horaEntrada;
        this.horaSaida = null;
        this.valor = 0.0;
        this.status = "ABERTO";
    }

    // Validação da entrada do veículo: garante dados mínimos consistentes
    // antes de gerar o ticket, lançando exceção em vez de criar um ticket inválido.
    private void validarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo.");
        }
        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank()) {
            throw new IllegalArgumentException("Placa inválida: não pode ser vazia.");
        }
        if (veiculo.getTipo() == null) {
            throw new IllegalArgumentException("Tipo de veículo não informado.");
        }
    }

    // Tarifa por hora de acordo com o tipo do veículo
    private double tarifaPorTipo(TipoVeiculo tipo) {
        switch (tipo) {
            case MOTO:
                return TARIFA_HORA_MOTO;
            case CARRO:
                return TARIFA_HORA_CARRO;
            case CAMINHONETE:
                return TARIFA_HORA_CAMINHONETE;
            default:
                throw new IllegalStateException("Tipo de veículo sem tarifa definida: " + tipo);
        }
    }

    /**
     * Calcula o valor a cobrar com base no tempo estacionado (arredondado
     * para cima, hora cheia, mínimo de 1 hora) multiplicado pela tarifa do
     * tipo do veículo.
     */
    private double calcularValor(LocalDateTime momentoSaida) {
        long minutos = Duration.between(horaEntrada, momentoSaida).toMinutes();
        long horas = (long) Math.ceil(minutos / 60.0);
        if (horas < 1) {
            horas = 1; // cobrança mínima de 1 hora
        }
        return horas * tarifaPorTipo(veiculo.getTipo());
    }

    /**
     * Fecha o ticket: registra a hora de saída, calcula o valor final e
     * muda o status para FECHADO. Retorna o valor a ser cobrado.
     */
    public double fecharTicket() {
        return fecharTicket(LocalDateTime.now());
    }

    /** Versão que aceita um horário de saída específico (para testes/demonstração). */
    public double fecharTicket(LocalDateTime momentoSaida) {
        if (status.equals("FECHADO")) {
            System.out.println("Este ticket já está fechado.");
            return valor;
        }
        this.horaSaida = momentoSaida;
        this.valor = calcularValor(momentoSaida);
        this.status = "FECHADO";
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public double getValor() {
        return valor;
    }

    /** Mostra o comprovante do ticket no console. */
    public void gerarComprovante() {
        System.out.println("----- COMPROVANTE -----");
        System.out.println("Placa: " + veiculo.getPlaca() + " | Tipo: " + veiculo.getTipo());
        System.out.println("Proprietário: " + veiculo.getProprietario());
        System.out.println("Entrada: " + horaEntrada);
        if (horaSaida != null) {
            System.out.println("Saída: " + horaSaida);
            System.out.printf("Valor a pagar: R$ %.2f%n", valor);
        } else {
            System.out.println("Saída: (ainda estacionado)");
        }
        System.out.println("Status: " + status);
        System.out.println("------------------------");
    }
}