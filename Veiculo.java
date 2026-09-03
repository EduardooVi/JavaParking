/**
 * Representa os veículos que utilizam o estacionamento.
 * Responsabilidade: guardar os dados comuns a qualquer veículo e
 * representar os comportamentos comuns de entrada, saída e estacionamento.
 *
 * (Etapa 3 - Atividade 2) Virou superclasse abstrata: não existe, no nosso
 * domínio, um "Veículo genérico" que não seja Carro, Moto ou Caminhonete.
 * Cada veículo real sempre é de um desses três tipos, então impedir
 * `new Veiculo(...)` direto mantém o modelo fiel ao domínio.
 */
public abstract class Veiculo {

    // Atributos comuns a qualquer tipo de veículo (Encontro 1, pergunta 5)
    private String placa;
    private String modelo;
    private String cor;
    private String proprietario;
    private TipoVeiculo tipo;

    // Construtor "protected": só as subclasses podem chamar, via super(...).
    // Ninguém de fora consegue criar um Veiculo "puro".
    protected Veiculo(String placa, String modelo, String cor, String proprietario, TipoVeiculo tipo) {
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

    // --- Comportamentos comuns (Encontro 1, pergunta 5): entrar, sair e
    // estacionar são iguais pra qualquer tipo de veículo, então ficam aqui
    // na superclasse em vez de repetidos em cada subclasse. ---

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

    // --- Contrato comum, comportamento específico (Etapa 3 - Atividade 8) ---
    // Antes, quem decidia a tarifa era o Ticket, com um switch perguntando
    // "que tipo é esse?". Isso foi um sinal de herança faltando (Atividade 4).
    // Agora cada subclasse SABE sua própria tarifa - é ela quem responde,
    // não uma classe externa checando o tipo.
    /**
     * Tarifa cobrada por hora de permanência. Cada subclasse fornece seu
     * próprio valor.
     */
    public abstract double getTarifaHora();
}