package dao;

import java.sql.Connection;
import java.sql.Statement;

public class CriarTabelas {

    public static void criar() {

        String sqlVeiculos = """
            CREATE TABLE IF NOT EXISTS veiculos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                placa TEXT NOT NULL UNIQUE,
                modelo TEXT NOT NULL,
                cor TEXT NOT NULL,
                tipo TEXT NOT NULL
            );
        """;

        String sqlVagas = """
            CREATE TABLE IF NOT EXISTS vagas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                numero INTEGER NOT NULL UNIQUE,
                ocupada INTEGER NOT NULL DEFAULT 0
            );
        """;

        String sqlMovimentacoes = """
            CREATE TABLE IF NOT EXISTS movimentacoes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                veiculo_id INTEGER NOT NULL,
                vaga_id INTEGER NOT NULL,
                data_entrada TEXT NOT NULL,
                data_saida TEXT,
                valor_pago REAL,

                FOREIGN KEY (veiculo_id) REFERENCES veiculos(id),
                FOREIGN KEY (vaga_id) REFERENCES vagas(id)
            );
        """;

        try {

            Connection conexao = Conexao.conectar();

            Statement stmt = conexao.createStatement();

            stmt.execute(sqlVeiculos);
            stmt.execute(sqlVagas);
            stmt.execute(sqlMovimentacoes);

            stmt.execute("""
                INSERT OR IGNORE INTO vagas (numero, ocupada)
                VALUES
                (1, 0),
                (2, 0),
                (3, 0),
                (4, 0),
                (5, 0);
            """);

            System.out.println("Tabelas criadas com sucesso!");

        } catch (Exception e) {

            System.out.println("Erro ao criar tabelas.");
            e.printStackTrace();
        }
    }
}