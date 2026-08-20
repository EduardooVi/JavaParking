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

        // Demonstrando a validação: tentando gerar ticket para veículo com placa vazia
        System.out.println();
        try {
            Veiculo veiculoInvalido = new Veiculo("", "Sem Placa", "Preto", "Desconhecido", TipoVeiculo.CARRO);
            new Ticket(veiculoInvalido);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao gerar ticket: " + e.getMessage());
        }
    }
}