/**
 * Um Carro é um Veiculo (Atividade 1: relação "é um" confirmada).
 * Especialização que adiciona um atributo próprio: número de portas.
 */
public class Carro extends Veiculo {

    private int numeroPortas;

    public Carro(String placa, String modelo, String cor, String proprietario, int numeroPortas) {
        // super(...): inicializa a parte comum (herdada de Veiculo).
        // O tipo é definido aqui dentro, não pelo chamador - assim ninguém
        // consegue criar um Carro com TipoVeiculo.MOTO por engano.
        super(placa, modelo, cor, proprietario, TipoVeiculo.CARRO);

        if (numeroPortas <= 0) {
            throw new IllegalArgumentException("Número de portas inválido: deve ser maior que zero.");
        }
        this.numeroPortas = numeroPortas;
    }

    public int getNumeroPortas() {
        return numeroPortas;
    }

    // Sobrescrita (Atividade 8): Carro fornece sua própria versão do
    // método declarado em Veiculo. @Override confirma que estamos de fato
    // implementando o contrato herdado, não criando um método novo por acaso.
    @Override
    public double getTarifaHora() {
        return 8.00;
    }
}