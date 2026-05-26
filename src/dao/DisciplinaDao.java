package dao;

import connection.DbConnection;
import model.Curso;
import model.Disciplina;
import model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DisciplinaDao implements GenericDao<Disciplina> {

    private final DbConnection dbConnection;

    public DisciplinaDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void inserir(Disciplina d) {
        String sql = """
            INSERT INTO disciplina (nome, data_inicio, data_encerramento, codigo_professor, codigo_curso)
            VALUES (?, ?, ?, ?, ?)
            """;
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, d.getNomeDisciplina());
            stmt.setDate(2, new java.sql.Date(d.getDataInicio().getTime()));
            stmt.setDate(3, new java.sql.Date(d.getDataEncerramento().getTime()));
            stmt.setInt(4, d.getProfessorResponsavel().getCodProfessor());
            stmt.setInt(5, d.getCurso().getCodCurso());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir disciplina: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Disciplina> buscarPorId(int id) {
        String sql = buildSelectSql() + " WHERE d.numero = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar disciplina: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public List<Disciplina> listarTodos() {
        List<Disciplina> lista = new ArrayList<>();
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(buildSelectSql() + " ORDER BY d.nome");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar disciplinas: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void atualizar(Disciplina d) {
        String sql = """
            UPDATE disciplina
               SET nome = ?, data_inicio = ?, data_encerramento = ?,
                   codigo_professor = ?, codigo_curso = ?
             WHERE numero = ?
            """;
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, d.getNomeDisciplina());
            stmt.setDate(2, new java.sql.Date(d.getDataInicio().getTime()));
            stmt.setDate(3, new java.sql.Date(d.getDataEncerramento().getTime()));
            stmt.setInt(4, d.getProfessorResponsavel().getCodProfessor());
            stmt.setInt(5, d.getCurso().getCodCurso());
            stmt.setInt(6, d.getNumeroDisciplina());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar disciplina: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletar(int id) {
        String sql = "DELETE FROM disciplina WHERE numero = ?";
        try (PreparedStatement stmt = dbConnection.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar disciplina: " + e.getMessage(), e);
        }
    }

    private String buildSelectSql() {
        return """
            SELECT d.numero, d.nome, d.data_inicio, d.data_encerramento,
                   p.codigo_funcional, p.nome AS nome_professor, p.data_nascimento,
                   c.codigo AS cod_curso, c.nome_curso, c.descricao
              FROM disciplina d
         LEFT JOIN professor p ON p.codigo_funcional = d.codigo_professor
         LEFT JOIN curso     c ON c.codigo = d.codigo_curso
            """;
    }

    private Disciplina mapRow(ResultSet rs) throws SQLException {
        Curso curso = new Curso(rs.getInt("cod_curso"), rs.getString("nome_curso"), rs.getString("descricao"));
        Professor prof = new Professor(curso, rs.getInt("codigo_funcional"), rs.getString("nome_professor"), rs.getDate("data_nascimento"));
        return new Disciplina(rs.getInt("numero"), rs.getString("nome"),
                rs.getDate("data_inicio"), rs.getDate("data_encerramento"), prof, curso);
    }
}
