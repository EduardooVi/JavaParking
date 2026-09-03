/**
 * Uma Moto é um Veiculo (Atividade 1: relação "é um" confirmada).
 * Especialização que adiciona um atributo próprio: se possui bau (baú de carga).
 */
public class Moto extends Veiculo {

    private boolean possuiBau;

    public Moto(String placa, String modelo, String cor, String proprietario, boolean possuiBau) {
        super(placa, modelo, cor, proprietario, TipoVeiculo.MOTO);
        this.possuiBau = possuiBau;
    }

    public boolean isPossuiBau() {
        return possuiBau;
    }

    @Override
    public double getTarifaHora() {
        return 5.00;
    }
}