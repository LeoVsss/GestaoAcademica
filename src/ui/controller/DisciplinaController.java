package ui.controller;

import model.Curso;
import model.Disciplina;
import model.Professor;
import service.CursoService;
import service.DisciplinaService;
import service.ProfessorService;
import ui.panel.DisciplinaPanel;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * SRP: orquestra eventos de UI do painel de Disciplina.
 * DIP: depende de interfaces de serviço.
 */
public class DisciplinaController {

    private final DisciplinaService disciplinaService;
    private final ProfessorService  professorService;
    private final CursoService      cursoService;
    private final DisciplinaPanel   panel;

    public DisciplinaController(DisciplinaService disciplinaService,
                                ProfessorService professorService,
                                CursoService cursoService,
                                DisciplinaPanel panel) {
        this.disciplinaService = disciplinaService;
        this.professorService  = professorService;
        this.cursoService      = cursoService;
        this.panel             = panel;
        registrarEventos();
        carregarCombos();
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
            Disciplina d = montarDisciplina();
            disciplinaService.cadastrar(d);
            mostrarSucesso("Disciplina cadastrada com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onAtualizar() {
        if (panel.getNumero() < 0) { mostrarAviso("Selecione uma disciplina para atualizar."); return; }
        try {
            Disciplina d = montarDisciplina();
            disciplinaService.atualizar(d);
            mostrarSucesso("Disciplina atualizada com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onExcluir() {
        int id = panel.getNumero();
        if (id < 0) { mostrarAviso("Selecione uma disciplina para excluir."); return; }
        int confirm = JOptionPane.showConfirmDialog(panel,
                "Deseja excluir a disciplina nº " + id + "?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            disciplinaService.remover(id);
            mostrarSucesso("Disciplina excluída com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onSelecionarLinha() {
        int row = panel.getSelectedRow();
        if (row < 0) return;
        int numero = (int) panel.tableModel.getValueAt(row, 0);
        disciplinaService.buscarPorId(numero).ifPresent(panel::preencherFormulario);
    }

    private Disciplina montarDisciplina() throws Exception {
        int numero = panel.getNumero();
        Professor prof = panel.getProfessorSelecionado();
        Curso curso    = panel.getCursoSelecionado();
        return new Disciplina(
                numero > 0 ? numero : null,
                panel.txtNome.getText().trim(),
                panel.getDataInicio(),
                panel.getDataEncerramento(),
                prof, curso
        );
    }

    public void carregarCombos() {
        try {
            panel.atualizarComboProfessor(professorService.listarTodos());
            panel.atualizarComboCurso(cursoService.listarTodos());
        } catch (Exception ex) {
            mostrarErro("Erro ao carregar combos: " + ex.getMessage());
        }
    }

    public void carregarTabela() {
        panel.tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            List<Disciplina> lista = disciplinaService.listarTodos();
            for (Disciplina d : lista) {
                panel.tableModel.addRow(new Object[]{
                        d.getNumeroDisciplina(),
                        d.getNomeDisciplina(),
                        d.getDataInicio() != null ? sdf.format(d.getDataInicio()) : "",
                        d.getDataEncerramento() != null ? sdf.format(d.getDataEncerramento()) : "",
                        d.getProfessorResponsavel() != null ? d.getProfessorResponsavel().getNomeProfessor() : "",
                        d.getCurso() != null ? d.getCurso().getNomeCurso() : ""
                });
            }
        } catch (Exception ex) {
            mostrarErro("Erro ao carregar disciplinas: " + ex.getMessage());
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
