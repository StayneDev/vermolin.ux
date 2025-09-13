# Caso de Uso: Atualizar Estoque

**ID:** UC09
**Nome:** Atualizar Estoque
**Ator Principal:** Sistema
**Atores Secundários:** Nenhum

---

## Descrição
Este caso de uso descreve como o sistema ajusta automaticamente o estoque dos produtos após vendas ou reposições.

---

## Pré-condições
- Uma venda deve ter sido concluída ou uma reposição de estoque deve ter sido registrada.

---

## Pós-condições
- O estoque do produto afetado será atualizado no banco de dados.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia automaticamente quando uma venda é finalizada ou uma reposição de estoque é registrada.
2. O sistema identifica o(s) produto(s) envolvido(s) na transação (venda ou reposição).
3. Para cada produto, o sistema calcula a nova quantidade em estoque (decrementa para vendas, incrementa para reposições).
4. O sistema atualiza o campo 'estoque_atual' do(s) produto(s) no banco de dados.
5. O caso de uso é encerrado com sucesso.

---

## Fluxos Alternativos
**A1 – Erro na Atualização**
- Se ocorrer um erro durante a atualização do estoque no banco de dados, o sistema registra o erro e tenta reverter a transação original (venda ou reposição) se possível.
- O sistema pode notificar um administrador sobre a falha.

---

## Regras de Negócio
- A atualização do estoque deve ser atômica e consistente.
- O estoque não pode ser negativo após uma venda.

---

## Exceções
- Se houver falha de conexão com o banco de dados, o sistema não consegue atualizar o estoque e registra o erro.

---

## Extensões
- Nenhuma extensão direta, pois é um processo interno do sistema.

