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

    %% Notas sobre funções (opcional)
    subgraph FuncoesCaixa [Funções do Caixa]
        consultarPreco[consultarPreco()]
        abrirTransacaoVenda[abrirTransacaoVenda()]
        adicionarProduto[adicionarProduto()]
        removerProduto[removerProduto()]
        pesarProduto[pesarProduto()]
        cancelarVenda[cancelarVenda()]
        registrarFormaPagamento[registrarFormaPagamento()]
        confirmarPagamento[confirmarPagamento()]
        calcularTroco[calcularTroco()]
        registrarVenda[registrarVenda()]
    end

    Caixa --> FuncoesCaixa

    subgraph FuncoesEstoquista [Funções do Estoquista]
        registrarEntradaProduto[registrarEntradaProduto()]
        registrarSaidaProduto[registrarSaidaProduto()]
        editarEstoque[editarEstoque()]
        consultarProduto[consultarProduto()]
    end

    Estoquista --> FuncoesEstoquista

    subgraph FuncoesGerente [Funções do Gerente]
        crudProduto[crudProduto()]
        crudUsuario[crudUsuario()]
        atribuirCargo[atribuirCargo()]
        crudFornecedor[crudFornecedor()]
        receberNotificacaoBaixaEstoque[receberNotificacaoBaixaEstoque()]
        contatarFornecedor[contatarFornecedor()]
    end

    Gerente --> FuncoesGerente
