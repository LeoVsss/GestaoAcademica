package dao;

import connection.DbConnection;
import model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDao implements GenericDao<Curso> {

    private final DbConnection dbConnection;

    public CursoDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void inserir(Curso curso) {
        String sql;
        if (curso.getCodCurso() != null) {
            sql = "INSERT INTO curso (codigo, nome_curso, descricao) VALUES (?, ?, ?)";
        } else {
            sql = "INSERT INTO curso (nome_curso, descricao) VALUES (?, ?)";
        }
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            if (curso.getCodCurso() != null) {
                stmt.setInt(1, curso.getCodCurso());
                stmt.setString(2, curso.getNomeCurso());
                stmt.setString(3, curso.getDescCurso());
            } else {
                stmt.setString(1, curso.getNomeCurso());
                stmt.setString(2, curso.getDescCurso());
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir curso: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Curso> buscarPorId(int id) {
        String sql = "SELECT codigo, nome_curso, descricao FROM curso WHERE codigo = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Curso> listarTodos() {
        String sql = "SELECT codigo, nome_curso, descricao FROM curso ORDER BY nome_curso";
        List<Curso> cursos = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) cursos.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos: " + e.getMessage(), e);
        }
        return cursos;
    }

    @Override
    public void atualizar(Curso curso) {
        String sql = "UPDATE curso SET nome_curso = ?, descricao = ? WHERE codigo = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, curso.getNomeCurso());
            stmt.setString(2, curso.getDescCurso());
            stmt.setInt(3, curso.getCodCurso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM curso WHERE codigo = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso: " + e.getMessage(), e);
        }
    }

    private Curso mapRow(ResultSet rs) throws SQLException {
        return new Curso(rs.getInt("codigo"), rs.getString("nome_curso"), rs.getString("descricao"));
    }
}
