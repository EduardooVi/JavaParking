/**
 * Tipos de veículo aceitos no estacionamento.
 * Usar enum em vez de String evita erros de digitação (ex: "carro" vs "Carro")
 * e restringe os valores possíveis a apenas estes três, conforme a modelagem
 * definida na Atividade 3.
 */
public enum TipoVeiculo {
    MOTO,
    CARRO,
    CAMINHONETE
}