# Caso de Uso: Manter Funcionário

**ID:** UC04
**Nome:** Manter Funcionário
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Este caso de uso descreve como o Gerente pode cadastrar, editar, excluir ou listar funcionários do sistema.

---

## Pré-condições
- O Gerente deve estar autenticado no sistema.

---

## Pós-condições
- O registro do funcionário será criado, atualizado, excluído ou listado no banco de dados.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia quando o Gerente seleciona a opção "Manter Funcionário" no sistema.
2. O sistema exibe a tela de gerenciamento de funcionários, com opções para: Cadastrar Novo, Editar, Excluir e Listar Funcionários.

### Sub-fluxo: Cadastrar Novo Funcionário
2.1. O Gerente clica em "Cadastrar Novo".
2.2. O sistema exibe um formulário de cadastro de funcionário.
2.3. O Gerente preenche os dados do funcionário (nome, CPF, cargo, perfil de acesso, etc.).
2.4. O Gerente clica em "Salvar".
2.5. O sistema valida os dados e registra o novo funcionário no banco de dados.
2.6. O sistema exibe a mensagem "Funcionário cadastrado com sucesso".

### Sub-fluxo: Editar Funcionário
2.1. O Gerente seleciona um funcionário da lista e clica em "Editar".
2.2. O sistema exibe o formulário com os dados atuais do funcionário.
2.3. O Gerente altera os dados desejados.
2.4. O Gerente clica em "Salvar".
2.5. O sistema valida os dados e atualiza o registro do funcionário no banco de dados.
2.6. O sistema exibe a mensagem "Funcionário atualizado com sucesso".

### Sub-fluxo: Excluir Funcionário
2.1. O Gerente seleciona um funcionário da lista e clica em "Excluir".
2.2. O sistema solicita confirmação da exclusão.
2.3. O Gerente confirma a exclusão.
2.4. O sistema remove o registro do funcionário do banco de dados.
2.5. O sistema exibe a mensagem "Funcionário excluído com sucesso".

### Sub-fluxo: Listar Funcionários
2.1. O sistema exibe uma lista de todos os funcionários cadastrados, com seus principais dados.

---

## Fluxos Alternativos
**A1 – Dados Inválidos (Cadastro/Edição)**
- Se algum campo obrigatório não for preenchido ou estiver inválido, o sistema exibe mensagem de erro.
- O Gerente corrige os dados e tenta novamente.

**A2 – Funcionário Não Encontrado (Edição/Exclusão)**
- Se o funcionário selecionado para edição ou exclusão não for encontrado, o sistema exibe uma mensagem de erro.

**A3 – Cancelamento da Operação**
- A qualquer momento, o Gerente pode cancelar a operação e retornar à tela anterior.

---

## Regras de Negócio
- Cada funcionário deve ter um identificador único (ex: CPF).
- A exclusão de um funcionário pode exigir a desvinculação de registros relacionados (ex: vendas realizadas).
- Apenas o Gerente tem permissão para manter funcionários.

---

## Exceções
- Se houver falha de conexão com o banco de dados, o sistema informa o erro e a operação não é concluída.

---

## Extensões
- Integração com módulo de controle de ponto.
- Geração de relatórios de funcionários.

