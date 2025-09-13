# Caso de Uso: Consultar Produto

**ID:** UC03
**Nome:** Consultar Produto
**Ator Principal:** Caixa, Gerente
**Atores Secundários:** Sistema

---

## Descrição
Este caso de uso descreve como um usuário (Caixa ou Gerente) consulta informações detalhadas de um produto no sistema.

---

## Pré-condições
- O usuário (Caixa ou Gerente) deve estar autenticado no sistema.
- O produto a ser consultado deve estar cadastrado no sistema.

---

## Pós-condições
- As informações detalhadas do produto serão exibidas ao usuário.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia quando o usuário seleciona a opção "Consultar Produto" no sistema.
2. O sistema exibe um campo de busca para o produto.
3. O usuário informa o nome, código (SKU) ou parte do nome do produto que deseja consultar.
4. O usuário clica no botão "Buscar" ou pressiona Enter.
5. O sistema pesquisa o produto no banco de dados.
6. Se o produto for encontrado, o sistema exibe as informações detalhadas do produto, como: nome, descrição, categoria, unidade de medida, preço de compra, preço de venda, quantidade em estoque, estoque mínimo e máximo.
7. O caso de uso é encerrado com sucesso.

---

## Fluxos Alternativos
**A1 – Produto não encontrado**
- Se o produto não for encontrado com base nos critérios de busca, o sistema exibe uma mensagem de erro: "Produto não encontrado. Verifique os dados e tente novamente."
- O usuário pode refinar a busca ou tentar novamente.

**A2 – Busca sem resultados**
- Se a busca retornar múltiplos resultados que correspondam parcialmente ao critério, o sistema pode exibir uma lista de produtos correspondentes para que o usuário selecione o desejado.
- O usuário seleciona o produto desejado e o sistema exibe suas informações detalhadas.

---

## Regras de Negócio
- A busca de produtos deve ser flexível, permitindo pesquisa por nome, código ou parte do nome.
- As informações exibidas devem ser completas e atualizadas.

---

## Exceções
- Se houver falha de conexão com o banco de dados, o sistema informa o erro e a consulta não é concluída.

---

## Extensões
- Integração com módulo de histórico de vendas do produto.
- Integração com módulo de fornecedores para exibir informações do fornecedor do produto.

