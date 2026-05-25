package ui.panel;

import model.Curso;
import model.Professor;
import ui.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * SRP: apenas responsável pelo layout e componentes visuais de Professor.
 */
public class ProfessorPanel extends JPanel {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    public final JTextField    txtCodigo      = UITheme.createTextField();
    public final JTextField    txtNome        = UITheme.createTextField();
    public final JTextField    txtDataNasc    = UITheme.createTextField();
    public final JComboBox<Curso> cbCurso     = new JComboBox<>();

    public final JButton btnSalvar    = UITheme.createButton("💾 Salvar",     UITheme.SUCCESS);
    public final JButton btnAtualizar = UITheme.createButton("✏ Atualizar",   UITheme.WARNING);
    public final JButton btnExcluir   = UITheme.createButton("🗑 Excluir",    UITheme.DANGER);
    public final JButton btnLimpar    = UITheme.createButton("✖ Limpar",      UITheme.TEXT_MUTED);

    public final DefaultTableModel tableModel;
    public final JTable table;

    public ProfessorPanel() {
        setLayout(new BorderLayout());
        setBackground(UITheme.SURFACE);

        tableModel = new DefaultTableModel(
                new String[]{"Código", "Nome", "Data Nasc.", "Curso"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTableHeader(table);
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(110);

        txtDataNasc.setToolTipText("Formato: dd/MM/yyyy");
        cbCurso.setFont(UITheme.FONT_INPUT);

        add(buildFormPanel(),  BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
    }

    private JPanel buildFormPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.SURFACE);
        wrapper.setBorder(UITheme.titledBorder("Dados do Professor"));

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setBackground(UITheme.SURFACE);
        GridBagConstraints gbc = defaultGbc();

        addLabelField(fields, gbc, 0, "Código Funcional:", txtCodigo);
        addLabelField(fields, gbc, 1, "Nome:",             txtNome);
        addLabelField(fields, gbc, 2, "Data Nascimento:",  txtDataNasc);
        addLabelField(fields, gbc, 3, "Curso:",            cbCurso);

        // Hint data
        gbc.gridy = 2; gbc.gridx = 2; gbc.weightx = 0;
        JLabel hint = new JLabel("(dd/MM/yyyy)");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(UITheme.TEXT_MUTED);
        fields.add(hint, gbc);

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
        p.setBorder(UITheme.titledBorder("Lista de Professores"));
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

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

    public void atualizarComboCurso(List<Curso> cursos) {
        cbCurso.removeAllItems();
        cursos.forEach(cbCurso::addItem);
    }

    public void preencherFormulario(Professor p) {
        txtCodigo.setText(p.getCodProfessor() != null ? String.valueOf(p.getCodProfessor()) : "");
        txtNome.setText(p.getNomeProfessor() != null ? p.getNomeProfessor() : "");
        txtDataNasc.setText(p.getDataNascProfessor() != null ? sdf.format(p.getDataNascProfessor()) : "");
        if (p.getCurso() != null) {
            for (int i = 0; i < cbCurso.getItemCount(); i++) {
                if (cbCurso.getItemAt(i).getCodCurso().equals(p.getCurso().getCodCurso())) {
                    cbCurso.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public void limparFormulario() {
        txtCodigo.setText(""); txtNome.setText(""); txtDataNasc.setText("");
        if (cbCurso.getItemCount() > 0) cbCurso.setSelectedIndex(0);
        table.clearSelection();
    }

    public int getCodigo() {
        try { return Integer.parseInt(txtCodigo.getText().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public java.util.Date getDataNascimento() throws ParseException {
        return sdf.parse(txtDataNasc.getText().trim());
    }

    public Curso getCursoSelecionado() {
        return (Curso) cbCurso.getSelectedItem();
    }

    public int getSelectedRow() { return table.getSelectedRow(); }
}
