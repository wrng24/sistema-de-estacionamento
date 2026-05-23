package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDateTime;
import model.Veiculo;

public class MovimentacaoDAO {

    public boolean veiculoEstacionado(int veiculoId) {

        String sql = """
            SELECT * FROM movimentacoes
            WHERE veiculo_id = ?
            AND data_saida IS NULL
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setInt(1, veiculoId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registrarEntrada(int veiculoId, int vagaId) {

        String sql = """
            INSERT INTO movimentacoes
            (veiculo_id, vaga_id, data_entrada)
            VALUES (?, ?, ?)
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setInt(1, veiculoId);
            stmt.setInt(2, vagaId);
            stmt.setString(3, LocalDateTime.now().toString());

            stmt.executeUpdate();

            System.out.println("Entrada registrada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int registrarSaida(int veiculoId, Veiculo veiculo) {

        String buscarSql = """
            SELECT * FROM movimentacoes
            WHERE veiculo_id = ?
            AND data_saida IS NULL
        """;

        String updateSql = """
            UPDATE movimentacoes
            SET data_saida = ?, valor_pago = ?
            WHERE id = ?
        """;

        int vagaId = -1;

        try (Connection conexao = Conexao.conectar()) {

            try (
                PreparedStatement buscarStmt = conexao.prepareStatement(buscarSql)
            ) {
                buscarStmt.setInt(1, veiculoId);

                try (ResultSet rs = buscarStmt.executeQuery()) {

                    if (rs.next()) {

                        int movimentacaoId = rs.getInt("id");
                        vagaId = rs.getInt("vaga_id");

                        LocalDateTime entrada = LocalDateTime.parse(
                                rs.getString("data_entrada")
                        );

                        LocalDateTime saida = LocalDateTime.now();

                        long horas = Duration.between(entrada, saida).toHours();

                        if (horas == 0) {
                            horas = 1;
                        }

                        double valor = veiculo.calcularValor((int) horas);

                        try (
                            PreparedStatement updateStmt =
                                    conexao.prepareStatement(updateSql)
                        ) {
                            updateStmt.setString(1, saida.toString());
                            updateStmt.setDouble(2, valor);
                            updateStmt.setInt(3, movimentacaoId);

                            updateStmt.executeUpdate();
                        }

                        System.out.println("Saída registrada!");
                        System.out.println("Valor a pagar: R$ " + valor);

                    } else {
                        System.out.println("Veículo não está estacionado.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vagaId;
    }

    public void listarEstacionados() {

        String sql = """
            SELECT v.placa, v.modelo, v.cor, v.tipo, vg.numero, m.data_entrada
            FROM movimentacoes m
            JOIN veiculos v ON m.veiculo_id = v.id
            JOIN vagas vg ON m.vaga_id = vg.id
            WHERE m.data_saida IS NULL
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            System.out.println("\n=== VEÍCULOS ESTACIONADOS ===");

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;

                System.out.println("-------------------------");
                System.out.println("Placa: " + rs.getString("placa"));
                System.out.println("Modelo: " + rs.getString("modelo"));
                System.out.println("Cor: " + rs.getString("cor"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Vaga: " + rs.getInt("numero"));
                System.out.println("Entrada: " + rs.getString("data_entrada"));
            }

            if (!encontrou) {
                System.out.println("Nenhum veículo estacionado no momento.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mostrarHistorico() {

        String sql = """
            SELECT v.placa, v.modelo, v.tipo, vg.numero,
                   m.data_entrada, m.data_saida, m.valor_pago
            FROM movimentacoes m
            JOIN veiculos v ON m.veiculo_id = v.id
            JOIN vagas vg ON m.vaga_id = vg.id
            ORDER BY m.id DESC
        """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            System.out.println("\n=== HISTÓRICO DE MOVIMENTAÇÕES ===");

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;

                System.out.println("-------------------------");
                System.out.println("Placa: " + rs.getString("placa"));
                System.out.println("Modelo: " + rs.getString("modelo"));
                System.out.println("Tipo: " + rs.getString("tipo"));
                System.out.println("Vaga: " + rs.getInt("numero"));
                System.out.println("Entrada: " + rs.getString("data_entrada"));
                System.out.println("Saída: " + rs.getString("data_saida"));
                System.out.println("Valor pago: R$ " + rs.getDouble("valor_pago"));
            }

            if (!encontrou) {
                System.out.println("Nenhuma movimentação registrada.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}