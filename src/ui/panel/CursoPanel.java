package ui.panel;

import model.Curso;
import ui.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * SRP: apenas responsável pelo layout e componentes visuais de Curso.
 * Não contém lógica de negócio — delega ao CursoController.
 */
public class CursoPanel extends JPanel {

    // Campos do formulário
    public final JTextField txtCodigo      = UITheme.createTextField();
    public final JTextField txtNome        = UITheme.createTextField();
    public final JTextArea  txtDescricao   = UITheme.createTextArea(3, 20);

    // Botões de ação
    public final JButton btnSalvar    = UITheme.createButton("💾 Salvar",     UITheme.SUCCESS);
    public final JButton btnAtualizar = UITheme.createButton("✏ Atualizar",   UITheme.WARNING);
    public final JButton btnExcluir   = UITheme.createButton("🗑 Excluir",    UITheme.DANGER);
    public final JButton btnLimpar    = UITheme.createButton("✖ Limpar",      UITheme.TEXT_MUTED);

    // Tabela
    public final DefaultTableModel tableModel;
    public final JTable table;

    public CursoPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(UITheme.SURFACE);

        tableModel = new DefaultTableModel(new String[]{"Código", "Nome do Curso", "Descrição"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTableHeader(table);
        table.getColumnModel().getColumn(0).setMaxWidth(80);

        add(buildFormPanel(),  BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
    }

    private JPanel buildFormPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.SURFACE);
        wrapper.setBorder(UITheme.titledBorder("Dados do Curso"));

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setBackground(UITheme.SURFACE);
        GridBagConstraints gbc = defaultGbc();

        addLabelField(fields, gbc, 0, "Código:",    txtCodigo);
        addLabelField(fields, gbc, 1, "Nome:",      txtNome);

        // Descrição ocupa duas linhas
        gbc.gridy = 2; gbc.gridx = 0; gbc.anchor = GridBagConstraints.NORTHWEST;
        fields.add(makeLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        fields.add(new JScrollPane(txtDescricao), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0;

        wrapper.add(fields,        BorderLayout.CENTER);
        wrapper.add(buildButtons(), BorderLayout.SOUTH);
        return wrapper;
    }

    private JPanel buildButtons() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        p.setBackground(UITheme.SURFACE);
        p.add(btnLimpar); p.add(btnExcluir); p.add(btnAtualizar); p.add(btnSalvar);
        return p;
    }

    private JPanel buildTablePanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(UITheme.SURFACE);
        p.setBorder(UITheme.titledBorder("Lista de Cursos"));
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    // ─── helpers ────────────────────────────────────────────────────────────

    private GridBagConstraints defaultGbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        return g;
    }

    private void addLabelField(JPanel p, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridy = row; gbc.gridx = 0; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        p.add(makeLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        p.add(field, gbc);
    }

    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_LABEL);
        l.setForeground(UITheme.TEXT_PRIMARY);
        return l;
    }

    /** Preenche o formulário com dados de um curso selecionado. */
    public void preencherFormulario(Curso c) {
        txtCodigo.setText(c.getCodCurso() != null ? String.valueOf(c.getCodCurso()) : "");
        txtNome.setText(c.getNomeCurso() != null ? c.getNomeCurso() : "");
        txtDescricao.setText(c.getDescCurso() != null ? c.getDescCurso() : "");
    }

    /** Limpa todos os campos do formulário. */
    public void limparFormulario() {
        txtCodigo.setText(""); txtNome.setText(""); txtDescricao.setText("");
        table.clearSelection();
    }

    /** Retorna o código digitado ou -1 se inválido. */
    public int getCodigo() {
        try { return Integer.parseInt(txtCodigo.getText().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    /** Retorna o índice da linha selecionada na tabela. */
    public int getSelectedRow() { return table.getSelectedRow(); }
}
