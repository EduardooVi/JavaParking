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

    private Veiculo veiculo;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSaida;
    private double valor;
    private StatusTicket status; // ABERTO enquanto o veículo está estacionado, FECHADO após o pagamento
    // (Atividade 7) Antes era String livre; virou enum após revisão de IA,
    // para impedir valores inconsistentes que uma String permitiria.

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
        this.status = StatusTicket.ABERTO;
    }

    // Validação da entrada do veículo (Atividade 6 - revisão): antes, este
    // método também checava placa e tipo. Isso foi removido daqui porque
    // essa responsabilidade agora pertence ao próprio Veiculo, que já
    // garante nascer válido em seu construtor. Um Veiculo que existe já é,
    // por definição, um veículo com dados coerentes — o Ticket só precisa
    // garantir que recebeu uma referência de verdade, não nula.
    private void validarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo.");
        }
    }

    /**
     * Calcula o valor a cobrar com base no tempo estacionado (arredondado
     * para cima, hora cheia, mínimo de 1 hora) multiplicado pela tarifa do
     * veículo.
     *
     * (Etapa 3 - Atividade 8) Antes, este método perguntava "que tipo é
     * esse?" através de um switch (tarifaPorTipo). Agora o próprio veículo
     * responde sua tarifa via getTarifaHora() - método sobrescrito por
     * Carro, Moto e Caminhonete. O Ticket não precisa mais saber que tipos
     * de veículo existem; é polimorfismo substituindo a decisão condicional.
     */
    private double calcularValor(LocalDateTime momentoSaida) {
        long minutos = Duration.between(horaEntrada, momentoSaida).toMinutes();
        long horas = (long) Math.ceil(minutos / 60.0);
        if (horas < 1) {
            horas = 1; // cobrança mínima de 1 hora
        }
        return horas * veiculo.getTarifaHora();
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
        if (status == StatusTicket.FECHADO) {
            System.out.println("Este ticket já está fechado.");
            return valor;
        }
        // Validação no fechamento (Atividade 6): uma saída anterior à
        // entrada geraria tempo negativo e, consequentemente, um valor de
        // cobrança negativo — um estado impossível no domínio.
        if (momentoSaida.isBefore(horaEntrada)) {
            throw new IllegalArgumentException(
                    "Hora de saída não pode ser anterior à hora de entrada.");
        }
        this.horaSaida = momentoSaida;
        this.valor = calcularValor(momentoSaida);
        this.status = StatusTicket.FECHADO;
        return valor;
    }

    public StatusTicket getStatus() {
        return status;
    }

    public double getValor() {
        return valor;
    }

    // Getter legítimo: outras classes podem precisar consultar quando o
    // veículo entrou, sem depender de imprimir o comprovante inteiro.
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
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