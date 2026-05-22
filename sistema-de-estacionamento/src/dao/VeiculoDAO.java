package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Caminhonete;
import model.Carro;
import model.Moto;
import model.Veiculo;

public class VeiculoDAO {

    public void cadastrar(Veiculo veiculo) {

        String sql = """
            INSERT INTO veiculos (placa, modelo, cor, tipo)
            VALUES (?, ?, ?, ?)
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());
            stmt.setString(4, veiculo.getTipo());

            stmt.executeUpdate();

            System.out.println(
                    "Veículo cadastrado com sucesso!"
            );

        } catch (Exception e) {

            System.out.println(
                    "Erro ao cadastrar veículo."
            );

            e.printStackTrace();
        }
    }

    public boolean placaExiste(String placa) {

        String sql = """
            SELECT * FROM veiculos
            WHERE placa = ?
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, placa);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    public int buscarIdPorPlaca(String placa) {

        String sql = """
            SELECT id FROM veiculos
            WHERE placa = ?
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, placa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return rs.getInt("id");
            }

            return -1;

        } catch (Exception e) {

            e.printStackTrace();
            return -1;
        }
    }

    public Veiculo buscarVeiculoPorPlaca(String placa) {

        String sql = """
            SELECT * FROM veiculos
            WHERE placa = ?
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt =
                    conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, placa);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String tipo =
                        rs.getString("tipo");

                Veiculo veiculo;

                switch (tipo) {

                    case "carro":

                        veiculo = new Carro(
                                rs.getString("placa"),
                                rs.getString("modelo"),
                                rs.getString("cor")
                        );

                        break;

                    case "moto":

                        veiculo = new Moto(
                                rs.getString("placa"),
                                rs.getString("modelo"),
                                rs.getString("cor")
                        );

                        break;

                    case "caminhonete":

                        veiculo = new Caminhonete(
                                rs.getString("placa"),
                                rs.getString("modelo"),
                                rs.getString("cor")
                        );

                        break;

                    default:

                        return null;
                }

                veiculo.setId(
                        rs.getInt("id")
                );

                return veiculo;
            }

            return null;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}