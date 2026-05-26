package service.impl;

import dao.DisciplinaDao;
import model.Disciplina;
import service.DisciplinaService;

import java.util.List;
import java.util.Optional;

public class DisciplinaServiceImpl implements DisciplinaService {

    private final DisciplinaDao disciplinaDao;

    public DisciplinaServiceImpl(DisciplinaDao disciplinaDao) {
        this.disciplinaDao = disciplinaDao;
    }

    @Override
    public void cadastrar(Disciplina disciplina) {
        validar(disciplina);
        disciplinaDao.inserir(disciplina);
    }

    @Override
    public Optional<Disciplina> buscarPorId(int id) {
        return disciplinaDao.buscarPorId(id);
    }

    @Override
    public List<Disciplina> listarTodos() {
        return disciplinaDao.listarTodos();
    }

    @Override
    public void atualizar(Disciplina disciplina) {
        validar(disciplina);
        if (disciplina.getNumeroDisciplina() == null)
            throw new IllegalArgumentException("Número da disciplina é obrigatório para atualização.");
        disciplinaDao.atualizar(disciplina);
    }

    @Override
    public void remover(int id) {
        disciplinaDao.deletar(id);
    }

    private void validar(Disciplina d) {
        if (d.getNomeDisciplina() == null || d.getNomeDisciplina().isBlank())
            throw new IllegalArgumentException("Nome da disciplina é obrigatório.");
        if (d.getDataInicio() == null)
            throw new IllegalArgumentException("Data de início é obrigatória.");
        if (d.getDataEncerramento() == null)
            throw new IllegalArgumentException("Data de encerramento é obrigatória.");
        if (d.getDataInicio().after(d.getDataEncerramento()))
            throw new IllegalArgumentException("Data de início deve ser anterior ao encerramento.");
        if (d.getProfessorResponsavel() == null)
            throw new IllegalArgumentException("Professor responsável é obrigatório.");
        if (d.getCurso() == null)
            throw new IllegalArgumentException("Curso é obrigatório.");
    }
}
