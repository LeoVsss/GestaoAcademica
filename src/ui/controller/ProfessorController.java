package ui.controller;

import model.Curso;
import model.Professor;
import service.CursoService;
import service.ProfessorService;
import ui.panel.ProfessorPanel;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * SRP: orquestra eventos de UI do painel de Professor.
 * DIP: depende de ProfessorService e CursoService (interfaces).
 */
public class ProfessorController {

    private final ProfessorService professorService;
    private final CursoService     cursoService;
    private final ProfessorPanel   panel;

    public ProfessorController(ProfessorService professorService,
                               CursoService cursoService,
                               ProfessorPanel panel) {
        this.professorService = professorService;
        this.cursoService     = cursoService;
        this.panel            = panel;
        registrarEventos();
        carregarComboCurso();
        carregarTabela();
    }

    private void registrarEventos() {
        panel.btnSalvar.addActionListener(e    -> onSalvar());
        panel.btnAtualizar.addActionListener(e -> onAtualizar());
        panel.btnExcluir.addActionListener(e   -> onExcluir());
        panel.btnLimpar.addActionListener(e    -> panel.limparFormulario());

        panel.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onSelecionarLinha();
        });
    }

    private void onSalvar() {
        try {
            Professor prof = montarProfessor();
            professorService.cadastrar(prof);
            mostrarSucesso("Professor cadastrado com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onAtualizar() {
        if (panel.getCodigo() < 0) { mostrarAviso("Selecione um professor para atualizar."); return; }
        try {
            Professor prof = montarProfessor();
            professorService.atualizar(prof);
            mostrarSucesso("Professor atualizado com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onExcluir() {
        int id = panel.getCodigo();
        if (id < 0) { mostrarAviso("Selecione um professor para excluir."); return; }
        int confirm = JOptionPane.showConfirmDialog(panel,
                "Deseja excluir o professor de código " + id + "?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            professorService.remover(id);
            mostrarSucesso("Professor excluído com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onSelecionarLinha() {
        int row = panel.getSelectedRow();
        if (row < 0) return;
        int codigo = (int) panel.tableModel.getValueAt(row, 0);
        professorService.buscarPorId(codigo).ifPresent(panel::preencherFormulario);
    }

    private Professor montarProfessor() throws Exception {
        int codigo = panel.getCodigo();
        String nome = panel.txtNome.getText().trim();
        java.util.Date dataNasc = panel.getDataNascimento();
        Curso curso = panel.getCursoSelecionado();
        return new Professor(curso, codigo > 0 ? codigo : null, nome, dataNasc);
    }

    public void carregarComboCurso() {
        try {
            List<Curso> cursos = cursoService.listarTodos();
            panel.atualizarComboCurso(cursos);
        } catch (Exception ex) {
            mostrarErro("Erro ao carregar cursos: " + ex.getMessage());
        }
    }

    public void carregarTabela() {
        panel.tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            List<Professor> professores = professorService.listarTodos();
            for (Professor p : professores) {
                panel.tableModel.addRow(new Object[]{
                        p.getCodProfessor(),
                        p.getNomeProfessor(),
                        p.getDataNascProfessor() != null ? sdf.format(p.getDataNascProfessor()) : "",
                        p.getCurso() != null ? p.getCurso().getNomeCurso() : ""
                });
            }
        } catch (Exception ex) {
            mostrarErro("Erro ao carregar professores: " + ex.getMessage());
        }
    }

    private void mostrarSucesso(String msg) {
        JOptionPane.showMessageDialog(panel, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    private void mostrarErro(String msg) {
        JOptionPane.showMessageDialog(panel, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    private void mostrarAviso(String msg) {
        JOptionPane.showMessageDialog(panel, msg, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}
