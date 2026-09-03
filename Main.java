import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Criando veículos de tipos diferentes, com proprietário
        Veiculo carro1 = new Veiculo("ABC-1234", "Fiat Uno", "Branco", "Maria Silva", TipoVeiculo.CARRO);
        Veiculo moto1 = new Veiculo("MTO-0001", "Honda CG", "Vermelha", "João Souza", TipoVeiculo.MOTO);
        Veiculo caminhonete1 = new Veiculo("CAM-9999", "Toyota Hilux", "Cinza", "Ana Costa", TipoVeiculo.CAMINHONETE);

        carro1.exibirDados();
        moto1.exibirDados();
        caminhonete1.exibirDados();

        // Vagas do estacionamento
        Vaga vaga1 = new Vaga(1);
        Vaga vaga2 = new Vaga(2);
        Vaga vaga3 = new Vaga(3);

        // Comportamento do veículo: entrar e estacionar (delega a mudança de estado à Vaga)
        carro1.entrar();
        carro1.estacionar(vaga1);

        moto1.entrar();
        moto1.estacionar(vaga2);

        caminhonete1.entrar();
        caminhonete1.estacionar(vaga3);

        System.out.println();
        vaga1.exibirStatus();
        vaga2.exibirStatus();
        vaga3.exibirStatus();
        System.out.println();

        // Emitindo os tickets com hora de entrada simulada (2h, 1h e 3h atrás)
        // para demonstrar o cálculo por tempo estacionado sem precisar esperar de verdade
        Ticket ticketCarro = new Ticket(carro1, LocalDateTime.now().minusHours(2));
        Ticket ticketMoto = new Ticket(moto1, LocalDateTime.now().minusHours(1));
        Ticket ticketCaminhonete = new Ticket(caminhonete1, LocalDateTime.now().minusHours(3).minusMinutes(15));

        // Fechando os tickets: calcula o valor com base no tempo + tarifa do tipo
        ticketCarro.fecharTicket();
        ticketMoto.fecharTicket();
        ticketCaminhonete.fecharTicket();

        ticketCarro.gerarComprovante();
        ticketMoto.gerarComprovante();
        ticketCaminhonete.gerarComprovante();

        // Comportamento do veículo: sair (libera a vaga)
        System.out.println();
        carro1.sair(vaga1);
        vaga1.exibirStatus();

        // ---------------------------------------------------------------
        // Atividade 8 — Testes de estado válido e inválido
        // ---------------------------------------------------------------
        System.out.println();
        System.out.println("===== Testes de validação (Atividade 8) =====");

        // ---- 2 criações válidas de objetos ----
        // (Já demonstradas no início do main: carro1, moto1 e caminhonete1
        // nasceram com dados válidos, passando pela validação do construtor
        // de Veiculo sem lançar exceção.)
        System.out.println("[Criação válida 1] Veículo criado: " + carro1.getPlaca());
        System.out.println("[Criação válida 2] Vaga criada: número " + vaga1.getNumero());

        // ---- 2 operações válidas ----
        // (Também já demonstradas acima: carro1.estacionar(vaga1) mudou o
        // estado da vaga corretamente, e ticketCarro.fecharTicket() calculou
        // e fechou o ticket com sucesso.)
        System.out.println("[Operação válida 1] carro1.estacionar(vaga1) -> vaga ficou ocupada");
        System.out.println("[Operação válida 2] ticketCarro.fecharTicket() -> status ficou " + ticketCarro.getStatus());

        // ---- 2 tentativas de alteração/criação inválida ----

        // [Tentativa inválida 1] veículo com placa vazia.
        // A validação está no construtor de Veiculo: o objeto não chega a existir.
        try {
            new Veiculo("", "Sem Placa", "Preto", "Desconhecido", TipoVeiculo.CARRO);
        } catch (IllegalArgumentException e) {
            System.out.println("[Tentativa inválida 1] Erro ao criar veículo: " + e.getMessage());
        }

        // [Tentativa inválida 2] vaga com número inválido (zero ou negativo).
        try {
            new Vaga(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("[Tentativa inválida 2] Erro ao criar vaga: " + e.getMessage());
        }

        // Tentativa extra (bônus, além do mínimo de 2): ticket com saída
        // anterior à entrada.
        try {
            Ticket ticketInvalido = new Ticket(moto1, LocalDateTime.now());
            ticketInvalido.fecharTicket(LocalDateTime.now().minusHours(1));
        } catch (IllegalArgumentException e) {
            System.out.println("[Tentativa inválida extra] Erro ao fechar ticket: " + e.getMessage());
        }

        // ---- 1 caso em que um método de negócio protege melhor que um setter genérico ----
        // Vaga NÃO expõe setOcupada(boolean). Se expusesse, seria possível
        // fazer vaga2.setOcupada(true) sem vincular nenhum veículo, deixando
        // ocupada=true e veiculoAtual=null (estado inconsistente).
        // Com ocupar(), a regra "não ocupar vaga já ocupada" é garantida
        // dentro do próprio objeto, e o estado nunca fica inconsistente.
        System.out.println();
        System.out.println("[Método de domínio x setter genérico] tentando ocupar vaga já ocupada:");
        vaga2.ocupar(carro1); // vaga2 já está ocupada pela moto1 -> apenas avisa, não corrompe o estado
    }
}