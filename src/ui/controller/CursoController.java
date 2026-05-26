package ui.controller;

import model.Curso;
import service.CursoService;
import ui.panel.CursoPanel;

import javax.swing.*;
import java.util.List;

/**
 * SRP: orquestra eventos de UI do painel de Curso.
 * DIP: depende de CursoService (interface), não de implementação concreta.
 */
public class CursoController {

    private final CursoService service;
    private final CursoPanel   panel;

    public CursoController(CursoService service, CursoPanel panel) {
        this.service = service;
        this.panel   = panel;
        registrarEventos();
        carregarTabela();
    }

    // ─── Registro de eventos ─────────────────────────────────────────────────

    private void registrarEventos() {
        panel.btnSalvar.addActionListener(e    -> onSalvar());
        panel.btnAtualizar.addActionListener(e -> onAtualizar());
        panel.btnExcluir.addActionListener(e   -> onExcluir());
        panel.btnLimpar.addActionListener(e    -> panel.limparFormulario());

        // Seleção na tabela preenche o formulário
        panel.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onSelecionarLinha();
        });
    }

    // ─── Handlers ────────────────────────────────────────────────────────────

    private void onSalvar() {
        try {
            int cod = panel.getCodigo();
            Curso curso = new Curso(
                    cod > 0 ? cod : null,
                    panel.txtNome.getText().trim(),
                    panel.txtDescricao.getText().trim()
            );
            service.cadastrar(curso);
            mostrarSucesso("Curso cadastrado com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onAtualizar() {
        if (panel.getCodigo() < 0) { mostrarAviso("Selecione um curso para atualizar."); return; }
        try {
            Curso curso = new Curso(
                    panel.getCodigo(),
                    panel.txtNome.getText().trim(),
                    panel.txtDescricao.getText().trim()
            );
            service.atualizar(curso);
            mostrarSucesso("Curso atualizado com sucesso!");
            panel.limparFormulario();
            carregarTabela();
        } catch (Exception ex) {
            mostrarErro(ex.getMessage());
        }
    }

    private void onExcluir() {
        int id = panel.getCodigo();
        if (id < 0) { mostrarAviso("Selecione um curso para excluir."); return; }
        int confirm = JOptionPane.showConfirmDialog(panel,
                "Deseja excluir o curso de código " + id + "?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            service.remover(id);
            mostrarSucesso("Curso excluído com sucesso!");
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
        service.buscarPorId(codigo).ifPresent(panel::preencherFormulario);
    }

    // ─── Utilitários ─────────────────────────────────────────────────────────

    public void carregarTabela() {
        panel.tableModel.setRowCount(0);
        try {
            List<Curso> cursos = service.listarTodos();
            for (Curso c : cursos) {
                panel.tableModel.addRow(new Object[]{
                        c.getCodCurso(), c.getNomeCurso(), c.getDescCurso()
                });
            }
        } catch (Exception ex) {
            mostrarErro("Erro ao carregar cursos: " + ex.getMessage());
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
