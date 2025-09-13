# Caso de Uso: Gerenciar Fornecimento

**ID:** UC06
**Nome:** Gerenciar Fornecimento
**Ator Principal:** Gerente
**Atores Secundários:** Sistema, Fornecedor

---

## Descrição
Este caso de uso descreve como o Gerente pode controlar fornecedores e o relacionamento com os produtos no sistema.

---

## Pré-condições
- O Gerente deve estar autenticado no sistema.

---

## Pós-condições
- O registro do fornecedor será criado, atualizado, excluído ou listado no banco de dados.
- O relacionamento entre fornecedores e produtos será mantido.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia quando o Gerente seleciona a opção "Gerenciar Fornecimento" no sistema.
2. O sistema exibe a tela de gerenciamento de fornecedores, com opções para: Cadastrar Novo, Editar, Excluir, Listar Fornecedores e Associar Produtos.

### Sub-fluxo: Cadastrar Novo Fornecedor
2.1. O Gerente clica em "Cadastrar Novo".
2.2. O sistema exibe um formulário de cadastro de fornecedor.
2.3. O Gerente preenche os dados do fornecedor (nome, CNPJ, contato, endereço, etc.).
2.4. O Gerente clica em "Salvar".
2.5. O sistema valida os dados e registra o novo fornecedor no banco de dados.
2.6. O sistema exibe a mensagem "Fornecedor cadastrado com sucesso".

### Sub-fluxo: Editar Fornecedor
2.1. O Gerente seleciona um fornecedor da lista e clica em "Editar".
2.2. O sistema exibe o formulário com os dados atuais do fornecedor.
2.3. O Gerente altera os dados desejados.
2.4. O Gerente clica em "Salvar".
2.5. O sistema valida os dados e atualiza o registro do fornecedor no banco de dados.
2.6. O sistema exibe a mensagem "Fornecedor atualizado com sucesso".

### Sub-fluxo: Excluir Fornecedor
2.1. O Gerente seleciona um fornecedor da lista e clica em "Excluir".
2.2. O sistema solicita confirmação da exclusão.
2.3. O Gerente confirma a exclusão.
2.4. O sistema remove o registro do fornecedor do banco de dados.
2.5. O sistema exibe a mensagem "Fornecedor excluído com sucesso".

### Sub-fluxo: Listar Fornecedores
2.1. O sistema exibe uma lista de todos os fornecedores cadastrados, com seus principais dados.

### Sub-fluxo: Associar Produtos a Fornecedor
2.1. O Gerente seleciona um fornecedor da lista e clica em "Associar Produtos".
2.2. O sistema exibe uma lista de produtos disponíveis e produtos já associados ao fornecedor.
2.3. O Gerente seleciona/desseleciona os produtos para associar/desassociar.
2.4. O Gerente clica em "Salvar Associações".
2.5. O sistema atualiza as associações entre produtos e fornecedores no banco de dados.
2.6. O sistema exibe a mensagem "Associações atualizadas com sucesso".

---

## Fluxos Alternativos
**A1 – Dados Inválidos (Cadastro/Edição)**
- Se algum campo obrigatório não for preenchido ou estiver inválido, o sistema exibe mensagem de erro.
- O Gerente corrige os dados e tenta novamente.

**A2 – Fornecedor Não Encontrado (Edição/Exclusão/Associação)**
- Se o fornecedor selecionado não for encontrado, o sistema exibe uma mensagem de erro.

**A3 – Cancelamento da Operação**
- A qualquer momento, o Gerente pode cancelar a operação e retornar à tela anterior.

---

## Regras de Negócio
- Cada fornecedor deve ter um identificador único (ex: CNPJ).
- Um produto pode ser fornecido por múltiplos fornecedores.
- Apenas o Gerente tem permissão para gerenciar fornecedores.

---

## Exceções
- Se houver falha de conexão com o banco de dados, o sistema informa o erro e a operação não é concluída.

---

## Extensões
- Geração de relatórios de fornecedores (ex: produtos fornecidos por cada fornecedor).
- Integração com módulo de pedidos de compra para automatizar o processo de reposição.

