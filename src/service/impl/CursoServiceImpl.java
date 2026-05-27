package service.impl;

import dao.CursoDao;
import model.Curso;
import service.CursoService;

import java.util.List;
import java.util.Optional;

public class CursoServiceImpl implements CursoService {

    private final CursoDao cursoDao;

    public CursoServiceImpl(CursoDao cursoDao) {
        this.cursoDao = cursoDao;
    }

    @Override
    public void cadastrar(Curso curso) {
        validar(curso);
        cursoDao.inserir(curso);
    }

    @Override
    public Optional<Curso> buscarPorId(int id) {
        return cursoDao.buscarPorId(id);
    }

    @Override
    public List<Curso> listarTodos() {
        return cursoDao.listarTodos();
    }

    @Override
    public void atualizar(Curso curso) {
        validar(curso);
        if (curso.getCodCurso() == null)
            throw new IllegalArgumentException("Código do curso é obrigatório para atualização.");
        cursoDao.atualizar(curso);
    }

    @Override
    public void remover(int id) {
        cursoDao.deletar(id);
    }

    private void validar(Curso curso) {
        if (curso.getNomeCurso() == null || curso.getNomeCurso().isBlank())
            throw new IllegalArgumentException("Nome do curso é obrigatório.");
    }
}
