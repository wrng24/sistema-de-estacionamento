package model;

public class Carro extends Veiculo {

    public Carro(String placa, String modelo, String cor) {
        super(placa, modelo, cor, "carro");
    }

    @Override
    public double calcularValor(int horas) {
        return calcularValorBase(horas);
    }
}