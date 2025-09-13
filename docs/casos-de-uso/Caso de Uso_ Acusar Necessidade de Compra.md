# Caso de Uso: Acusar Necessidade de Compra

**ID:** UC10
**Nome:** Acusar Necessidade de Compra
**Ator Principal:** Sistema
**Atores Secundários:** Gerente

---

## Descrição
Este caso de uso descreve como o sistema alerta o Gerente quando produtos atingem a quantidade mínima no estoque, indicando a necessidade de compra.

---

## Pré-condições
- Os produtos devem ter um estoque mínimo configurado.
- O estoque atual de um produto deve ser verificado periodicamente ou após movimentações.

---

## Pós-condições
- O Gerente será notificado sobre os produtos com estoque abaixo do mínimo.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia automaticamente, acionado por um evento (ex: após uma venda que reduza o estoque, ou por uma verificação agendada).
2. O sistema verifica o estoque atual de um produto e compara com seu estoque mínimo configurado.
3. Se o estoque atual for menor ou igual ao estoque mínimo, o sistema identifica a necessidade de compra.
4. O sistema gera uma notificação (ex: alerta na interface, e-mail, mensagem) para o Gerente, informando qual produto e a quantidade atual.
5. O caso de uso é encerrado com sucesso.

---

## Fluxos Alternativos
**A1 – Estoque Acima do Mínimo**
- Se o estoque atual for maior que o estoque mínimo, nenhuma ação é tomada e o caso de uso é encerrado.

**A2 – Falha na Notificação**
- Se houver falha ao enviar a notificação ao Gerente (ex: erro de e-mail), o sistema registra o erro e pode tentar novamente mais tarde ou usar um método alternativo.

---

## Regras de Negócio
- A notificação deve ser acionada apenas quando o estoque atual atingir ou ficar abaixo do estoque mínimo.
- O sistema deve evitar notificações duplicadas para o mesmo produto em um curto período.

---

## Exceções
- Se houver falha de conexão com o banco de dados, o sistema não consegue verificar o estoque e registrar a necessidade de compra.

---

## Extensões
- Integração com módulo de sugestão de compra, que sugere a quantidade ideal a ser comprada com base no histórico de vendas.
- Geração automática de pedidos de compra para fornecedores.

