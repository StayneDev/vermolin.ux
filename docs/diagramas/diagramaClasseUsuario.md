classDiagram
    direction LR

    class Usuario {
        <<abstract>>
        + id: Long
        + nome: String
        + login: String
        + senhaHash: String
        + dataCadastro: LocalDateTime
        + autenticar(login, senha): Boolean
        + trocarSenha(novaSenha)
    }

    class Caixa {
        + iniciarVenda(): Venda
        + processarPagamento(venda)
    }

    class Estoquista {
        + registrarEntradaDeLote(produto, quantidade)
        + realizarContagemInventario()
    }

    class Gerente {
        + autorizarAjusteEstoque(movimentacao)
        + gerarRelatorioFinanceiro()
    }

    Usuario <|-- Caixa: Generalização
    Usuario <|-- Estoquista: Generalização
    Usuario <|-- Gerente: Generalização

    Caixa "1" --o "0..*" Venda: processa
    Estoquista "1" --o "0..*" RegistroEstoque: executa
    Gerente "1" --o "0..*" RegistroEstoque: autoriza
