# Caso de Uso: Permitir login no sistema

**ID:** UC01
**Nome:** Permitir login no sistema
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Permitir que o funcionário autentique-se usando usuário e senha e inicie uma sessão.

## Pré-condições
- A tela de login está acessível.
- O usuário possui credenciais válidas ou tentará fornecê-las.

## Pós-condições
- Sessão do usuário iniciada.
- Evento de login registrado no log de auditoria.

## Fluxo Principal
1. Usuário acessa a tela de login.
2. Usuário informa nome de usuário e senha.
3. Sistema valida campos obrigatórios (usuário e senha).
4. Sistema verifica credenciais contra base de usuários.
5. Se credenciais válidas, sistema cria sessão, registra o evento e redireciona para o dashboard.
6. Sistema aciona UC03 (Identificar cargo) para carregar permissões correspondentes.

## Fluxos Alternativos / Exceções
- A1. Campos obrigatórios ausentes: o sistema exibe mensagem e não prossegue.
- A2. Credenciais inválidas: o sistema informa tentativa falha e permite nova tentativa; após N tentativas, bloqueia conta temporariamente.
- A3. Conta bloqueada: o sistema informa motivo e orienta procedimento de recuperação.
- A4. Erro do sistema (banco/serviço): o sistema exibe mensagem genérica e registra o erro no log.

## Regras de Negócio
- Senha deve obedecer à política de segurança configurada.

## Observações
- Tentativas de login falhas devem ser registradas para auditoria.


---

# Caso de Uso: Realizar logout

**ID:** UC02
**Nome:** Realizar logout
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Encerrar a sessão atual do usuário de forma segura.

## Pré-condições
- Usuário está autenticado.

## Pós-condições
- Sessão encerrada e usuário redirecionado para a tela de login.
- Evento de logout registrado.

## Fluxo Principal
1. Usuário seleciona a opção 'Logout' no sistema.
2. Sistema confirma a ação (opcional).
3. Sistema invalida a sessão, remove tokens e limpa dados sensíveis de sessão.
4. Sistema registra o evento de logout no log de auditoria.
5. Usuário é redirecionado para a tela de login.

## Fluxos Alternativos / Exceções
- A1. Sessão expirada: sistema encerra a sessão automaticamente e solicita novo login.
- A2. Falha ao invalidar sessão: sistema registra erro e força expiração no backend.

## Observações
- O logout deve encerrar todas as sessões ativas do usuário em todos os dispositivos quando solicitado.


---

# Caso de Uso: Identificar cargo do funcionário ao autenticar

**ID:** UC03
**Nome:** Identificar cargo do funcionário ao autenticar
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Após autenticação, identificar o cargo do funcionário e preparar as permissões e visibilidade do sistema.

## Pré-condições
- Usuário autenticado.

## Pós-condições
- Cargo identificado e permissões carregadas.
- Menu e telas adaptadas ao cargo.

## Fluxo Principal
1. Após login, o sistema consulta o perfil do usuário.
2. Sistema obtém o cargo (ex.: Caixa, Estoquista, Gerente) associado ao usuário.
3. Sistema carrega conjunto de permissões e recursos permitidos para o cargo.
4. Sistema envia essas permissões para a camada de apresentação e atualiza a interface do usuário.

## Fluxos Alternativos / Exceções
- A1. Nenhum cargo associado: sistema avisa que o usuário precisa de atribuição de cargo e bloqueia funcionalidades sensíveis; registra evento e direciona para o gerente.
- A2. Cargo inválido ou inexistente: sistema notifica administrador e aplica perfil mínimo.

## Regras de Negócio
- Permissões devem ser definidas em uma matriz 'cargo x recurso'.


---

# Caso de Uso: Exibir funcionalidades permitidas ao cargo do funcionário logado

**ID:** UC04
**Nome:** Exibir funcionalidades permitidas ao cargo do funcionário logado
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Controlar visibilidade e acesso das funcionalidades com base no cargo do usuário logado.

## Pré-condições
- Cargo do usuário identificado e permissões carregadas.

## Pós-condições
- Interface exibe apenas ações permitidas para o cargo.

## Fluxo Principal
1. Camada de apresentação solicita permissões do usuário.
2. Sistema avalia cada funcionalidade e decide visibilidade/enable/disable.
3. Interface esconde ou desabilita ações não permitidas.
4. Tentativa de acessar rota/ação proibida é interceptada pelo backend.

