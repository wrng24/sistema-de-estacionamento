package model;

public class Moto extends Veiculo {

    public Moto(String placa, String modelo, String cor) {
        super(placa, modelo, cor, "moto");
    }

    @Override
    public double calcularValor(int horas) {
        return calcularValorBase(horas) * 0.5;
    }
}