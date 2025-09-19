# Casos de Uso - Sistema de Gestão (Com Conexões)

## 1. Login

**ID:** UC01  
**Nome:** Login  
**Atores:**  
- **Primário:** Funcionário  
- **Secundário:** Sistema  

**Pré-condições:** O sistema está disponível na tela de login.  
**Pós-condições:** Sessão do usuário é iniciada e as permissões são carregadas.  

**Fluxo Principal:**
1. O Sistema exibe os campos de usuário e senha.
2. O Funcionário insere suas credenciais.
3. O Funcionário confirma os dados.
4. O Sistema valida as credenciais.
5. O Sistema inicia a sessão e redireciona para a página inicial.

**Fluxos Alternativos:**
- **A1. Credenciais Inválidas:** O Sistema exibe mensagem de erro e mantém-se na tela de login.

---

## 2. Logout

**ID:** UC02  
**Nome:** Logout  
**Atores:**  
- **Primário:** Funcionário  
- **Secundário:** Sistema  

**Pré-condições:** O usuário deve possuir uma sessão ativa no sistema.  
**Pós-condições:** A sessão do usuário é finalizada.  

**Fluxo Principal:**
1. O Funcionário solicita o logout através do menu.
2. O Sistema finaliza a sessão corrente.
3. O Sistema redireciona para a tela de login (UC01).

---

## 3. Consultar Preço

**ID:** UC03  
**Nome:** Consultar Preço  
**Atores:**  
- **Primário:** Caixa  
- **Secundário:** Sistema  

**Pré-condições:** O Caixa deve estar logado (UC01).  
**Pós-condições:** O preço do produto é exibido.  

**Fluxo Principal:**
1. O Caixa acessa a função de consulta de preço.
2. O Caixa informa o código ou nome do produto.
3. O Sistema retorna o preço atual do produto.

**Conexões:**
- É utilizado pelo UC04 (Registrar Venda)

---

## 4. Registrar Venda

**ID:** UC04  
**Nome:** Registrar Venda  
**Atores:**  
- **Primário:** Caixa  
- **Secundário:** Sistema  

**Pré-condições:** O Caixa deve estar logado (UC01).  
**Pós-condições:** Uma venda é registrada no sistema.  

**Fluxo Principal:**
1. O Caixa inicia uma nova venda.
2. O Caixa adiciona itens à venda utilizando Consultar Preço (UC03).
3. O Caixa finaliza a venda.
4. O Sistema registra a venda e seus itens.
5. O Sistema aciona Pagamento (UC05).

**Conexões:**
- Utiliza UC03 (Consultar Preço)
- Chama UC05 (Pagamento)

---

## 5. Pagamento

**ID:** UC05  
**Nome:** Pagamento  
**Atores:**  
- **Primário:** Caixa  
- **Secundário:** Sistema  

**Pré-condições:** Deve existir uma venda em aberto (UC04).  
**Pós-condições:** O pagamento é registrado e associado à venda.  

**Fluxo Principal:**
1. O Caixa seleciona a venda para pagamento.
2. O Caixa informa a forma de pagamento e o valor.
3. O Sistema registra o pagamento.
4. O Sistema atualiza o status da venda para "Paga".
5. O Sistema aciona Saída de Produto do Estoque (UC07) para cada item vendido.

**Conexões:**
- Chamado por UC04 (Registrar Venda)
- Chama UC07 (Saída de Produto do Estoque)

---

## 6. Entrada de Produto no Estoque

**ID:** UC06  
**Nome:** Entrada de Produto no Estoque  
**Atores:**  
- **Primário:** Estoquista  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado (UC01) e ter permissões de estoque.  
**Pós-condições:** A quantidade em estoque do produto é aumentada.  

**Fluxo Principal:**
1. O Estoquista seleciona a opção de entrada no estoque.
2. O Estoquista seleciona o produto utilizando Consultar Produto no Estoque (UC09).
3. O Estoquista informa a quantidade de entrada.
4. O Sistema atualiza o saldo do produto.

**Conexões:**
- Utiliza UC09 (Consultar Produto no Estoque)

---

## 7. Saída de Produto do Estoque

**ID:** UC07  
**Nome:** Saída de Produto do Estoque  
**Atores:**  
- **Primário:** Sistema  
- **Secundário:** Estoquista  

**Pré-condições:** Deve existir produto em estoque.  
**Pós-condições:** A quantidade em estoque do produto é reduzida.  

**Fluxo Principal:**
1. O Sistema recebe solicitação de saída (do UC05 ou manualmente).
2. O Sistema verifica a quantidade disponível utilizando Consultar Produto no Estoque (UC09).
3. O Sistema atualiza o saldo do produto.
4. Se estoque ficar abaixo do mínimo, aciona Notificar Gerente (UC20).

**Conexões:**
- Chamado por UC05 (Pagamento)
- Utiliza UC09 (Consultar Produto no Estoque)
- Chama UC20 (Notificar Gerente)

---

## 8. Editar Produto no Estoque

**ID:** UC08  
**Nome:** Editar Produto no Estoque  
**Atores:**  
- **Primário:** Estoquista  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado (UC01) e ter permissões de estoque.  
**Pós-condições:** Os dados do produto no estoque são alterados.  

**Fluxo Principal:**
1. O Estoquista seleciona o produto no estoque utilizando Consultar Produto no Estoque (UC09).
2. O Estoquista edita as informações desejadas.
3. O Sistema salva as alterações.

**Conexões:**
- Utiliza UC09 (Consultar Produto no Estoque)

---

## 9. Consultar Produto no Estoque

