package model;

public abstract class Veiculo {

    private int id;
    private String placa;
    private String modelo;
    private String cor;
    private String tipo;

    public Veiculo(String placa, String modelo, String cor, String tipo) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.tipo = tipo;
    }

    public abstract double calcularValor(int horas);

    protected double calcularValorBase(int horas) {
        double valor = 5.0;

        if (horas > 1) {
            valor += (horas - 1) * 3.0;
        }

        return valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public String getTipo() {
        return tipo;
    }
}