## Fluxos Alternativos / Exceções
- A1. Usuário tenta acessar funcionalidade não permitida via URL direta: backend retorna 'Acesso Negado' e registra tentativa.
- A2. Permissões atualizadas em tempo de execução: sistema atualiza a interface sem precisar de novo login.

## Observações
- Caixa não deve ver informações de fornecedor nem validade de produtos.


---

# Caso de Uso: Validar dados obrigatórios antes de salvar registros

**ID:** UC05
**Nome:** Validar dados obrigatórios antes de salvar registros
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Garantir que todos os campos obrigatórios estejam preenchidos antes de persistir registros.

## Pré-condições
- Tela de cadastro/edição acessível.

## Pós-condições
- Registro salvo somente se todos os dados obrigatórios estiverem válidos.
- Mensagens de erro exibidas em caso de inconsistência.

## Fluxo Principal
1. Usuário acessa formulário de inclusão/edição.
2. Usuário preenche os campos e aciona comando 'Salvar'.
3. Sistema valida campos obrigatórios e formatos (ex.: datas, números).
4. Se válido, sistema persiste o registro e registra evento de auditoria.
5. Se inválido, sistema mostra mensagens de erro e não persiste.

## Fluxos Alternativos / Exceções
- A1. Algum campo obrigatório faltando: sistema destaca campos e não permite salvar.
- A2. Formato inválido (ex.: CNPJ inválido): sistema exibe mensagem específica.
- A3. Conflito de unicidade (ex.: código de produto já existe): sistema informa e sugere ação.


---

# Caso de Uso: Registrar todas operações de estoque e vendas com data, hora e usuário responsável

**ID:** UC06
**Nome:** Registrar todas operações de estoque e vendas com data, hora e usuário responsável
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Registrar em log/audit trail todas as operações relevantes de estoque e vendas.

## Pré-condições
- Sistema de logs/auditoria operacional disponível.

## Pós-condições
- Operação registrada com timestamp e identificação do usuário.
- Auditoria consultável.

## Fluxo Principal
1. Operação de estoque ou venda é iniciada (entrada, saída, ajuste, venda).
2. Sistema prepara registro de auditoria contendo: tipo de operação, usuário, data/hora, itens afetados e observações.
3. Sistema persiste o registro de auditoria em local apropriado (BD de auditoria).
4. Sistema confirma sucesso da operação ao usuário.

## Fluxos Alternativos / Exceções
- A1. Serviço de auditoria indisponível: sistema grava em fila local para retry e informa operação concluída, garantindo eventual consistência.
- A2. Falha ao gravar auditoria: sistema tenta novamente e registra erro crítico.

## Observações
- Todos os registros de auditoria devem ser imutáveis e consultáveis por gerente.


---

# Caso de Uso: Manter histórico de vendas e movimentações de estoque para consulta futura

**ID:** UC07
**Nome:** Manter histórico de vendas e movimentações de estoque para consulta futura
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Armazenar histórico completo de vendas e movimentos de estoque para consultas e relatórios.

## Pré-condições
- Banco de dados disponível.

## Pós-condições
- Histórico disponível para consulta por período, produto e usuário.

## Fluxo Principal
1. Ao concluir operação (venda/entrada/saída/ajuste), sistema persiste registro detalhado no histórico.
2. Sistema indexa registros para consultas eficientes por filtros (data, produto, usuário).
3. Usuário com permissão realiza consultas usando filtros e recebe resultado paginado.

## Fluxos Alternativos / Exceções
- A1. Consulta com filtro muito amplo: sistema recomenda paginação e limita tempo/resultado.
- A2. Histórico corrompido (falha de storage): sistema exibe erro e aciona rotina de recuperação.


---

# Caso de Uso: Validar quantidade em estoque ao adicionar produto à venda ou registrar saída

**ID:** UC08
**Nome:** Validar quantidade em estoque ao adicionar produto à venda ou registrar saída
**Ator Principal:** Todo funcionário
**Atores Secundários:** Sistema

---

## Descrição
Verificar disponibilidade de estoque antes de autorizar saída ou inclusão em venda.

## Pré-condições
- Dados de estoque atualizados e consistentes.

## Pós-condições
- Adicionar/remover somente se houver quantidade suficiente ou com autorização para permitir negativo.

