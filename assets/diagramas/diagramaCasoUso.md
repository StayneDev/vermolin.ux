# Diagrama de Caso de Uso — Sistema Hortifruti

```mermaid
flowchart TD
    %% Funcionários
    Funcionario[Funcionario]
    Caixa[Caixa]
    Estoquista[Estoquista]
    Gerente[Gerente]

    %% Produtos e Usuários
    Produto[Produto]
    Usuario[Usuario]
    Fornecedor[Fornecedor]

    %% Herança
    Funcionario --> Caixa
    Funcionario --> Estoquista
    Funcionario --> Gerente

    %% Associações
    Caixa --> Produto
    Estoquista --> Produto
    Gerente --> Produto
    Gerente --> Usuario
    Gerente --> Fornecedor

    %% Funções (nomes sem parênteses)
    subgraph FuncoesCaixa [Funções do Caixa]
        consultarPreco
        abrirTransacaoVenda
        adicionarProduto
        removerProduto
        pesarProduto
        cancelarVenda
        registrarFormaPagamento
        confirmarPagamento
        calcularTroco
        registrarVenda
    end
    Caixa --> FuncoesCaixa

    subgraph FuncoesEstoquista [Funções do Estoquista]
        registrarEntradaProduto
        registrarSaidaProduto
        editarEstoque
        consultarProduto
    end
    Estoquista --> FuncoesEstoquista

    subgraph FuncoesGerente [Funções do Gerente]
        crudProduto
        crudUsuario
        atribuirCargo
        crudFornecedor
        receberNotificacaoBaixaEstoque
        contatarFornecedor
    end
    Gerente --> FuncoesGerente
