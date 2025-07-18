package br.com.alura.literalura.literalura.repository;

import br.com.alura.literalura.literalura.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private final String URL = "jdbc:postgresql://localhost:5432/literalura";
    private final String USUARIO = "literalura";
    private final String SENHA = "@123456";

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void salvarLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, nascimento_autor, falecimento_autor, downloads, idioma) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getNascimentoAutor());
            stmt.setInt(4, livro.getFalecimentoAutor());
            stmt.setInt(5, livro.getDownloads());
            stmt.setString(6, livro.getIdioma());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao salvar livro: " + e.getMessage());
        }
    }

    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT titulo, autor, nascimento_autor, falecimento_autor, downloads, idioma FROM livros";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                livros.add(new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("nascimento_autor"),
                        rs.getInt("falecimento_autor"),
                        rs.getInt("downloads"),
                        rs.getString("idioma")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }

        return livros;
    }
}