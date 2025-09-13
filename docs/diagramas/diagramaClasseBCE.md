```mermaid
classDiagram
  direction LR

  subgraph Produto - BCE
    class ProdutoController {
      + cadastrarProduto(produto: Produto): Produto
      + consultarProduto(id: int): Produto
      + atualizarProduto(produto: Produto): Produto
      + excluirProduto(id: int): void
    }

    class ProdutoService {
      + criarProduto(produto: Produto): Produto
      + buscarProduto(id: int): Produto
      + atualizarProduto(produto: Produto): Produto
      + deletarProduto(id: int): void
      + validarProduto(produto: Produto): boolean
    }

    class ProdutoRepository {
      + save(produto: Produto): Produto
      + findById(id: int): Produto
      + update(produto: Produto): Produto
      + delete(id: int): void
    }

    class Produto {
      + id: int
      + nome: string
      + categoria: string
      + unidade_medida: UnidadeMedida
      + valor_unitario: decimal
    }

    ProdutoController --o ProdutoService : usa
    ProdutoService --o ProdutoRepository : usa
    ProdutoService --o Produto : manipula
    ProdutoRepository --o Produto : gerencia

    %% Enum for UnidadeMedida
    class UnidadeMedida {
      <<enumeration>>
      KG
      LITRO
      UNIDADE
      CAIXA
    }
    Produto -- UnidadeMedida : tem
  end
