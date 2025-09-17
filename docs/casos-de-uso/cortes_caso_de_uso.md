# Casos de Uso - Sistema de Gestão (MVP)

## 1. Fazer Login no Sistema

**ID:** UC01  
**Nome:** Fazer Login no Sistema  
**Atores:**  
- **Primário:** Funcionário (Caixa, Estoquista, Gerente)  
- **Secundário:** Sistema  

**Pré-condições:** O sistema está inicializado e a tela de login está acessível.  
**Pós-condições:** Uma sessão é iniciada para o usuário, e a interface é carregada de acordo com as permissões do seu cargo.  

**Fluxo Principal:**
1. O Sistema exibe a tela de login.
2. O Funcionário informa seu nome de usuário e senha.
3. O Funcionário aciona a ação "Entrar".
4. O Sistema valida as credenciais.
5. O Sistema identifica o cargo do funcionário e carrega suas permissões.
6. O Sistema adapta os menus e funcionalidades da tela inicial com base nessas permissões.
7. O Sistema redireciona o usuário para a tela inicial (dashboard).

**Fluxos Alternativos:**
- **A1. Credenciais Inválidas:** No passo 4, se as credenciais forem inválidas, o Sistema informa "Usuário ou senha incorretos" e permanece na tela de login, permitindo nova tentativa.

---

## 2. Realizar Venda

**ID:** UC02  
**Nome:** Realizar Venda  
**Atores:**  
- **Primário:** Caixa  
- **Secundário:** Sistema  

**Pré-condições:** O Caixa deve estar autenticado no sistema.  
**Pós-condições:** A venda é registrada, o estoque dos produtos vendidos é decrementado, e um comprovante é gerado.  

**Fluxo Principal:**
1. O Caixa aciona a opção "Nova Venda". O Sistema cria uma nova transação.
2. Para cada item:
   - O Caixa escaneia o código de barras ou busca o produto pelo nome.
   - O Sistema valida se há estoque disponível.
   - O Sistema adiciona o item à venda e recalcula o total.
3. O Caixa repete o passo 2 até todos os itens serem adicionados.
4. O Caixa aciona "Finalizar Venda".
5. O Caixa seleciona a(s) forma(s) de pagamento e informa o valor recebido para cada uma.
6. O Sistema calcula e exibe o troco, se aplicável.
7. O Caixa confirma o recebimento.
8. O Sistema registra a venda como concluída, atualiza o estoque e gera um comprovante.

**Fluxos Alternativos:**
- **A1. Estoque Insuficiente:** No passo 2, se o estoque for insuficiente, o Sistema alerta o Caixa e não adiciona o item.
- **A2. Cancelamento:** A qualquer momento antes do passo 7, o Caixa pode cancelar a venda. O Sistema descarta a transação.

---

## 3. Gerenciar Estoque

**ID:** UC03  
**Nome:** Gerenciar Estoque  
**Atores:**  
- **Primário:** Estoquista, Gerente  
- **Secundário:** Sistema  

**Pré-condições:** O usuário deve estar autenticado e ter permissão para gerenciar estoque.  
**Pós-condições:** O estoque é atualizado conforme a operação realizada (entrada, saída, ajuste).  

**Fluxo Principal:**
1. O Usuário aciona a opção "Gerenciar Estoque".
2. O Usuário seleciona o produto.
3. O Usuário seleciona o tipo de operação ("Entrada", "Saída" ou "Ajuste").
4. O Usuário informa a quantidade e uma breve justificativa (ex.: "Compra NF 123", "Quebra").
5. O Usuário confirma a operação.
6. O Sistema atualiza o saldo do produto e registra a operação.

**Fluxos Alternativos:**
- **A1. Saída com Saldo Insuficiente:** No passo 4, se for uma saída e o saldo for insuficiente, o Sistema alerta e pergunta se deseja prosseguir mesmo assim. Se confirmado, o Sistema registra o saldo negativo e a justificativa.

---

## 4. Gerenciar Cadastros

**ID:** UC04  
**Nome:** Gerenciar Cadastros  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** O Gerente deve estar autenticado.  
**Pós-condições:** O cadastro (Produto, Usuário, Fornecedor) é criado, editado ou inativado.  

**Fluxo Principal:**
1. O Gerente aciona a opção "Cadastros".
2. O Gerente seleciona o que deseja gerenciar ("Produtos", "Usuários" ou "Fornecedores").
3. O Sistema exibe a lista de itens cadastrados.
4. O Gerente pode:
   - **Criar Novo:** Preenche os campos obrigatórios e salva.
   - **Editar Existente:** Altera os campos necessários e salva.
   - **Inativar:** Altera o status do item para "Inativo".
5. O Sistema valida e persiste as alterações.

**Fluxos Alternativos:**
- **A1. Dados Inválidos:** No passo 5, se houver erro de validação (ex.: código duplicado), o Sistema rejeita e informa o erro.

---

## 5. Ver Relatório de Vendas

**ID:** UC05  
**Nome:** Ver Relatório de Vendas  
**Atores:**  
- **Primário:** Gerente  
- **Secundário:** Sistema  

**Pré-condições:** O Gerente deve estar autenticado.  
**Pós-condições:** O relatório é exibido na tela.  

**Fluxo Principal:**
1. O Gerente aciona a opção "Relatórios".
2. O Gerente seleciona "Vendas por Período".
3. O Gerente define um intervalo de datas e aciona "Gerar".
4. O Sistema exibe uma lista simplificada das vendas daquele período, com o total geral.

**Fluxos Alternativos:**
- **A1. Nenhuma Venda no Período:** No passo 4, se não houver dados, o Sistema exibe a mensagem "Nenhuma venda encontrada".

---

## 6. Ver Alertas de Estoque Baixo

**ID:** UC06  
**Nome:** Ver Alertas de Estoque Baixo  
**Atores:**  
- **Primário:** Sistema, Gerente  
- **Secundário:** -  

**Pré-condições:** O Gerente deve estar autenticado. Produtos devem ter estoque mínimo configurado.  
**Pós-condições:** O Gerente visualiza a lista de produtos críticos.  

**Fluxo Principal:**
1. O Sistema verifica periodicamente o estoque de todos os produtos.
2. Para cada produto abaixo do estoque mínimo, o Sistema gera um alerta.
3. Ao fazer login, o Gerente vê um número de notificações no menu.
4. O Gerente acessa a tela "Alertas".
5. O Sistema exibe a lista de produtos com estoque baixo.

**Fluxos Alternativos:**
- **A1. Sem Alertas:** No passo 5, se não houver alertas, a tela exibe "Estoque normalizado".

---

## 7. Emitir Comprovante

**ID:** UC07  
**Nome:** Emitir Comprovante  
**Atores:**  
- **Primário:** Caixa  
- **Secundário:** Sistema  

**Pré-condições:** O Caixa deve estar autenticado. Deve existir uma venda concluída.  
**Pós-condições:** Um comprovante é reemitido.  

**Fluxo Principal:**
1. O Caixa acessa a opção "Histórico de Vendas".
2. O Caixa localiza a venda desejada pela data e valor.
3. O Caixa seleciona a venda e aciona "Reimprimir Comprovante".
4. O Sistema envia o comando para a impressora fiscal.

**Fluxos Alternativos:**
- **A1. Impressora Offline:** No passo 4, se a impressora não estiver disponível, o Sistema exibe um erro e o comprovante não é impresso.