## Fluxo Principal
1. Usuário adiciona item à venda ou solicita saída de estoque.
2. Sistema consulta quantidade disponível (considerando reservas).
3. Se quantidade suficiente, sistema reserva a quantidade e permite prosseguir.
4. Sistema atualiza status da operação e recalcula totais.

## Fluxos Alternativos / Exceções
- A1. Quantidade insuficiente: sistema informa e bloqueia inclusão; permite solicitar autorização de gerente para permitir venda em negativa.
- A2. Estoque negativo permitido somente com aprovação de gerente: sistema grava justificativa e aprova manualmente.


---

# Caso de Uso: Consultar informações de produtos (Tudo exceto Fornecedor)

**ID:** UC09
**Nome:** Consultar informações de produtos (Tudo exceto Fornecedor)
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Permitir que o caixa consulte informações de produtos necessárias para venda, sem mostrar fornecedor ou validade.

## Pré-condições
- Usuário com cargo Caixa autenticado.

## Pós-condições
- Informações básicas do produto exibidas (nome, preço, código, unidade).

## Fluxo Principal
1. Caixa acessa funcionalidade de consulta de produto.
2. Caixa pesquisa por código, nome ou faz leitura de código de barras.
3. Sistema retorna informações permitidas ao cargo (exclui fornecedor e validade).
4. Sistema permite adicionar o produto a uma venda existente.

## Fluxos Alternativos / Exceções
- A1. Produto não encontrado: sistema informa e sugere cadastrar ou solicitar auxílio ao estoquista/gerente.
- A2. Produto com restrição de venda: sistema exibe aviso e solicita autorização.

## Observações
- Caixa não deve ter acesso a dados sensíveis como fornecedor e validade.


---

# Caso de Uso: Consultar informações de produtos (Todas)

**ID:** UC10
**Nome:** Consultar informações de produtos (Todas)
**Ator Principal:** Estoquista, Gerente
**Atores Secundários:** Sistema

---

## Descrição
Permitir consulta completa das informações de produtos para gestão e controle.

## Pré-condições
- Usuário com cargo Estoquista ou Gerente autenticado.

## Pós-condições
- Informações completas do produto exibidas (inclui fornecedor, validade, histórico).

## Fluxo Principal
1. Usuario acessa consulta de produtos.
2. Usuario pesquisa por nome, código ou filtro avançado.
3. Sistema retorna ficha completa do produto incluindo fornecedor, lote, validade e histórico de movimentações.
4. Usuario pode navegar para ações de edição ou ajuste conforme permissão.

## Fluxos Alternativos / Exceções
- A1. Produto não encontrado: sistema informa e sugere cadastrar novo produto.
- A2. Permissão insuficiente: ação edit ou ajuste requerida é bloqueada.


---

# Caso de Uso: Abrir uma transação de venda

**ID:** UC11
**Nome:** Abrir uma transação de venda
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Iniciar uma nova transação de venda para registrar itens e pagamentos.

## Pré-condições
- Usuário com cargo Caixa autenticado.

## Pós-condições
- Transação criada com status 'Aberta' e identificador único.

## Fluxo Principal
1. Caixa seleciona 'Nova Venda'.
2. Sistema cria registro de venda com status 'Aberta' e informa número/ID.
3. Sistema reserva sessão da venda até confirmação ou cancelamento.
4. Caixa procede com inclusão de itens.

## Fluxos Alternativos / Exceções
- A1. Falha ao criar transação: sistema exibe erro e registra tentativa.
- A2. Sessão de venda existente para o mesmo operador: sistema permite múltiplas vendas simultâneas ou avisa conforme configuração.


---

# Caso de Uso: Adicionar produtos à venda

**ID:** UC12
**Nome:** Adicionar produtos à venda
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Adicionar itens a uma transação de venda aberta, considerando estoque e regras de unidade/peso.

## Pré-condições
- Transação de venda aberta.

## Pós-condições
- Itens adicionados à venda e totais atualizados.

## Fluxo Principal
1. Caixa seleciona venda aberta.
2. Caixa faz leitura de código de barras ou busca o produto.
3. Sistema valida disponibilidade em estoque.
4. Caixa informa quantidade ou o peso (se aplicável).
5. Sistema adiciona item como linha na venda, recalcula subtotais e total.
6. Sistema registra ação no log da venda.

## Fluxos Alternativos / Exceções
- A1. Produto não existe: sistema informa e sugere cadastro.
- A2. Estoque insuficiente: sistema impede inclusão e sugere redução de quantidade ou autorização de gerente.
- A3. Produto por peso: sistema aciona fluxo UC14 para leitura de balança.


