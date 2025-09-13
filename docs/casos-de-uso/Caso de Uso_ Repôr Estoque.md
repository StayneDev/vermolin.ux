# Caso de Uso: Repôr Estoque

**ID:** UC07
**Nome:** Repôr Estoque
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Este caso de uso descreve como o Gerente adiciona itens ao estoque manualmente (entrada de mercadorias) no sistema.

---

## Pré-condições
- O Gerente deve estar autenticado no sistema.
- Os produtos a serem repostos devem estar cadastrados no sistema.

---

## Pós-condições
- O estoque dos produtos será atualizado no sistema.
- Um registro da entrada de mercadorias será criado.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia quando o Gerente seleciona a opção "Repôr Estoque" no sistema.
2. O sistema exibe a interface de reposição de estoque.
3. O Gerente informa o código ou nome do produto a ser reposto.
4. O sistema exibe os detalhes do produto (nome, estoque atual).
5. O Gerente informa a quantidade a ser adicionada ao estoque.
6. O Gerente pode informar o número da nota fiscal de entrada (opcional).
7. O Gerente clica em "Confirmar Reposição".
8. O sistema valida os dados e atualiza o estoque do produto, adicionando a quantidade informada.
9. O sistema registra a movimentação de entrada de estoque.
10. O sistema exibe a mensagem "Estoque reposto com sucesso".
11. O caso de uso é encerrado com sucesso.

---

## Fluxos Alternativos
**A1 – Produto não encontrado**
- Se o produto informado não for encontrado, o sistema exibe uma mensagem de erro: "Produto não encontrado."
- O Gerente pode tentar novamente ou buscar outro produto.

**A2 – Quantidade Inválida**
- Se a quantidade informada for zero ou negativa, o sistema exibe uma mensagem de erro: "Quantidade inválida. Informe um valor positivo."
- O Gerente corrige a quantidade e tenta novamente.

**A3 – Cancelamento da Operação**
- A qualquer momento, o Gerente pode cancelar a operação e retornar à tela anterior.

---

## Regras de Negócio
- A quantidade adicionada deve ser um número positivo.
- O sistema deve registrar a data e hora da reposição, o produto, a quantidade e o responsável.

---

## Exceções
- Se houver falha de conexão com o banco de dados, o sistema informa o erro e a reposição não é concluída.

---

## Extensões
- Integração com módulo de gerenciamento de fornecedores para vincular a reposição a um fornecedor específico.
- Geração de relatórios de movimentação de estoque.