**ID:** UC09  
**Nome:** Consultar Produto no Estoque  
**Atores:**  
- **Primário:** Estoquista  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado (UC01) e ter permissões de estoque.  
**Pós-condições:** As informações do produto no estoque são exibidas.  

**Fluxo Principal:**
1. O Estoquista acessa a consulta de estoque.
2. O Estoquista informa o código ou nome do produto.
3. O Sistema exibe as informações do produto no estoque.

**Conexões:**
- Utilizado por UC06, UC07, UC08

---

## 10. Editar Produto

**ID:** UC10  
**Nome:** Editar Produto  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** Os dados do produto são alterados.  

**Fluxo Principal:**
1. O Gerente seleciona o produto a ser editado utilizando Consultar Produto (UC11).
2. O Gerente altera as informações do produto.
3. O Sistema salva as alterações.

**Conexões:**
- Utiliza UC11 (Consultar Produto)

---

## 11. Consultar Produto

**ID:** UC11  
**Nome:** Consultar Produto  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** As informações do produto são exibidas.  

**Fluxo Principal:**
1. O Gerente acessa a consulta de produtos.
2. O Gerente informa o código ou nome do produto.
3. O Sistema exibe as informações do produto.

**Conexões:**
- Utilizado por UC10, UC12, UC13, UC14

---

## 12. Cadastrar Produto

**ID:** UC12  
**Nome:** Cadastrar Produto  
**Atores:** 
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** Um novo produto é cadastrado no sistema.  

**Fluxo Principal:**
1. O Gerente acessa o cadastro de produtos.
2. O Gerente preenche os dados do novo produto.
3. O Sistema valida e salva o novo produto.
4. O Sistema aciona Entrada de Produto no Estoque (UC06) para estoque inicial.

**Conexões:**
- Utiliza UC11 (Consultar Produto) para verificar se produto já existe
- Chama UC06 (Entrada de Produto no Estoque)

---

## 13. Inativar Produto

**ID:** UC13  
**Nome:** Inativar Produto  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** O produto é marcado como inativo no sistema.  

**Fluxo Principal:**
1. O Gerente seleciona o produto a ser inativado utilizando Consultar Produto (UC11).
2. O Gerente confirma a inativação.
3. O Sistema marca o produto como inativo.

**Conexões:**
- Utiliza UC11 (Consultar Produto)

---

## 14. Deletar Produto

**ID:** UC14  
**Nome:** Deletar Produto  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** O produto é removido do sistema.  

**Fluxo Principal:**
1. O Gerente seleciona o produto a ser deletado utilizando Consultar Produto (UC11).
2. O Gerente confirma a exclusão.
3. O Sistema remove o produto do banco de dados.

**Conexões:**
- Utiliza UC11 (Consultar Produto)

---

## 15. Editar Usuário

**ID:** UC15  
**Nome:** Editar Usuário  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** Os dados do usuário são alterados.  

**Fluxo Principal:**
1. O Gerente seleciona o usuário a ser editado utilizando Consultar Usuário (UC16).
2. O Gerente altera as informações do usuário.
3. O Sistema salva as alterações.

**Conexões:**
- Utiliza UC16 (Consultar Usuário)

---

## 16. Consultar Usuário

**ID:** UC16  
**Nome:** Consultar Usuário  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** As informações do usuário são exibidas.  

**Fluxo Principal:**
1. O Gerente acessa a consulta de usuários.
2. O Gerente informa o nome ou login do usuário.
3. O Sistema exibe as informações do usuário.

**Conexões:**
- Utilizado por UC15, UC17, UC18, UC19

---

## 17. Cadastrar Usuário

**ID:** UC17  
**Nome:** Cadastrar Usuário  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** Um novo usuário é cadastrado no sistema.  

**Fluxo Principal:**
1. O Gerente acessa o cadastro de usuários.
2. O Gerente preenche os dados do novo usuário.
3. O Sistema valida e salva o novo usuário utilizando Consultar Usuário (UC16) para verificar duplicidade.

**Conexões:**
- Utiliza UC16 (Consultar Usuário)

---

## 18. Inativar Usuário

**ID:** UC18  
**Nome:** Inativar Usuário  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** O usuário é marcado como inativo no sistema.  

**Fluxo Principal:**
1. O Gerente seleciona o usuário a ser inativado utilizando Consultar Usuário (UC16).
2. O Gerente confirma a inativação.
3. O Sistema marca o usuário como inativo.

**Conexões:**
- Utiliza UC16 (Consultar Usuário)

---

## 19. Deletar Usuário

**ID:** UC19  
**Nome:** Deletar Usuário  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** Usuário deve estar logado como gerente (UC01).  
**Pós-condições:** O usuário é removido do sistema.  

**Fluxo Principal:**
1. O Gerente seleciona o usuário a ser deletado utilizando Consultar Usuário (UC16).
2. O Gerente confirma a exclusão.
3. O Sistema remove o usuário do banco de dados.

**Conexões:**
- Utiliza UC16 (Consultar Usuário)

---

## 20. Notificar Gerente sobre Falta de Estoque

**ID:** UC20  
**Nome:** Notificar Gerente sobre Falta de Estoque  
**Atores:**  
- **Primário:** Sistema  
- **Secundário:** Gerente  

**Pré-condições:** Deve existir produtos com estoque abaixo do mínimo configurado.  
**Pós-condições:** O gerente recebe uma notificação sobre falta de estoque.  

**Fluxo Principal:**
1. O Sistema verifica os níveis de estoque periodicamente.
2. Ao identificar produto com estoque abaixo do mínimo, o Sistema gera notificação.
3. O Sistema exibe alerta para o gerente logado.
4. O Sistema pode enviar e-mail de notificação (opcional).

**Conexões:**
- Chamado por UC07 (Saída de Produto do Estoque)