---

# Caso de Uso: Remover produtos da venda antes da finalização

**ID:** UC13
**Nome:** Remover produtos da venda antes da finalização
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Permitir remoção de itens de uma venda enquanto ela estiver aberta (não paga).

## Pré-condições
- Venda está em status 'Aberta' e sem pagamento registrado.

## Pós-condições
- Item removido e totais recalculados.

## Fluxo Principal
1. Caixa seleciona a venda e escolhe o item a ser removido.
2. Sistema solicita confirmação da remoção.
3. Ao confirmar, sistema remove a linha, atualiza totais e disponibiliza estoque reservado.
4. Sistema registra a ação no log da venda.

## Fluxos Alternativos / Exceções
- A1. Item já pago ou venda encerrada: sistema impede remoção e exibe mensagem.
- A2. Falha ao atualizar estoque: sistema alerta e registra erro.


---

# Caso de Uso: Pesar produto adicionado quando aplicável

**ID:** UC14
**Nome:** Pesar produto adicionado quando aplicável
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Registrar peso de produtos vendidos por peso e calcular valor pela unidade de medida.

## Pré-condições
- Balança conectada ou entrada manual de peso disponível.
- Produto configurado para venda por peso.

## Pós-condições
- Peso registrado, valor calculado e item adicionado corretamente na venda.

## Fluxo Principal
1. Caixa seleciona produto com venda por peso.
2. Sistema solicita colocar o produto na balança.
3. Sistema recebe leitura da balança ou permite entrada manual do peso se balança indisponível com justificativa.
4. Sistema calcula o valor (preço por kg * peso) e adiciona o item à venda.
5. Registro do peso e operador é salvo para auditoria.

## Fluxos Alternativos / Exceções
- A1. Balança não responde: permitir entrada manual do peso com justificativa e marcada para revisão.
- A2. Peso fora do intervalo plausível: sistema exibe aviso e solicita nova pesagem.


---

# Caso de Uso: Cancelar a venda antes da finalização

**ID:** UC15
**Nome:** Cancelar a venda antes da finalização
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Cancelar uma venda aberta antes do registro do pagamento, registrando o usuário que cancelou e motivo.

## Pré-condições
- Venda em status 'Aberta'.

## Pós-condições
- Venda marcada como 'Cancelada' com registro do usuário e motivo.
- Estoque reservado liberado.

## Fluxo Principal
1. Caixa seleciona a venda aberta e escolhe a opção 'Cancelar'.
2. Sistema solicita confirmação e motivo do cancelamento.
3. Ao confirmar, sistema atualiza status para 'Cancelada', registra usuário e motivo, e libera estoque reservado.
4. Sistema registra evento de cancelamento em auditoria.

## Fluxos Alternativos / Exceções
- A1. Venda já possui pagamento: sistema impede cancelamento e orienta processo de estorno/retorno.
- A2. Cancelamento não confirmado: operação abortada.


---

# Caso de Uso: Registrar forma de pagamento (dinheiro, cartão, Pix)

**ID:** UC16
**Nome:** Registrar forma de pagamento (dinheiro, cartão, Pix)
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Registrar um ou mais meios de pagamento utilizados na quitação da venda.

## Pré-condições
- Venda com itens adicionados e total calculado.

## Pós-condições
- Meios de pagamento registrados e total pago atingido ou atualizado.

## Fluxo Principal
1. Caixa seleciona 'Pagamento' na venda aberta.
2. Caixa informa forma de pagamento (Dinheiro, Cartão, Pix) e o valor para cada forma.
3. Sistema valida se soma dos pagamentos cobre o total da venda ou registra pagamento parcial.
4. Sistema grava os dados do pagamento (tipo, valor, comprovante quando aplicável).

## Fluxos Alternativos / Exceções
- A1. Pagamento com cartão recusado: sistema informa e permite tentar outro cartão ou método.
- A2. Pagamento parcial: sistema permite registrar pagamento restante em outra forma.
- A3. Falha na integração com adquirente: sistema registra tentativa e permite concluir com outro método.


---

# Caso de Uso: Confirmar pagamento e calcular troco automaticamente

**ID:** UC17
**Nome:** Confirmar pagamento e calcular troco automaticamente
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Confirmar que o pagamento foi realizado e calcular troco automaticamente para pagamentos em dinheiro.

