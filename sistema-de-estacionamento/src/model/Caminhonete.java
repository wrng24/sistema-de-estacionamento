package model;

public class Caminhonete extends Veiculo {

    public Caminhonete(String placa, String modelo, String cor) {
        super(placa, modelo, cor, "caminhonete");
    }

    @Override
    public double calcularValor(int horas) {
        return calcularValorBase(horas) * 1.5;
    }
}