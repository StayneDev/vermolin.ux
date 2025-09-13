# vemolin.ux

estrutura de pastas

meu-projeto/
│
├── docs/                          # Documentação
│   ├── requisitos/                # Documentos de requisitos
│   │   └── requisitos-funcionais.md
│   │
│   ├── casos-de-uso/              # Casos de uso
│   │   ├── UC01-venda-produto.md
│   │   ├── UC02-gerenciar-estoque.md
│   │   └── ...
│   │
│   └── diagramas/                 # Diagramas UML
│       ├── caso-de-uso/
│       │   └── ...
│       ├── classe/
│       │   └── ...
│       └── sequencia/
│           └── ...
│   
│
├── src/                           # Código-fonte
│   ├── boundary/                  # Interfaces com o usuário (controllers, APIs, UI)
│   │   ├── controllerUsuario.java
│   │   ├── controllerProduto.java
│   │   └── ...
│   │
│   ├── control/                   # Lógica de negócio (services)
│   │   ├── serviceUsuario.java
│   │   ├── serviceProduto.java
│   │   └── ...
│   │
│   ├── entity/                    # Entidades de domínio
│   │   ├── Usuario.java
│   │   ├── Produto.java
│   │   ├── Venda.java
│   │   └── RegistroEstoque.java
│   │
│   ├── repository/                # Persistência de dados
│   │   ├── repositoryUsuario.java
│   │   ├── repositoryProduto.java
│   │   └── ...
│   │
│   └── Main.java                  # Ponto de entrada da aplicação
│
├── test/                          # Testes unitários e de integração
│   ├── UsuarioTest.java
│   ├── ProdutoTest.java
│   └── ...
│
├── assets/                        # Recursos auxiliares do projeto
│   ├── imagens/                   # Logos, ícones, mockups de UI
│   ├── dados-exemplo/             # Arquivos CSV/JSON de exemplo
│   ├── scripts/                   # SQL de carga inicial, seeds
│   └── styles/                    # CSS, fontes ou templates de front-end (se aplicável)
│
├── build/                         # Arquivos gerados (build, bin, etc.)
│
├── .gitignore
├── README.md
└── LICENSE
