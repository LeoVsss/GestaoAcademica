package service;

import model.Disciplina;
import java.util.List;
import java.util.Optional;

public interface DisciplinaService {
    void cadastrar(Disciplina disciplina);
    Optional<Disciplina> buscarPorId(int id);
    List<Disciplina> listarTodos();
    void atualizar(Disciplina disciplina);
    void remover(int id);
}
