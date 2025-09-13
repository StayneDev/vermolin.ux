# Caso de Uso: Manter Produto

**ID:** UC05
**Nome:** Manter Produto
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Este caso de uso descreve como o Gerente pode cadastrar, editar, excluir ou listar produtos no sistema.

---

## Pré-condições
- O Gerente deve estar autenticado no sistema.

---

## Pós-condições
- O registro do produto será criado, atualizado, excluído ou listado no banco de dados.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia quando o Gerente seleciona a opção "Manter Produto" no sistema.
2. O sistema exibe a tela de gerenciamento de produtos, com opções para: Cadastrar Novo, Editar, Excluir e Listar Produtos.

### Sub-fluxo: Cadastrar Novo Produto
2.1. O Gerente clica em "Cadastrar Novo".
2.2. O sistema exibe um formulário de cadastro de produto.
2.3. O Gerente preenche os dados do produto (nome, código/SKU, preço de compra, preço de venda, estoque mínimo, estoque máximo, categoria, etc.).
2.4. O Gerente clica em "Salvar".
2.5. O sistema valida os dados e registra o novo produto no banco de dados.
2.6. O sistema exibe a mensagem "Produto cadastrado com sucesso".

### Sub-fluxo: Editar Produto
2.1. O Gerente seleciona um produto da lista e clica em "Editar".
2.2. O sistema exibe o formulário com os dados atuais do produto.
2.3. O Gerente altera os dados desejados.
2.4. O Gerente clica em "Salvar".
2.5. O sistema valida os dados e atualiza o registro do produto no banco de dados.
2.6. O sistema exibe a mensagem "Produto atualizado com sucesso".

### Sub-fluxo: Excluir Produto
2.1. O Gerente seleciona um produto da lista e clica em "Excluir".
2.2. O sistema solicita confirmação da exclusão.
2.3. O Gerente confirma a exclusão.
2.4. O sistema remove o registro do produto do banco de dados.
2.5. O sistema exibe a mensagem "Produto excluído com sucesso".

### Sub-fluxo: Listar Produtos
2.1. O sistema exibe uma lista de todos os produtos cadastrados, com seus principais dados.

---

## Fluxos Alternativos
**A1 – Dados Inválidos (Cadastro/Edição)**
- Se algum campo obrigatório não for preenchido ou estiver inválido, o sistema exibe mensagem de erro.
- O Gerente corrige os dados e tenta novamente.

**A2 – Produto Não Encontrado (Edição/Exclusão)**
- Se o produto selecionado para edição ou exclusão não for encontrado, o sistema exibe uma mensagem de erro.

**A3 – Cancelamento da Operação**
- A qualquer momento, o Gerente pode cancelar a operação e retornar à tela anterior.

---

## Regras de Negócio
- Cada produto deve ter um código único (SKU).
- O preço de compra e venda devem ser números não negativos.
- A quantidade em estoque, estoque mínimo e máximo devem ser números não negativos.
- Apenas o Gerente tem permissão para manter produtos.

---

## Exceções
- Se houver falha de conexão com o banco de dados, o sistema informa o erro e a operação não é concluída.

---

## Extensões
- Integração com módulo de fornecedores para associar produtos a fornecedores.
- Geração de relatórios de produtos (ex: produtos mais vendidos, produtos com baixo estoque).

