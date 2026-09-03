/**
 * Controla a disponibilidade de uma vaga do estacionamento.
 * Responsabilidade (Atividade 3): saber se está ocupada ou livre, e por
 * qual veículo, ao longo do tempo.
 */
public class Vaga {

    private int numero;
    private boolean ocupada;
    private Veiculo veiculoAtual; // relacionamento Vaga-Veiculo (sugestão de IA aceita)

    public Vaga(int numero) {
        // Validação no construtor (Atividade 6): uma vaga não pode nascer
        // com número zero ou negativo, pois não existe fisicamente no
        // estacionamento.
        if (numero <= 0) {
            throw new IllegalArgumentException("Número da vaga inválido: deve ser maior que zero.");
        }

        this.numero = numero;
        this.ocupada = false;
        this.veiculoAtual = null;
    }

    public int getNumero() {
        return numero;
    }

    // Getter legítimo: outras classes (ex: relatórios, o próprio Main)
    // podem precisar saber QUAL veículo está na vaga, sem depender de
    // exibirStatus() apenas imprimir no console.
    public Veiculo getVeiculoAtual() {
        return veiculoAtual;
    }

    // Comportamento: muda o estado da vaga (sugestão de IA aceita na Atividade 5)
    public void ocupar(Veiculo veiculo) {
        if (ocupada) {
            System.out.println("Vaga " + numero + " já está ocupada!");
            return;
        }
        this.veiculoAtual = veiculo;
        this.ocupada = true;
    }

    public void liberar() {
        if (!ocupada) {
            System.out.println("Vaga " + numero + " já está livre!");
            return;
        }
        this.veiculoAtual = null;
        this.ocupada = false;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void exibirStatus() {
        if (ocupada) {
            System.out.println("Vaga " + numero + " está OCUPADA por "
                    + veiculoAtual.getModelo() + " (" + veiculoAtual.getPlaca() + ")");
        } else {
            System.out.println("Vaga " + numero + " está LIVRE");
        }
    }
}