## Pré-condições
- Pagamento registrado (ao menos dinheiro ou combinação que permita troco).

## Pós-condições
- Venda marcada como paga com troco calculado quando aplicável.

## Fluxo Principal
1. Após registro do(s) pagamento(s), sistema verifica se o total foi quitado.
2. Se houver pagamento em dinheiro maior que o total, sistema calcula troco = valor_pago_em_dinheiro - total.
3. Sistema exibe valor do troco para o caixa e inclui informação no recibo.
4. Sistema finaliza etapa de pagamento e prossegue para registro da venda (UC18).

## Fluxos Alternativos / Exceções
- A1. Valor em dinheiro insuficiente: sistema informa valor faltante e permite registrar pagamento complementar.
- A2. Regra de arredondamento (por moeda/local): sistema aplica regra definida e registra ajuste.
- A3. Erro no cálculo: sistema bloqueia finalização e registra erro.


---

# Caso de Uso: Registrar a venda no sistema após pagamento

**ID:** UC18
**Nome:** Registrar a venda no sistema após pagamento
**Ator Principal:** Caixa
**Atores Secundários:** Sistema

---

## Descrição
Finalizar a venda após confirmação do pagamento, atualizando estoque e gerando comprovantes.

## Pré-condições
- Pagamento confirmado e registrado.

## Pós-condições
- Venda registrada como 'Concluída', estoque atualizado e comprovante gerado.

## Fluxo Principal
1. Sistema recebe confirmação de pagamento.
2. Sistema atualiza status da venda para 'Concluída' e gera número de documento fiscal/recibo.
3. Sistema deduz as quantidades no estoque e registra movimentações.
4. Sistema registra evento de venda concluída na auditoria e disponibiliza comprovante para impressão/enviar.

## Fluxos Alternativos / Exceções
- A1. Falha ao atualizar estoque: sistema tenta rollback; se não for possível, marca venda como 'Concluída (estoque pendente)' e notifica gerente.
- A2. Erro na emissão do comprovante fiscal: sistema registra falha e permite emissão manual posterior.


---

# Caso de Uso: Registrar entrada de produtos no estoque

**ID:** UC19
**Nome:** Registrar entrada de produtos no estoque
**Ator Principal:** Estoquista
**Atores Secundários:** Sistema

---

## Descrição
Registrar a entrada de mercadorias no estoque com informações de fornecedor, lote e validade quando aplicável.

## Pré-condições
- Usuário Estoquista autenticado.
- Produto previamente cadastrado no sistema.

## Pós-condições
- Estoque aumentado e registro de entrada persistido com lote/fornecedor/validade.

## Fluxo Principal
1. Estoquista seleciona 'Registrar Entrada'.
2. Estoquista informa produto, quantidade, fornecedor, número de nota e data, e validade (se aplicável).
3. Sistema valida fornecedor e produto, e persiste registro de entrada.
4. Sistema atualiza quantidade em estoque e registra auditoria.

## Fluxos Alternativos / Exceções
- A1. Fornecedor não cadastrado: sistema informa e solicita cadastro prévio ou associação posterior.
- A2. Dados incompletos: sistema não permite salvar e aponta campos faltantes.
- A3. Divergência na nota fiscal: registrar entrada em quarentena para conferência.


---

# Caso de Uso: Registrar saída de produtos do estoque

**ID:** UC20
**Nome:** Registrar saída de produtos do estoque
**Ator Principal:** Estoquista
**Atores Secundários:** Sistema

---

## Descrição
Registrar saídas de estoque por venda, perda, doação ou transferência, com motivo.

## Pré-condições
- Usuário Estoquista autenticado.
- Motivo de saída informado.

## Pós-condições
- Estoque reduzido e registro de saída persistido com motivo e responsável.

## Fluxo Principal
1. Estoquista seleciona 'Registrar Saída'.
2. Estoquista informa produto, quantidade e motivo (venda, perda, doação, etc.).
3. Sistema valida disponibilidade e persiste registro de saída.
4. Sistema reduz estoque e registra auditoria.

## Fluxos Alternativos / Exceções
- A1. Quantidade insuficiente: sistema informa e sugere ação corretiva ou autorização de gerente.
- A2. Saída por perda: sistema solicita justificativa detalhada e anexa evidências (opcional).


---

# Caso de Uso: Editar registro de estoque, incluindo ajuste de quantidade

**ID:** UC21
**Nome:** Editar registro de estoque, incluindo ajuste de quantidade
**Ator Principal:** Estoquista
**Atores Secundários:** Sistema

