<h1 align="center">
<p>Sistema de Gerenciamento de Biblioteca</p>
</h1>

### Resumo do Projeto:
O Sistema de Gerenciamento de Biblioteca é um projeto desenvolvido para atender às necessidades de uma pequena biblioteca comunitária localizada em um bairro tranquilo. O sistema visa simplificar e automatizar as operações relacionadas ao gerenciamento de livros, empréstimos, devoluções e reservas. Ele permitirá que os usuários cadastrem livros, pesquisem por títulos, autores e categorias, realizem empréstimos e devoluções com facilidade.

### Descrição do Projeto:
A biblioteca comunitária que servia a um bairro tranquilo enfrentava desafios crescentes à medida que o número de livros e usuários aumentava rapidamente. O gerenciamento manual baseado em fichas de papel e planilhas se tornou insuficiente para lidar com a quantidade crescente de atividades. A bibliotecária, Sra. Garcia, percebeu a necessidade de um sistema informatizado para simplificar suas tarefas e melhorar a experiência dos usuários.

O novo Sistema de Gerenciamento de Biblioteca permitirá à Sra. Garcia adicionar novos livros ao catálogo, registrar informações detalhadas, como título, autor, editora, ISBN, ano de publicação e categoria, e verificar a disponibilidade dos exemplares. Os usuários terão acesso a um computador, onde poderão pesquisar, reservar livros e fazer empréstimos.

Com o sistema em funcionamento, a Sra. Garcia poderá concentrar seus esforços em melhorar a experiência dos usuários, planejando eventos literários, expandindo a variedade de livros disponíveis e criando um ambiente acolhedor para a comunidade.

### Requisitos do Projeto:
A Sra. Garcia estabeleceu os seguintes requisitos para o sistema:

1. **Registro de Livros:** O sistema deve permitir o registro de novos livros, incluindo informações como título, autor, editora, ISBN, ano de publicação e categoria.

2. **Pesquisa de Livros:** Os usuários devem ser capazes de pesquisar livros por título, autor, ISBN ou categoria para encontrar informações sobre disponibilidade, localização e outros detalhes.

3. **Empréstimo e Devolução:** O sistema deve permitir o registro de empréstimos de livros para os usuários da biblioteca, incluindo a data de empréstimo, data de devolução esperada e a identificação do usuário que realizou o empréstimo. Além disso, o sistema deve permitir o registro da devolução dos livros.

4. **Reserva de Livros:** Os usuários devem ter a opção de reservar livros que estejam emprestados por outros usuários. As reservas devem ser registradas por ordem de solicitação.

5. **Renovação de Empréstimos:** O sistema deve permitir a renovação dos empréstimos de livros, desde que não haja outras reservas para o mesmo livro e o limite de renovações não tenha sido atingido.

6. **Controle de Usuários:** O sistema deve permitir o cadastro de novos usuários, com informações como nome, endereço, telefone e número de identificação. Deve ser possível bloquear uma conta, impedindo que o usuário faça empréstimos e renovações.

7. **Relatórios e Estatísticas:** O sistema deve ser capaz de gerar relatórios e estatísticas, como o número de livros emprestados, atrasados e reservados, histórico de empréstimos de um usuário específico e os livros mais populares.

8. **Gerenciamento de Multas:** O sistema deve calcular e registrar multas por atraso na devolução de livros, multando o usuário com o dobro dos dias em atraso.

9. **Gerenciamento de Acervo:** O sistema deve permitir o gerenciamento do acervo da biblioteca, incluindo adição, remoção e atualização de informações sobre os livros, além do controle de estoque.

10. **Controle de Operadores do Sistema:** O sistema deve permitir o cadastro de novos operadores, com informações como nome, número de identificação, cargo e senha de acesso. Os cargos podem ser do tipo Administrador ou Bibliotecário, onde os Bibliotecários terão acesso limitado às funcionalidades.

### Requisitos de Desenvolvimento (Obrigatório):
- O versionamento de código será realizado no GitHub.
- Deve ser adotada uma padronização de commits.
- A entrega do código deve ser feita através da criação de releases contendo:
    - Tag: número da versão (ex: v2.0)
    - Título: nome da fase (ex: Fase 2 - Implementação e testes)
    - Descrição: descrição de todos os requisitos desenvolvidos até o momento.
- O código fonte deve ser implementado na IDE IntelliJ IDEA, facilitando a integração com o JavaFX e o gerenciamento de dependências.
- Inicialmente, o sistema deve conter dados fictícios gerados por ferramentas como generatedata.com.

### Produto:
- O projeto pode ser desenvolvido individualmente ou em dupla.
- Os artefatos gerados em cada fase de desenvolvimento devem ser entregues conforme orientações do tutor.
- O código fonte deve ser implementado na última versão estável do Java, utilizando o JavaFX.

### Observações:
- A entrega fora do prazo definido resultará em descontos na nota.
- Certifique-se de adicionar a declaração de autoria do código nas classes desenvolvidas.
- Todas as classes, atributos e métodos devem estar documentados usando o padrão Javadoc.
- A correção será baseada na análise do código e na documentação Javadoc.
- O produto entregue corresponderá a 75% da nota, enquanto o desempenho nos tutoriais corresponderá a 25% da nota. A não entrega do produto resultará em nota zero nos tutoriais.

### Artefatos e Prazos de Entrega:
- Fase 1 - Diagramação, Implementação e Testes (Backend):
    - Diagrama de Casos de Uso
    - Diagrama de Classes
    - Padrão de projeto DAO (Data Access Object) para o CRUD
    - Testes de unidade e integração 

- Fase 2 - Persistência de dados 
    - Armazenamento dos dados em disco.
- Fase 3 - Implementação da interface gráfica (frontend)
      - Construção da interface gráfica com JavaFX 
