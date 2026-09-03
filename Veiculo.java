/**
 * Representa os veículos que utilizam o estacionamento.
 * Responsabilidade (conforme modelagem da Atividade 3): guardar os dados
 * do veículo e representar suas ações de entrada, saída e estacionamento.
 */
public class Veiculo {

    // Atributos privados: encapsulamento (sugestão de IA aceita na Atividade 5)
    private String placa;
    private String modelo;
    private String cor;
    private String proprietario; // atributo previsto na modelagem, estava faltando
    private TipoVeiculo tipo;

    public Veiculo(String placa, String modelo, String cor, String proprietario, TipoVeiculo tipo) {
        // Validação no construtor (Atividade 6): o veículo precisa nascer
        // válido. Antes, essa checagem só existia dentro do Ticket — mas
        // quem deveria garantir dados coerentes é o próprio Veiculo, não
        // uma classe que apenas o utiliza depois.
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("Placa inválida: não pode ser vazia.");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo inválido: não pode ser vazio.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de veículo não informado.");
        }

        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.proprietario = proprietario;
        this.tipo = tipo;
    }

    // --- Getters: acesso controlado ao estado (sem setters, pois esses
    // dados não deveriam mudar depois que o veículo é criado) ---

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public String getProprietario() {
        return proprietario;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    // --- Comportamentos previstos na modelagem (Atividade 3) ---
    // Antes, só a Vaga tinha ocupar()/liberar(). Aqui o Veículo passa a ter
    // sua própria ação de entrar/sair/estacionar, delegando a mudança de
    // estado da vaga para a própria Vaga (cada classe cuida do que é seu).

    /** O veículo entra no estabelecimento (evento anterior a ocupar uma vaga). */
    public void entrar() {
        System.out.println(modelo + " (" + placa + ") entrou no estacionamento.");
    }

    /** O veículo estaciona em uma vaga específica. */
    public void estacionar(Vaga vaga) {
        vaga.ocupar(this);
        System.out.println(modelo + " (" + placa + ") estacionou na vaga " + vaga.getNumero() + ".");
    }

    /** O veículo sai da vaga em que estava, liberando-a. */
    public void sair(Vaga vaga) {
        vaga.liberar();
        System.out.println(modelo + " (" + placa + ") saiu da vaga " + vaga.getNumero() + ".");
    }

    public void exibirDados() {
        System.out.println("Placa: " + placa + " | Modelo: " + modelo + " | Cor: " + cor
                + " | Proprietário: " + proprietario + " | Tipo: " + tipo);
    }
}