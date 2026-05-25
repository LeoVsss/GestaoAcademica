package ui.panel;

import model.Curso;
import model.Disciplina;
import model.Professor;
import ui.util.UITheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * SRP: apenas responsável pelo layout e componentes visuais de Disciplina.
 */
public class DisciplinaPanel extends JPanel {

    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

    public final JTextField           txtNumero        = UITheme.createTextField();
    public final JTextField           txtNome          = UITheme.createTextField();
    public final JTextField           txtDataInicio    = UITheme.createTextField();
    public final JTextField           txtDataEnc       = UITheme.createTextField();
    public final JComboBox<Professor> cbProfessor      = new JComboBox<>();
    public final JComboBox<Curso>     cbCurso          = new JComboBox<>();

    public final JButton btnSalvar    = UITheme.createButton("💾 Salvar",     UITheme.SUCCESS);
    public final JButton btnAtualizar = UITheme.createButton("✏ Atualizar",   UITheme.WARNING);
    public final JButton btnExcluir   = UITheme.createButton("🗑 Excluir",    UITheme.DANGER);
    public final JButton btnLimpar    = UITheme.createButton("✖ Limpar",      UITheme.TEXT_MUTED);

    public final DefaultTableModel tableModel;
    public final JTable table;

    public DisciplinaPanel() {
        setLayout(new BorderLayout());
        setBackground(UITheme.SURFACE);

        tableModel = new DefaultTableModel(
                new String[]{"Nº", "Nome", "Início", "Encerramento", "Professor", "Curso"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTableHeader(table);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(120);

        cbProfessor.setFont(UITheme.FONT_INPUT);
        cbCurso.setFont(UITheme.FONT_INPUT);
        txtNumero.setEditable(false);
        txtNumero.setBackground(new Color(241, 245, 249));
        txtNumero.setToolTipText("Gerado automaticamente");

        add(buildFormPanel(),  BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
    }

    private JPanel buildFormPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UITheme.SURFACE);
        wrapper.setBorder(UITheme.titledBorder("Dados da Disciplina"));

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setBackground(UITheme.SURFACE);
        GridBagConstraints gbc = defaultGbc();

        // Coluna esquerda e direita com 2 colunas de dados
        addLabelField(fields, gbc, 0, 0, "Nº (auto):",        txtNumero);
        addLabelField(fields, gbc, 0, 2, "Curso:",            cbCurso);
        addLabelField(fields, gbc, 1, 0, "Nome:",             txtNome);
        addLabelField(fields, gbc, 1, 2, "Professor:",        cbProfessor);
        addLabelField(fields, gbc, 2, 0, "Data Início:",      txtDataInicio);
        addLabelField(fields, gbc, 2, 2, "Data Encerramento:", txtDataEnc);

        // Hints de data
        for (int[] pos : new int[][]{{2,2},{2,4}}) {
            gbc.gridy = pos[0]; gbc.gridx = pos[1]; gbc.weightx = 0;
            JLabel h = new JLabel("(dd/MM/yyyy)");
            h.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            h.setForeground(UITheme.TEXT_MUTED);
            fields.add(h, gbc);
        }

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
        p.setBorder(UITheme.titledBorder("Lista de Disciplinas"));
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private GridBagConstraints defaultGbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        return g;
    }

    private void addLabelField(JPanel p, GridBagConstraints gbc,
                                int row, int colStart, String label, JComponent field) {
        gbc.gridy = row; gbc.gridx = colStart; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        p.add(makeLabel(label), gbc);
        gbc.gridx = colStart + 1; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        p.add(field, gbc);
    }

    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(UITheme.FONT_LABEL);
        l.setForeground(UITheme.TEXT_PRIMARY);
        return l;
    }

    public void atualizarComboProfessor(List<Professor> professores) {
        cbProfessor.removeAllItems();
        professores.forEach(cbProfessor::addItem);
    }

    public void atualizarComboCurso(List<Curso> cursos) {
        cbCurso.removeAllItems();
        cursos.forEach(cbCurso::addItem);
    }

    public void preencherFormulario(Disciplina d) {
        txtNumero.setText(d.getNumeroDisciplina() != null ? String.valueOf(d.getNumeroDisciplina()) : "");
        txtNome.setText(d.getNomeDisciplina() != null ? d.getNomeDisciplina() : "");
        txtDataInicio.setText(d.getDataInicio() != null ? sdf.format(d.getDataInicio()) : "");
        txtDataEnc.setText(d.getDataEncerramento() != null ? sdf.format(d.getDataEncerramento()) : "");

        if (d.getProfessorResponsavel() != null) {
            for (int i = 0; i < cbProfessor.getItemCount(); i++) {
                if (cbProfessor.getItemAt(i).getCodProfessor()
                        .equals(d.getProfessorResponsavel().getCodProfessor())) {
                    cbProfessor.setSelectedIndex(i); break;
                }
            }
        }
        if (d.getCurso() != null) {
            for (int i = 0; i < cbCurso.getItemCount(); i++) {
                if (cbCurso.getItemAt(i).getCodCurso().equals(d.getCurso().getCodCurso())) {
                    cbCurso.setSelectedIndex(i); break;
                }
            }
        }
    }

    public void limparFormulario() {
        txtNumero.setText(""); txtNome.setText(""); txtDataInicio.setText(""); txtDataEnc.setText("");
        if (cbProfessor.getItemCount() > 0) cbProfessor.setSelectedIndex(0);
        if (cbCurso.getItemCount() > 0) cbCurso.setSelectedIndex(0);
        table.clearSelection();
    }

    public int getNumero() {
        try { return Integer.parseInt(txtNumero.getText().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public java.util.Date getDataInicio() throws ParseException {
        return sdf.parse(txtDataInicio.getText().trim());
    }

    public java.util.Date getDataEncerramento() throws ParseException {
        return sdf.parse(txtDataEnc.getText().trim());
    }

    public Professor getProfessorSelecionado() { return (Professor) cbProfessor.getSelectedItem(); }
    public Curso getCursoSelecionado()         { return (Curso)     cbCurso.getSelectedItem(); }
    public int getSelectedRow()                { return table.getSelectedRow(); }
}
