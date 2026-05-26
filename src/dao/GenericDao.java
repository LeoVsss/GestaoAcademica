package dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    void inserir(T entity);
    Optional<T> buscarPorId(int id);
    List<T> listarTodos();
    void atualizar(T entity);
    void deletar(int id);
}
