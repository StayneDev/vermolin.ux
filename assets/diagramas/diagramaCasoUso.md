# Diagrama de Caso de Uso — Sistema Hortifruti

```mermaid
%%{init: {"theme": "default"}}%%
usecaseDiagram
    actor Cliente as C
    actor "Caixa / Operador" as Caixa
    actor Estoquista as Estoque
    actor Fornecedor as Forn
    actor Administrador as Admin
    actor Entregador as Entrega

    C --> (Navegar Catálogo)
    C --> (Ver Detalhes do Produto)
    C --> (Adicionar ao Carrinho)
    C --> (Escolher Unidade: kg/unidade)
    C --> (Finalizar Pedido)
    C --> (Pagar Pedido)
    C --> (Solicitar Entrega)
    C --> (Avaliar Pedido)

    Caixa --> (Registrar Venda Presencial)
    Caixa --> (Emitir Cupom / Nota Fiscal)
    Caixa --> (Processar Pagamento)
    Caixa --> (Aplicar Desconto / Promoção)

    Estoque --> (Consultar Níveis de Estoque)
    Estoque --> (Registrar Entrada de Mercadoria)
    Estoque --> (Ajustar Estoque)
    Estoque --> (Gerenciar Lotes / Validade)

    Forn --> (Enviar Pedido de Reposição)
    Forn --> (Confirmar Entrega de Fornecimento)

    Admin --> (Gerenciar Produtos)
    Admin --> (Gerenciar Usuários e Permissões)
    Admin --> (Gerar Relatórios: Vendas / Estoque / Financeiro)
    Admin --> (Configurar Unidades de Venda)
    Admin --> (Configurar Formas de Pagamento)

    Entrega --> (Receber Pedido para Entrega)
    Entrega --> (Confirmar Entrega ao Cliente)

    (Adicionar ao Carrinho) ..> (Escolher Unidade: kg/unidade) : <<include>>
    (Finalizar Pedido) ..> (Processar Pagamento) : <<include>>
    (Registrar Venda Presencial) ..> (Emitir Cupom / Nota Fiscal) : <<include>>
    (Registrar Entrada de Mercadoria) ..> (Ajustar Estoque) : <<include>>
    (Gerenciar Produtos) ..> (Configurar Unidades de Venda) : <<include>>
    (Gerar Relatórios: Vendas / Estoque / Financeiro) <-- (Emitir Cupom / Nota Fiscal) : <<extend>>
