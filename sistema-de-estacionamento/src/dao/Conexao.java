package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private static final String URL = "jdbc:sqlite:database/estacionamento.db";

    public static Connection conectar() throws SQLException {
        Connection conexao = DriverManager.getConnection(URL);

        try (Statement stmt = conexao.createStatement()) {
            stmt.execute("PRAGMA busy_timeout = 5000");
        }

        return conexao;
    }
}