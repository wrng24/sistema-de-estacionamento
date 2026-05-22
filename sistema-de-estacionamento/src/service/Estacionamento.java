package service;

import dao.MovimentacaoDAO;
import dao.VagaDAO;
import dao.VeiculoDAO;

import model.Caminhonete;
import model.Carro;
import model.Moto;
import model.Veiculo;

public class Estacionamento {

    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private VagaDAO vagaDAO = new VagaDAO();
    private MovimentacaoDAO movimentacaoDAO =
            new MovimentacaoDAO();

    public void cadastrarVeiculo(
            String placa,
            String modelo,
            String cor,
            String tipo
    ) {

        if (veiculoDAO.placaExiste(placa)) {

            System.out.println(
                    "Placa já cadastrada."
            );

            return;
        }

        Veiculo veiculo;

        switch (tipo.toLowerCase()) {

            case "carro":
                veiculo = new Carro(
                        placa,
                        modelo,
                        cor
                );
                break;

            case "moto":
                veiculo = new Moto(
                        placa,
                        modelo,
                        cor
                );
                break;

            case "caminhonete":
                veiculo = new Caminhonete(
                        placa,
                        modelo,
                        cor
                );
                break;

            default:
                System.out.println(
                        "Tipo inválido."
                );
                return;
        }

        veiculoDAO.cadastrar(veiculo);
    }

    public void registrarEntrada(
            String placa,
            int numeroVaga
    ) {

        int veiculoId =
                veiculoDAO.buscarIdPorPlaca(placa);

        if (veiculoId == -1) {

            System.out.println(
                    "Veículo não encontrado."
            );

            return;
        }

        if (!vagaDAO.vagaExiste(numeroVaga)) {

            System.out.println(
                    "Vaga não existe."
            );

            return;
        }

        if (vagaDAO.vagaOcupada(numeroVaga)) {

            System.out.println(
                    "Vaga ocupada."
            );

            return;
        }

        if (
            movimentacaoDAO
                    .veiculoEstacionado(veiculoId)
        ) {

            System.out.println(
                    "Veículo já estacionado."
            );

            return;
        }

        int vagaId =
                vagaDAO.buscarIdPorNumero(numeroVaga);

        movimentacaoDAO.registrarEntrada(
                veiculoId,
                vagaId
        );

        vagaDAO.atualizarStatus(numeroVaga, true);
    }

    public void registrarSaida(String placa) {

        Veiculo veiculo = veiculoDAO.buscarVeiculoPorPlaca(placa);

        if (veiculo == null) {
            System.out.println("Veículo não encontrado.");
            return;
        }

        int vagaId = movimentacaoDAO.registrarSaida(
                veiculo.getId(),
                veiculo
        );

        if (vagaId != -1) {
            vagaDAO.liberarPorId(vagaId);
        }
    }
}