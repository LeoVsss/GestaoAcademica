package service;

import model.Curso;
import java.util.List;
import java.util.Optional;

/** ISP: Interface focada exclusivamente no domínio Curso. */
public interface CursoService {
    void cadastrar(Curso curso);
    Optional<Curso> buscarPorId(int id);
    List<Curso> listarTodos();
    void atualizar(Curso curso);
    void remover(int id);
}
