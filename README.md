# Sistema de Gestão Acadêmica

Este é um sistema desktop desenvolvido em **Java** com interface gráfica (Swing), focado na gestão de **Cursos, Professores e Disciplinas**. O projeto foi construído aplicando princípios de arquitetura em camadas e injeção de dependências, garantindo um código limpo, testável e de fácil manutenção.

## Tecnologias Utilizadas
* **Linguagem:** Java
* **Interface Gráfica:** Swing (com tema Nimbus)
* **Banco de Dados:** PostgreSQL 
* **Gerenciamento de Configurações:** `dotenv-java` (para leitura segura de credenciais)

## Arquitetura do Projeto
O projeto segue uma separação clara de responsabilidades baseada no **Princípio da Inversão de Dependência (DIP)**:
* **Model:** Representação das entidades (`Curso`, `Professor`, `Disciplina`).
* **DAO (Data Access Object):** Gerenciamento da persistência de dados no PostgreSQL (operações de CRUD).
* **Service:** Camada de regras de negócio, intermediando a UI e os DAOs.
* **UI (User Interface):** Telas do sistema (`MainFrame`), que não acessam o banco de dados diretamente.

No ponto de entrada da aplicação (`Main.java`), as dependências são injetadas via construtor, seguindo o fluxo:
`DbConnection → DAO → Service → UI`

## Como Executar o Projeto
1. Clone este repositório em sua máquina local.
2. Na raiz do projeto, crie um arquivo `.env` com as configurações de conexão do seu banco de dados PostgreSQL:
   ```env
   DB_URL=jdbc:postgresql://localhost:5432/nome_do_seu_banco
   DB_USER=seu_usuario
   DB_PASSWORD=sua_senha
  

## Gestão do Projeto (Metodologia Kanban)
O desenvolvimento deste sistema foi planejado e guiado utilizando um Quadro Kanban no Trello. A metodologia visual permitiu mapear o fluxo de trabalho desde o Backlog do Projeto até a Conclusão.

Abaixo está o registro visual da evolução do ciclo de desenvolvimento, provando o trânsito dos cartões pelas etapas de construção, teste e entrega final:

1. Estruturação Inicial do Quadro
Criação dos cartões com base na arquitetura do sistema (Infra, DAOs, Services, UI).

![alt text](<Captura de tela 2026-05-25 160731.png>)

2. Puxando Tarefas para Desenvolvimento (Doing)
Início da implementação das camadas de Dados e Serviço, com a limpeza da interface do quadro.

3. Progresso Contínuo
Desenvolvimento das Models concluído (3/3 no checklist) e Infraestrutura de banco de dados em andamento. Início da fase de Teste e Revisão.

![alt text](<Captura de tela 2026-05-25 160744.png>)

4. Validação e Code Review
Camada de persistência (DAO) validada com sucesso e enviada para a coluna de "Feito", enquanto os ajustes de UI entram em revisão.

![alt text](<Captura de tela 2026-05-25 160815.png>)

5. Conclusão e Entrega Final (Done)
Todo o ciclo de desenvolvimento validado com sucesso. Todas as entregas das camadas do software foram finalizadas e integradas.

![alt text](<Captura de tela 2026-05-25 160823.png>)
