classDiagram
    direction LR

    %% -----------------------------------------------------------------------
    %% DOMÍNIO ENTIDADES (E) - Estrutura de Dados e Regras Internas
    %% -----------------------------------------------------------------------

    class Usuario {
        <<Entity>>
        + id: Long
        + nome: String
        + login: String
        + senhaHash: String
        + dataCadastro: LocalDateTime
        + autenticar(login, senha): Boolean
    }

    class Caixa {
        + iniciarVenda(): Venda
    }

    class Estoquista {
        + realizarContagemInventario()
    }

    class Gerente {
        + autorizarAjusteEstoque(movimentacao)
    }

    class Produto {
        <<Entity>>
        + id: Long
        + nome: String
        + unidadeMedida: Enum (KG, UNIDADE, MAÇO)
        + precoVendaAtual: Decimal
        + quantidadeAtual: Double (Estoque)
        + perecivel: Boolean
        + validarDisponibilidade(qtd): Boolean
    }

    class Venda {
        <<Entity>>
        + id: Long
        + dataHora: LocalDateTime
        + valorTotal: Decimal
        + tipoPagamento: Enum
        + calcularTotal(): Decimal
        + finalizarVenda()
    }

    class ItemVenda {
        <<Entity Detail>>
        + precoUnitarioVenda: Decimal
        + quantidadeVendida: Double
    }

    class RegistroEstoque {
        <<Entity Header>>
        + id: Long
        + motivoGeral: String
        + dataRegistro: LocalDateTime
    }

    class MovimentacaoEstoque {
        <<Entity Detail>>
        + quantidadeAlterada: Double
        + tipoMovimentacao: Enum (ENTRADA, SAIDA, PERDA)
        + dataMovimentacao: LocalDateTime
    }

    %% -----------------------------------------------------------------------
    %% RELACIONAMENTOS DO DOMÍNIO (ENTITIES)
    %% -----------------------------------------------------------------------
    Usuario <|-- Caixa: Generalização
    Usuario <|-- Estoquista: Generalização
    Usuario <|-- Gerente: Generalização

    Caixa "1" -- "0..*" Venda: processa
    Estoquista "1" -- "0..*" RegistroEstoque: executa
    Gerente "1" -- "0..*" RegistroEstoque: autoriza/supervisiona

    Venda "1" *-- "1..*" ItemVenda: Composição (Composição)
    ItemVenda "0..*" -- "1" Produto: referencia

    RegistroEstoque "1" *-- "1..*" MovimentacaoEstoque: Composição (Composição)
    MovimentacaoEstoque "0..*" -- "1" Produto: afeta
    MovimentacaoEstoque "0..*" -- "1" Usuario: responsavel_por

    %% -----------------------------------------------------------------------
    %% CAMADA CONTROLE (C) - Lógica de Negócio e Orquestração
    %% -----------------------------------------------------------------------

    class serviceUsuario {
        <<Control>>
        + criarUsuario(usuario)
        + validarAcesso(login, senha)
    }

    class serviceProduto {
        <<Control>>
        + salvarProduto(produto)
        + verificarEstoque(produtoId, qtd)
        + atualizarEstoqueAtomico(produtoId, delta)
    }

    class serviceVenda {
        <<Control>>
        + processarVenda(venda): Venda
        + estornarVenda(vendaId)
        -- Depende de --
    }

    class serviceRegistroEstoque {
        <<Control>>
        + registrarEntrada(registro): RegistroEstoque
        + registrarSaidaPorVenda(venda): MovimentacaoEstoque
        + registrarPerda(produto, qtd)
        -- Depende de --
    }

    %% -----------------------------------------------------------------------
    %% CAMADA BOUNDARY (B) e INFRA (REPOSITORY)
    %% -----------------------------------------------------------------------

    class controllerUsuario {
        <<Boundary>>
        + postLogin(request)
        + getUsuario(id)
    }

    class controllerProduto {
        <<Boundary>>
        + postProduto(request)
        + getProdutosDisponiveis()
    }

    class controllerVenda {
        <<Boundary>>
        + postNovaVenda(request)
        + getVenda(id)
    }

    class controllerRegistroEstoque {
        <<Boundary>>
        + postEntradaEstoque(request)
        + getHistorico(produtoId)
    }

    class repositoryUsuario {
        <<Repository>>
        + save(usuario)
        + findByLogin(login)
    }

    class repositoryProduto {
        <<Repository>>
        + save(produto)
        + findById(id)
    }

    class repositoryVenda {
        <<Repository>>
        + save(venda)
        + findById(id)
    }

    class repositoryRegistroEstoque {
        <<Repository>>
        + save(registro)
        + findByProduto(produtoId)
    }

    %% -----------------------------------------------------------------------
    %% FLUXO ARQUITETURAL E DEPENDÊNCIAS (B -> C -> E/R)
    %% -----------------------------------------------------------------------

    %% Controllers (B) dependem de Services (C)
    controllerUsuario -- serviceUsuario: usa
    controllerProduto -- serviceProduto: usa
    controllerVenda -- serviceVenda: usa
    controllerRegistroEstoque -- serviceRegistroEstoque: usa

    %% Services (C) dependem de Repositories
    serviceUsuario -- repositoryUsuario: usa
    serviceProduto -- repositoryProduto: usa
    serviceVenda -- repositoryVenda: usa
    serviceRegistroEstoque -- repositoryRegistroEstoque: usa

    %% Services (C) manipulam Entities (E)
    serviceUsuario -- Usuario: manipula
    serviceProduto -- Produto: manipula
    serviceVenda -- Venda: manipula
    serviceRegistroEstoque -- RegistroEstoque: manipula

    %% Orquestração (Inter-Service)
    serviceVenda..> serviceProduto: consulta/valida
    serviceVenda..> serviceRegistroEstoque: registra_saída

    serviceRegistroEstoque..> serviceProduto: atualiza_saldo
