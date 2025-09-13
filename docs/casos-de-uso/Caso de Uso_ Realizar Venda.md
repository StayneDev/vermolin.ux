# Caso de Uso: Realizar Venda

**ID:** UC02
**Nome:** Realizar Venda
**Ator Principal:** Caixa, Gerente
**Atores Secundários:** Sistema, Cliente

---

## Descrição
Este caso de uso descreve como um operador de caixa ou gerente registra a venda de produtos para um cliente no sistema.

---

## Pré-condições
- O usuário (Caixa ou Gerente) deve estar autenticado no sistema.
- Os produtos a serem vendidos devem estar cadastrados no sistema e com estoque disponível.

---

## Pós-condições
- A venda será registrada no sistema.
- O estoque dos produtos vendidos será atualizado.
- Um comprovante de venda (nota fiscal ou cupom) poderá ser gerado.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia quando o usuário seleciona a opção "Realizar Venda" no sistema.
2. O sistema exibe a interface de vendas.
3. O usuário adiciona produtos à venda, informando o código ou nome do produto e a quantidade desejada.
4. Para cada produto adicionado, o sistema exibe o nome, preço unitário, quantidade e subtotal.
5. O sistema calcula e exibe o valor total da venda.
6. O usuário informa a forma de pagamento (dinheiro, cartão, etc.).
7. O usuário confirma a venda.
8. O sistema processa o pagamento e registra a venda.
9. O sistema atualiza o estoque dos produtos vendidos.
10. O sistema gera um comprovante de venda e o exibe para impressão ou envio.
11. O caso de uso é encerrado com sucesso.

---

## Fluxos Alternativos
**A1 – Produto não encontrado**
- Se o produto informado não for encontrado, o sistema exibe uma mensagem de erro: "Produto não encontrado."
- O usuário pode tentar novamente ou buscar outro produto.

**A2 – Estoque insuficiente**
- Se a quantidade solicitada de um produto for maior que o estoque disponível, o sistema exibe uma mensagem de erro: "Estoque insuficiente para o produto [Nome do Produto]. Estoque disponível: [Quantidade]."
- O usuário pode ajustar a quantidade ou remover o produto da venda.

**A3 – Cancelamento da venda**
- A qualquer momento antes da confirmação final, o usuário pode cancelar a venda.
- O sistema limpa os itens da venda e retorna à tela principal de vendas.

---

## Regras de Negócio
- O valor total da venda deve ser a soma dos subtotais de todos os produtos.
- O estoque dos produtos deve ser decrementado após a confirmação da venda.
- O sistema deve permitir diferentes formas de pagamento.

---

## Exceções
- Se houver falha na comunicação com o sistema de pagamento, a venda não é concluída e o sistema informa o erro.
- Se houver falha na atualização do estoque, a venda é revertida e o sistema informa o erro.

---

## Extensões
- Integração com sistema de fidelidade para aplicar descontos ou acumular pontos.
- Integração com módulo de promoções para aplicar preços especiais automaticamente.

