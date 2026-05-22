package app;

import dao.CriarTabelas;
import service.Estacionamento;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        CriarTabelas.criar();

        Scanner scanner = new Scanner(System.in);
        Estacionamento estacionamento = new Estacionamento();

        int opcao;

        do {
            System.out.println("\n===== ESTACIONAMENTO =====");
            System.out.println("1 - Cadastrar veículo");
            System.out.println("2 - Registrar entrada");
            System.out.println("3 - Registrar saída");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Placa: ");
                    String placa = scanner.nextLine();

                    System.out.print("Modelo: ");
                    String modelo = scanner.nextLine();

                    System.out.print("Cor: ");
                    String cor = scanner.nextLine();

                    System.out.print("Tipo (carro/moto/caminhonete): ");
                    String tipo = scanner.nextLine();

                    estacionamento.cadastrarVeiculo(placa, modelo, cor, tipo);
                    break;

                case 2:
                    System.out.print("Placa: ");
                    String placaEntrada = scanner.nextLine();

                    System.out.print("Número da vaga: ");
                    int vaga = scanner.nextInt();
                    scanner.nextLine();

                    estacionamento.registrarEntrada(placaEntrada, vaga);
                    break;

                case 3:
                    System.out.print("Placa: ");
                    String placaSaida = scanner.nextLine();

                    estacionamento.registrarSaida(placaSaida);
                    break;

                case 0:
                    System.out.println("Sistema encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}