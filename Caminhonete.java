/**
 * Uma Caminhonete é um Veiculo (Atividade 1: relação "é um" confirmada).
 * Especialização que adiciona um atributo próprio: capacidade de carga.
 */
public class Caminhonete extends Veiculo {

    private double capacidadeCargaToneladas;

    public Caminhonete(String placa, String modelo, String cor, String proprietario, double capacidadeCargaToneladas) {
        super(placa, modelo, cor, proprietario, TipoVeiculo.CAMINHONETE);

        if (capacidadeCargaToneladas <= 0) {
            throw new IllegalArgumentException("Capacidade de carga inválida: deve ser maior que zero.");
        }
        this.capacidadeCargaToneladas = capacidadeCargaToneladas;
    }

    public double getCapacidadeCargaToneladas() {
        return capacidadeCargaToneladas;
    }

    @Override
    public double getTarifaHora() {
        return 12.00;
    }
}