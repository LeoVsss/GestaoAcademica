import connection.DbConnection;
import connection.PostgreConnection;
import dao.CursoDao;
import dao.DisciplinaDao;
import dao.ProfessorDao;
import service.CursoService;
import service.DisciplinaService;
import service.ProfessorService;
import service.impl.CursoServiceImpl;
import service.impl.DisciplinaServiceImpl;
import service.impl.ProfessorServiceImpl;
import ui.MainFrame;
import ui.util.UITheme;

import javax.swing.*;

/**
 * Ponto de entrada da aplicação.
 *
 * Composição de dependências (DIP):
 *   Connection → DAO → Service → Controller (injeção via construtor em cada camada).
 */
public class Main {

    public static void main(String[] args) {
        UITheme.applyNimbus();

        SwingUtilities.invokeLater(() -> {
            try {
                // Camada de infraestrutura
                DbConnection connection = new PostgreConnection();

                // DAOs
                CursoDao      cursoDao      = new CursoDao(connection);
                ProfessorDao  professorDao  = new ProfessorDao(connection);
                DisciplinaDao disciplinaDao = new DisciplinaDao(connection);

                // Serviços (interface ← implementação)
                CursoService      cursoService      = new CursoServiceImpl(cursoDao);
                ProfessorService  professorService  = new ProfessorServiceImpl(professorDao);
                DisciplinaService disciplinaService = new DisciplinaServiceImpl(disciplinaDao);

                // UI — recebe apenas interfaces de serviço
                MainFrame frame = new MainFrame(cursoService, professorService, disciplinaService);
                frame.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Erro ao conectar ao banco de dados:\n" + e.getMessage() +
                        "\n\nVerifique o arquivo .env com DB_URL, DB_USER e DB_PASSWORD.",
                        "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
