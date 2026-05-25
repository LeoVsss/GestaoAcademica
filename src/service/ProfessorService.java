package service;

import model.Professor;
import java.util.List;
import java.util.Optional;

/** ISP: Interface focada exclusivamente no domínio Professor. */
public interface ProfessorService {
    void cadastrar(Professor professor);
    Optional<Professor> buscarPorId(int id);
    List<Professor> listarTodos();
    void atualizar(Professor professor);
    void remover(int id);
}