---

## Descrição
Permitir ajustes de estoque com justificativa, mantendo histórico das alterações para auditoria.

## Pré-condições
- Usuário Estoquista autenticado.
- Permissão para ajuste concedida.

## Pós-condições
- Registro de estoque atualizado e histórico do ajuste registrado.

## Fluxo Principal
1. Usuário pesquisa o item/registro de estoque a ser ajustado.
2. Usuário seleciona 'Ajustar Estoque' e informa nova quantidade e justificativa.
3. Sistema valida justificativa e, se necessário, solicita aprovação de gerente.
4. Ao confirmar, sistema atualiza quantidade, persiste ajuste com usuário/justificativa/data e registra auditoria.

## Fluxos Alternativos / Exceções
- A1. Ajuste acima de limite definido: sistema bloqueia e solicita aprovação de gerente.
- A2. Falha ao gravar histórico: sistema aborta alteração e informa erro.


---

# Caso de Uso: Cadastrar novo produto no sistema

**ID:** UC22
**Nome:** Cadastrar novo produto no sistema
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Cadastrar produto com nome, código, preço, unidade de medida e fornecedor.

## Pré-condições
- Usuário Gerente autenticado.

## Pós-condições
- Produto cadastrado com identificador único.

## Fluxo Principal
1. Gerente acessa função 'Novo Produto'.
2. Gerente informa nome, código, preço, unidade, fornecedor e demais atributos obrigatórios.
3. Sistema valida unicidade do código e formatos obrigatórios.
4. Sistema persiste o produto e registra auditoria.

## Fluxos Alternativos / Exceções
- A1. Código já existe: sistema informa conflito e permite editar produto existente ou escolher novo código.
- A2. Dados obrigatórios faltando: sistema não permite salvar.


---

# Caso de Uso: Consultar informações de produtos cadastrados (para gestão)

**ID:** UC23
**Nome:** Consultar informações de produtos cadastrados (para gestão)
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Permitir consulta avançada de produtos para gestão, com filtros e exportação.

## Pré-condições
- Usuário Gerente autenticado.

## Pós-condições
- Resultado da consulta exibido com opções de exportação/edição.

## Fluxo Principal
1. Gerente acessa tela de consulta de produtos.
2. Gerente aplica filtros (nome, código, categoria, estoque mínimo).
3. Sistema retorna lista paginada de produtos com dados resumidos.
4. Gerente pode abrir ficha completa, editar ou exportar relatório.

## Fluxos Alternativos / Exceções
- A1. Consulta sem resultados: sistema informa e sugere relaxar filtros.
- A2. Erro de exportação: sistema registra falha e sugere tentativa posterior.


---

# Caso de Uso: Atualizar dados de produtos existentes

**ID:** UC24
**Nome:** Atualizar dados de produtos existentes
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Permitir alteração de atributos de produto como preço, descrição e fornecedor.

## Pré-condições
- Usuário Gerente autenticado.
- Produto existente selecionado.

## Pós-condições
- Dados do produto atualizados e versão/histórico registrado.

## Fluxo Principal
1. Gerente abre ficha do produto e seleciona 'Editar'.
2. Gerente altera campos necessários (preço, descrição, fornecedor, etc.).
3. Sistema valida entradas e persiste alterações.
4. Sistema registra histórico da alteração com usuário e timestamp.

## Fluxos Alternativos / Exceções
- A1. Conflito de edição simultânea: sistema detecta versão divergente e solicita reconciliação.
- A2. Dados inválidos: sistema rejeita alteração e exibe mensagens.


---

# Caso de Uso: Deletar produto do sistema

**ID:** UC25
**Nome:** Deletar produto do sistema
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Remover produto do catálogo, mantendo histórico de exclusão para auditoria.

## Pré-condições
- Usuário Gerente autenticado.
- Produto não deve ter restrições que impeçam exclusão.

## Pós-condições
- Produto marcado como deletado ou inativado e histórico de exclusão registrado.

## Fluxo Principal
1. Gerente seleciona produto e escolhe opção 'Deletar'.
2. Sistema verifica dependências (vendas, movimentos de estoque, ordens) e impede exclusão direta se houver vínculos.
3. Se permitido, sistema marca o produto como 'Inativo/Deletado' (soft delete) e registra a ação em auditoria.

