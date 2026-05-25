package ui;

import service.CursoService;
import service.DisciplinaService;
import service.ProfessorService;
import ui.controller.CursoController;
import ui.controller.DisciplinaController;
import ui.controller.ProfessorController;
import ui.panel.CursoPanel;
import ui.panel.DisciplinaPanel;
import ui.panel.ProfessorPanel;
import ui.util.UITheme;

import javax.swing.*;
import java.awt.*;

/**
 * SRP: responsável apenas pela montagem da janela principal.
 * DIP: recebe serviços via construtor — sem dependência de implementações concretas.
 */
public class MainFrame extends JFrame {

    private final CursoController      cursoController;
    private final ProfessorController  professorController;
    private final DisciplinaController disciplinaController;

    public MainFrame(CursoService cursoService,
                     ProfessorService professorService,
                     DisciplinaService disciplinaService) {

        super("Gestão Acadêmica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 650));
        setPreferredSize(new Dimension(1100, 750));

        // Painéis
        CursoPanel      cursoPanel      = new CursoPanel();
        ProfessorPanel  professorPanel  = new ProfessorPanel();
        DisciplinaPanel disciplinaPanel = new DisciplinaPanel();

        // Controllers — wireup de eventos e carga inicial
        cursoController      = new CursoController(cursoService, cursoPanel);
        professorController  = new ProfessorController(professorService, cursoService, professorPanel);
        disciplinaController = new DisciplinaController(disciplinaService, professorService, cursoService, disciplinaPanel);

        // Layout principal
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.SURFACE);
        root.add(buildHeader(),            BorderLayout.NORTH);
        root.add(buildTabs(cursoPanel, professorPanel, disciplinaPanel), BorderLayout.CENTER);
        root.add(buildStatusBar(),         BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    // ─── Cabeçalho ───────────────────────────────────────────────────────────

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UITheme.HEADER_BG);
        header.setPreferredSize(new Dimension(0, 60));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("🎓  Gestão Acadêmica");
        title.setFont(UITheme.FONT_TITLE);
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Sistema de Gerenciamento de Cursos, Professores e Disciplinas");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(148, 163, 184));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(title);
        textPanel.add(subtitle);
        header.add(textPanel, BorderLayout.CENTER);

        return header;
    }

    // ─── Abas ────────────────────────────────────────────────────────────────

    private JTabbedPane buildTabs(CursoPanel cursoPanel,
                                   ProfessorPanel professorPanel,
                                   DisciplinaPanel disciplinaPanel) {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setFont(UITheme.FONT_HEADING);
        tabs.setBackground(UITheme.SURFACE);

        tabs.addTab("📚  Cursos",       wrapTab(cursoPanel));
        tabs.addTab("👤  Professores",  wrapTab(professorPanel));
        tabs.addTab("📖  Disciplinas",  wrapTab(disciplinaPanel));

        // Recarregar combos ao trocar de aba (garantia de dados frescos)
        tabs.addChangeListener(e -> {
            int idx = tabs.getSelectedIndex();
            if (idx == 1) professorController.carregarComboCurso();
            if (idx == 2) disciplinaController.carregarCombos();
        });

        return tabs;
    }

    private JScrollPane wrapTab(JPanel panel) {
        JScrollPane sp = new JScrollPane(panel);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    // ─── Barra de status ─────────────────────────────────────────────────────

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        bar.setBackground(new Color(241, 245, 249));
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));

        JLabel lbl = new JLabel("✅  Conectado ao banco de dados PostgreSQL");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(UITheme.TEXT_MUTED);
        bar.add(lbl);
        return bar;
    }
}
