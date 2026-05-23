package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VagaDAO {

    public boolean vagaExiste(int numero) {
        String sql = "SELECT * FROM vagas WHERE numero = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean vagaOcupada(int numero) {
        String sql = "SELECT ocupada FROM vagas WHERE numero = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ocupada") == 1;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int buscarIdPorNumero(int numero) {
        String sql = "SELECT id FROM vagas WHERE numero = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setInt(1, numero);
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

    public void atualizarStatus(int numero, boolean ocupada) {
        String sql = "UPDATE vagas SET ocupada = ? WHERE numero = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setInt(1, ocupada ? 1 : 0);
            stmt.setInt(2, numero);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void liberarPorId(int vagaId) {
        String sql = "UPDATE vagas SET ocupada = 0 WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {
            stmt.setInt(1, vagaId);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}