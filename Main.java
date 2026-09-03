import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Criando veículos de tipos diferentes, com proprietário
        // (Etapa 3 - Atividade 2) Agora instanciamos as subclasses, não mais
        // Veiculo diretamente - o tipo já vem definido por qual subclasse é usada.
        Carro carro1 = new Carro("ABC-1234", "Fiat Uno", "Branco", "Maria Silva", 4);
        Moto moto1 = new Moto("MTO-0001", "Honda CG", "Vermelha", "João Souza", false);
        Caminhonete caminhonete1 = new Caminhonete("CAM-9999", "Toyota Hilux", "Cinza", "Ana Costa", 1.5);

        carro1.exibirDados();
        moto1.exibirDados();
        caminhonete1.exibirDados();

        // Evidenciando que cada subclasse tem, de fato, algo próprio
        // (não é herança "só por ter o mesmo nome de atributo")
        System.out.println("-> Carro tem " + carro1.getNumeroPortas() + " portas");
        System.out.println("-> Moto possui baú? " + moto1.isPossuiBau());
        System.out.println("-> Caminhonete carrega até " + caminhonete1.getCapacidadeCargaToneladas() + " toneladas");

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
        // A validação está no construtor de Veiculo (herdada por Carro via super()):
        // o objeto não chega a existir.
        try {
            new Carro("", "Sem Placa", "Preto", "Desconhecido", 4);
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

        // ---------------------------------------------------------------
        // Atividade 9 — Demonstrando polimorfismo
        // ---------------------------------------------------------------
        // Aqui está o ponto central: as variáveis são declaradas do tipo da
        // SUPERCLASSE (Veiculo), mas cada uma aponta pra um objeto real de
        // uma subclasse diferente. Quando chamamos getTarifaHora() em cada
        // uma, o Java executa a versão SOBRESCRITA pela classe REAL do
        // objeto, não uma versão genérica de "Veiculo" (que nem existe,
        // já que o método é abstrato lá).
        System.out.println();
        System.out.println("===== Polimorfismo (Atividade 9) =====");

        Veiculo v1 = carro1;        // declarado Veiculo, mas o objeto real é um Carro
        Veiculo v2 = moto1;         // declarado Veiculo, mas o objeto real é uma Moto
        Veiculo v3 = caminhonete1;  // declarado Veiculo, mas o objeto real é uma Caminhonete

        Veiculo[] frota = { v1, v2, v3 };
        for (Veiculo v : frota) {
            // Mesma chamada de método (v.getTarifaHora()) em todos os casos,
            // mas o resultado muda de acordo com o objeto real por trás da
            // referência - não com o tipo declarado da variável.
            System.out.printf("%s (declarado como Veiculo, objeto real: %s) -> tarifa R$ %.2f/h%n",
                    v.getModelo(), v.getClass().getSimpleName(), v.getTarifaHora());
        }
    }
}