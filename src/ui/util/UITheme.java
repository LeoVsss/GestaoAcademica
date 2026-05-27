package ui.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public final class UITheme {

    public static final Color PRIMARY        = new Color(37, 99, 235);
    public static final Color PRIMARY_HOVER  = new Color(29, 78, 216);
    public static final Color SUCCESS        = new Color(22, 163, 74);
    public static final Color DANGER         = new Color(220, 38, 38);
    public static final Color WARNING        = new Color(217, 119, 6);
    public static final Color SURFACE        = new Color(248, 250, 252);
    public static final Color HEADER_BG      = new Color(15, 23, 42);
    public static final Color TEXT_PRIMARY   = new Color(15, 23, 42);
    public static final Color TEXT_MUTED     = new Color(100, 116, 139);
    public static final Color BORDER_COLOR   = new Color(203, 213, 225);
    public static final Color TABLE_HEADER   = new Color(241, 245, 249);
    public static final Color TABLE_SELECTED = new Color(219, 234, 254);
    public static final Color WHITE          = Color.WHITE;

    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  20);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD,  14);
    public static final Font FONT_LABEL   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_INPUT   = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BUTTON  = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_TABLE   = new Font("Segoe UI", Font.PLAIN, 13);

    private UITheme() {}

    public static void applyNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("control",         SURFACE);
                    UIManager.put("nimbusBase",      PRIMARY);
                    UIManager.put("nimbusBlueGrey",  new Color(148, 163, 184));
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    public static JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(110, 34));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            final Color original = bg;
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(original.darker());
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(original);
            }
        });
        return btn;
    }

    public static JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(FONT_INPUT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return tf;
    }

    public static JTextArea createTextArea(int rows, int cols) {
        JTextArea ta = new JTextArea(rows, cols);
        ta.setFont(FONT_INPUT);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return ta;
    }

    public static Border titledBorder(String title) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR),
                        title,
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        FONT_HEADING, TEXT_PRIMARY),
                BorderFactory.createEmptyBorder(8, 12, 12, 12));
    }

    public static void styleTableHeader(JTable table) {
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(TABLE_HEADER);
        table.getTableHeader().setForeground(TEXT_PRIMARY);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(28);
        table.setFont(FONT_TABLE);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(TABLE_SELECTED);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
}