## Fluxos Alternativos / Exceções
- A1. Produto com vínculos ativos: sistema impede exclusão e sugere inativação ou limpeza de vínculos.
- A2. Deleção não confirmada: operação abortada.


---

# Caso de Uso: Cadastrar novo usuário no sistema

**ID:** UC26
**Nome:** Cadastrar novo usuário no sistema
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Cadastrar novos usuários com login, senha e cargo.

## Pré-condições
- Usuário Gerente autenticado.

## Pós-condições
- Novo usuário criado com cargo e credenciais iniciais.
- Evento registrado.

## Fluxo Principal
1. Gerente acessa 'Novo Usuário'.
2. Gerente informa nome, login, senha inicial (ou gera senha) e cargo.
3. Sistema valida unicidade do login e regra de senha.
4. Sistema persiste usuário, envia instruções de acesso (opcional) e registra auditoria.

## Fluxos Alternativos / Exceções
- A1. Login já existe: sistema informa e não permite duplicar.
- A2. Senha fraca: sistema exige conformidade com políticas de segurança.


---

# Caso de Uso: Consultar informações de usuários cadastrados

**ID:** UC27
**Nome:** Consultar informações de usuários cadastrados
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Permitir ao gerente buscar e visualizar usuários cadastrados e seu perfil.

## Pré-condições
- Usuário Gerente autenticado.

## Pós-condições
- Lista de usuários exibida com informações relevantes (nome, login, cargo, último acesso).

## Fluxo Principal
1. Gerente acessa tela de usuários.
2. Gerente pesquisa por nome ou login e aplica filtros.
3. Sistema retorna lista com perfis e ações rápidas (editar, resetar senha, inativar).

## Fluxos Alternativos / Exceções
- A1. Nenhum usuário encontrado: sistema informa.
- A2. Permissão insuficiente: ação restrita.


---

# Caso de Uso: Atualizar dados de usuários existentes

**ID:** UC28
**Nome:** Atualizar dados de usuários existentes
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Alterar atributos de usuários como cargo, senha e status.

## Pré-condições
- Usuário Gerente autenticado.
- Usuário alvo existe.

## Pós-condições
- Dados de usuário atualizados e histórico de alteração registrado.

## Fluxo Principal
1. Gerente seleciona usuário para edição.
2. Gerente altera campos (cargo, senha, ativo/inativo).
3. Sistema valida alterações e persiste.
4. Sistema registra quem alterou e quando na auditoria.

## Fluxos Alternativos / Exceções
- A1. Tentativa de reduzir privilégios de si mesmo: sistema pode impedir para evitar bloquear último admin.
- A2. Conflito de edição: sistema sinaliza e pede reconciliação.


---

# Caso de Uso: Deletar usuário do sistema

**ID:** UC29
**Nome:** Deletar usuário do sistema
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Remover ou inativar usuário e manter registro da exclusão.

## Pré-condições
- Usuário Gerente autenticado.
- Não ser o último usuário administrador (restrição).

## Pós-condições
- Usuário inativado/deletado e histórico registrado.

## Fluxo Principal
1. Gerente seleciona usuário e escolhe 'Deletar/Inativar'.
2. Sistema checa restrições (ex.: não deletar último admin).
3. Sistema marca usuário como inativo ou remove credenciais e registra ação.

## Fluxos Alternativos / Exceções
- A1. Tentativa de deletar último admin: sistema impede e alerta.
- A2. Usuário ativo em processos críticos: sistema pode exigir transferência de responsabilidade antes de inativar.


---

# Caso de Uso: Cadastrar novo fornecedor

**ID:** UC30
**Nome:** Cadastrar novo fornecedor
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Registrar informações de fornecedores (nome, contato, CNPJ) no sistema.

## Pré-condições
- Usuário Gerente autenticado.

## Pós-condições
- Fornecedor cadastrado com dados de contato e CNPJ validados.

## Fluxo Principal
1. Gerente acessa 'Novo Fornecedor'.
2. Gerente informa nome, contato, endereço e CNPJ.
3. Sistema valida formato do CNPJ e unicidade.
4. Sistema persiste fornecedor e registra auditoria.

## Fluxos Alternativos / Exceções
- A1. CNPJ já cadastrado: sistema informa duplicidade e sugere manutenção do registro existente.
- A2. Dados incompletos: sistema não permite salvar.


---

# Caso de Uso: Consultar informações de fornecedores cadastrados

