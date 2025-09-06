# Diagrama de Caso de Uso — Sistema Hortifruti

```mermaid
flowchart LR
    %% Atores
    C[Cliente]
    Caixa[Caixa / Operador]
    Estoque[Estoquista]
    Forn[Fornecedor]
    Admin[Administrador]
    Entrega[Entregador]

    %% Casos de Uso Cliente
    C --> UC1((Navegar Catálogo))
    C --> UC2((Ver Detalhes do Produto))
    C --> UC3((Adicionar ao Carrinho))
    C --> UC4((Escolher Unidade: kg/unidade))
    C --> UC5((Finalizar Pedido))
    C --> UC6((Pagar Pedido))
    C --> UC7((Solicitar Entrega))
    C --> UC8((Avaliar Pedido))

    %% Casos de Uso Caixa
    Caixa --> UC9((Registrar Venda Presencial))
    Caixa --> UC10((Emitir Cupom / Nota Fiscal))
    Caixa --> UC11((Processar Pagamento))
    Caixa --> UC12((Aplicar Desconto / Promoção))

    %% Casos de Uso Estoque
    Estoque --> UC13((Consultar Níveis de Estoque))
    Estoque --> UC14((Registrar Entrada de Mercadoria))
    Estoque --> UC15((Ajustar Estoque))
    Estoque --> UC16((Gerenciar Lotes / Validade))

    %% Casos de Uso Fornecedor
    Forn --> UC17((Enviar Pedido de Reposição))
    Forn --> UC18((Confirmar Entrega de Fornecimento))

    %% Casos de Uso Admin
    Admin --> UC19((Gerenciar Produtos))
    Admin --> UC20((Gerenciar Usuários e Permissões))
    Admin --> UC21((Gerar Relatórios: Vendas / Estoque / Financeiro))
    Admin --> UC22((Configurar Unidades de Venda))
    Admin --> UC23((Configurar Formas de Pagamento))

    %% Casos de Uso Entregador
    Entrega --> UC24((Receber Pedido para Entrega))
    Entrega --> UC25((Confirmar Entrega ao Cliente))

    %% Relações de include/extend representadas como setas pontilhadas
    UC3 -.-> UC4
    UC5 -.-> UC6
    UC9 -.-> UC10
    UC14 -.-> UC15
    UC19 -.-> UC22
    UC21 -.-> UC10
