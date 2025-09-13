```mermaid
sequenceDiagram
  actor Funcionario
  participant ProdutoController
  participant ProdutoService
  participant ProdutoRepository
  participant Produto

  Funcionario->>ProdutoController: 1. Solicita edição de Produto (idProduto)
  ProdutoController->>ProdutoService: 2. buscarProduto(idProduto)
  ProdutoService->>ProdutoRepository: 3. findById(idProduto)
  ProdutoRepository-->>ProdutoService: 4. Retorna Produto (existente)
  ProdutoService-->>ProdutoController: 5. Retorna Produto (existente)
  ProdutoController-->>Funcionario: 6. Exibe formulário de edição com dados do Produto

  Funcionario->>ProdutoController: 7. Informa novos dados do Produto e confirma
  ProdutoController->>ProdutoService: 8. atualizarProduto(produtoAtualizado)

  alt Validação de Dados
    ProdutoService->>ProdutoService: 9. validarProduto(produtoAtualizado)
    alt Dados Inválidos
      ProdutoService-->>ProdutoController: 10. Retorna Erro de Validação
      ProdutoController-->>Funcionario: 11. Exibe mensagem de erro
    else Dados Válidos
      ProdutoService->>ProdutoRepository: 12. update(produtoAtualizado)
      ProdutoRepository-->>ProdutoService: 13. Retorna Produto (atualizado)
      ProdutoService-->>ProdutoController: 14. Retorna Produto (atualizado)
      ProdutoController-->>Funcionario: 15. Exibe mensagem "Produto atualizado com sucesso"
    end
  end