**ID:** UC31
**Nome:** Consultar informações de fornecedores cadastrados
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Permitir busca e visualização dos fornecedores cadastrados.

## Pré-condições
- Usuário Gerente autenticado.

## Pós-condições
- Informações de fornecedores exibidas e possibilitam contato ou edição.

## Fluxo Principal
1. Gerente acessa tela de fornecedores.
2. Gerente pesquisa por nome ou CNPJ.
3. Sistema retorna resultado e permite abrir ficha completa.

## Fluxos Alternativos / Exceções
- A1. Fornecedor não encontrado: sistema informa.
- A2. Permissão insuficiente: ação bloqueada.


---

# Caso de Uso: Atualizar dados de fornecedores existentes

**ID:** UC32
**Nome:** Atualizar dados de fornecedores existentes
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Editar informações de contato e endereço dos fornecedores.

## Pré-condições
- Usuário Gerente autenticado.
- Fornecedor existente.

## Pós-condições
- Dados atualizados e histórico registrado.

## Fluxo Principal
1. Gerente abre ficha do fornecedor e seleciona 'Editar'.
2. Gerente altera dados necessários e salva.
3. Sistema valida e persiste alterações, registrando auditoria.

## Fluxos Alternativos / Exceções
- A1. Erro de validação de CNPJ: sistema rejeita alteração.
- A2. Conflito de edição simultânea: sistema avisa.


---

# Caso de Uso: Deletar fornecedor do sistema

**ID:** UC33
**Nome:** Deletar fornecedor do sistema
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Remover fornecedor do cadastro mantendo histórico; prevenir exclusão se houver vínculos ativos.

## Pré-condições
- Usuário Gerente autenticado.

## Pós-condições
- Fornecedor marcado como inativo/deletado e histórico registrado.

## Fluxo Principal
1. Gerente seleciona fornecedor para remoção.
2. Sistema verifica vínculos (entradas de estoque, pedidos) e impede remoção se existirem referências ativas.
3. Se permitido, sistema marca fornecedor como inativo/deletado e registra auditoria.

## Fluxos Alternativos / Exceções
- A1. Fornecedor com vínculos: sistema impede remoção e informa quais são os vínculos.
- A2. Remoção acidental: manter mecanismo de restauração (undo) ou manter histórico.


---

# Caso de Uso: Sistema notifica automaticamente gerente quando produto estiver com estoque baixo

**ID:** UC34
**Nome:** Sistema notifica automaticamente gerente quando produto estiver com estoque baixo
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Monitorar níveis e notificar gerente quando atingir ponto de reabastecimento.

## Pré-condições
- Níveis mínimos configurados por produto.

## Pós-condições
- Notificação enviada ao gerente com lista de produtos em estoque baixo.

## Fluxo Principal
1. Sistema monitora níveis de estoque continuamente ou em lotes agendados.
2. Ao identificar produto abaixo do limite, sistema gera alerta/entrada de notificação.
3. Sistema envia notificação (e-mail, dashboard, push) ao gerente com detalhes do produto e sugestão de quantidade a comprar.

## Fluxos Alternativos / Exceções
- A1. Serviço de notificação indisponível: sistema registra alerta em fila e tenta reenvio; disponibiliza relatório no dashboard.
- A2. Notificações frequentes (ruído): sistema aplica regras de agregação/threshold para reduzir alertas.


---

# Caso de Uso: Gerente entra em contato com fornecedor correspondente ao produto com estoque baixo

**ID:** UC35
**Nome:** Gerente entra em contato com fornecedor correspondente ao produto com estoque baixo
**Ator Principal:** Gerente
**Atores Secundários:** Sistema

---

## Descrição
Registrar e realizar contato com fornecedor para reposição quando necessário.

## Pré-condições
- Fornecedor cadastrado e dados de contato disponíveis.

## Pós-condições
- Contato registrado com data, responsável e resultado da tentativa.

## Fluxo Principal
1. Gerente acessa lista de produtos com estoque baixo.
2. Gerente seleciona produto e visualiza fornecedor associado.
3. Gerente inicia contato (telefone, e-mail ou sistema integrado) e registra a tentativa e resultado.
4. Sistema armazena o registro de contato para auditoria e acompanhamento.

## Fluxos Alternativos / Exceções
- A1. Fornecedor não responde: registrar tentativa e agendar nova tentativa; escalar para alternativas.
- A2. Dados de contato incorretos: solicitar atualização do fornecedor no cadastro.


---

