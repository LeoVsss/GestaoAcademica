package dao;

import connection.DbConnection;
import model.Curso;
import model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfessorDao implements GenericDao<Professor> {

    private final DbConnection dbConnection;

    public ProfessorDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void inserir(Professor professor) {
        String sql = "INSERT INTO professor (codigo_funcional, nome, data_nascimento, cod_curso) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, professor.getCodProfessor());
            stmt.setString(2, professor.getNomeProfessor());
            stmt.setDate(3, new java.sql.Date(professor.getDataNascProfessor().getTime()));
            stmt.setInt(4, professor.getCurso().getCodCurso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir professor: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Professor> buscarPorId(int id) {
        String sql = """
            SELECT p.codigo_funcional, p.nome, p.data_nascimento,
                   c.codigo AS cod_curso, c.nome_curso, c.descricao
              FROM professor p
         LEFT JOIN curso c ON c.codigo = p.cod_curso
             WHERE p.codigo_funcional = ?
            """;
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar professor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Professor> listarTodos() {
        String sql = """
            SELECT p.codigo_funcional, p.nome, p.data_nascimento,
                   c.codigo AS cod_curso, c.nome_curso, c.descricao
              FROM professor p
         LEFT JOIN curso c ON c.codigo = p.cod_curso
          ORDER BY p.nome
            """;
        List<Professor> lista = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar professores: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void atualizar(Professor professor) {
        String sql = "UPDATE professor SET nome = ?, data_nascimento = ?, cod_curso = ? WHERE codigo_funcional = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, professor.getNomeProfessor());
            stmt.setDate(2, new java.sql.Date(professor.getDataNascProfessor().getTime()));
            stmt.setInt(3, professor.getCurso().getCodCurso());
            stmt.setInt(4, professor.getCodProfessor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar professor: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM professor WHERE codigo_funcional = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar professor: " + e.getMessage(), e);
        }
    }

    private Professor mapRow(ResultSet rs) throws SQLException {
        Curso curso = new Curso(rs.getInt("cod_curso"), rs.getString("nome_curso"), rs.getString("descricao"));
        return new Professor(curso, rs.getInt("codigo_funcional"), rs.getString("nome"), rs.getDate("data_nascimento"));
    }
}
