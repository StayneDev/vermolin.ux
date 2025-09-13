# Caso de Uso: Realizar Login

**ID:** UC01
**Nome:** Realizar Login
**Ator Principal:** Usuário (Caixa, Gerente)
**Atores Secundários:** Sistema

---

## Descrição
Este caso de uso descreve como um usuário (Caixa ou Gerente) acessa o sistema utilizando suas credenciais válidas.

---

## Pré-condições
- O usuário deve possuir um cadastro válido no sistema.

---

## Pós-condições
- O usuário estará autenticado no sistema e terá acesso às funcionalidades permitidas pelo seu perfil.

---

## Fluxo Principal (Cenário de Sucesso)
1. O caso de uso inicia quando o usuário acessa a tela de login do sistema.
2. O sistema exibe os campos para inserção de credenciais (usuário e senha).
3. O usuário informa seu nome de usuário e senha.
4. O usuário clica no botão "Entrar" ou pressiona Enter.
5. O sistema valida as credenciais informadas.
6. Se as credenciais forem válidas, o sistema redireciona o usuário para a página inicial do sistema, de acordo com seu perfil de acesso.
7. O caso de uso é encerrado com sucesso.

---

## Fluxos Alternativos
**A1 – Credenciais Inválidas**
- Se as credenciais informadas não forem válidas (usuário ou senha incorretos), o sistema exibe uma mensagem de erro: "Usuário ou senha inválidos. Tente novamente."
- O usuário pode tentar novamente a partir do passo 3.

**A2 – Campos Vazios**
- Se o usuário tentar realizar o login com campos de usuário ou senha vazios, o sistema exibe uma mensagem de erro: "Por favor, preencha todos os campos."
- O usuário pode preencher os campos e tentar novamente a partir do passo 3.

---

## Regras de Negócio
- As credenciais de acesso (usuário e senha) devem ser únicas para cada usuário.
- O sistema deve diferenciar perfis de acesso (Caixa, Gerente) e conceder permissões de acordo.

---

## Exceções
- Se houver falha de conexão com o banco de dados de autenticação, o sistema informa o erro e o login não é concluído.

---

## Extensões
- Integração com módulo de recuperação de senha (para usuários que esqueceram a senha).
- Integração com módulo de bloqueio de conta após múltiplas tentativas falhas de login.

