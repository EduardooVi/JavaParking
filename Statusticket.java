/**
 * Status possíveis de um Ticket.
 * Antes era representado por um "status" do tipo String livre, o que
 * permitia valores inconsistentes (ex: "fechado", "Fechado", "cancelado").
 * Virou enum após revisão de IA na Atividade 7 - sugestão aceita.
 */
public enum StatusTicket {
    ABERTO,
    FECHADO
}