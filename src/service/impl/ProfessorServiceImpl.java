package service.impl;

import dao.ProfessorDao;
import model.Professor;
import service.ProfessorService;

import java.util.List;
import java.util.Optional;

public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorDao professorDao;

    public ProfessorServiceImpl(ProfessorDao professorDao) {
        this.professorDao = professorDao;
    }

    @Override
    public void cadastrar(Professor professor) {
        validar(professor);
        professorDao.inserir(professor);
    }

    @Override
    public Optional<Professor> buscarPorId(int id) {
        return professorDao.buscarPorId(id);
    }

    @Override
    public List<Professor> listarTodos() {
        return professorDao.listarTodos();
    }

    @Override
    public void atualizar(Professor professor) {
        validar(professor);
        if (professor.getCodProfessor() == null)
            throw new IllegalArgumentException("Código funcional é obrigatório para atualização.");
        professorDao.atualizar(professor);
    }

    @Override
    public void remover(int id) {
        professorDao.deletar(id);
    }

    private void validar(Professor professor) {
        if (professor.getNomeProfessor() == null || professor.getNomeProfessor().isBlank())
            throw new IllegalArgumentException("Nome do professor é obrigatório.");
        if (professor.getCurso() == null)
            throw new IllegalArgumentException("Curso é obrigatório.");
        if (professor.getDataNascProfessor() == null)
            throw new IllegalArgumentException("Data de nascimento é obrigatória.");
    }
}
