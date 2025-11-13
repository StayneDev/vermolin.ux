# ✅ Checklist de Configuração - Vermolin.UX

Use este checklist para garantir que o ambiente está pronto para executar o projeto.

---

## 📋 Pré-requisitos

### 1. Java JDK 17+

**Verificar instalação:**
```powershell
java -version
```

**Saída esperada:**
```
openjdk version "17.0.x" ou superior
```

**Se não instalado:**
- Download: https://adoptium.net/
- Instale a versão **17 (LTS)** ou superior
- Após instalar, **reinicie o terminal**

---

### 2. Apache Maven

**Verificar instalação:**
```powershell
mvn -version
```

**Saída esperada:**
```
Apache Maven 3.x.x
```

**Se não instalado:**
- Download: https://maven.apache.org/download.cgi
- Extraia para `C:\Program Files\Apache\maven`
- Adicione ao PATH:
  - Painel de Controle → Sistema → Variáveis de Ambiente
  - PATH → Adicionar: `C:\Program Files\Apache\maven\bin`
- **Reinicie o terminal**

---

## 🔧 Verificação do Projeto

### 1. Estrutura de Arquivos

Verifique se os seguintes arquivos existem:

```
vemolin.ux/
├── ✅ backend/
│   ├── ✅ pom.xml
│   ├── ✅ src/main/java/com/vermolinux/
│   └── ✅ src/main/resources/application.properties
├── ✅ start-backend.ps1
├── ✅ README.md
└── ✅ APRESENTACAO.md
```

### 2. Teste de Compilação

**Executar:**
```powershell
cd backend
mvn clean compile -DskipTests
```

**Resultado esperado:**
```
[INFO] BUILD SUCCESS
```

---

## 🚀 Teste de Execução

### 1. Iniciar o Sistema

**Opção A - Script automatizado:**
```powershell
.\start-backend.bat
ou
.\start-backend.bat
```

**Opção B - Manual:**
```powershell
cd backend
mvn spring-boot:run
```

### 2. Aguardar Inicialização

Mensagem esperada no console:
```
Started VermolinUxApplication in X.XXX seconds
```

### 3. Verificar Acesso

**Abrir no navegador:**
```
http://localhost:8080/api/swagger-ui.html
```

**Se funcionou:** Você verá a interface do Swagger com os endpoints! ✅

---

## 🧪 Teste Completo do Sistema

### 1. Login

No Swagger UI:

1. Encontre `POST /api/auth/login`
2. Clique em "Try it out"
3. Use:
```json
{
  "username": "gerente",
  "password": "gerente123"
}
```
4. Clique em "Execute"
5. **Resultado esperado:** Status 200 + token JWT

### 2. Autorizar

1. Clique no botão **"Authorize"** (cadeado no topo)
2. Digite: `Bearer <cole-o-token-aqui>`
3. Clique em "Authorize"

### 3. Teste de Endpoint

1. Encontre `GET /api/products`
2. Clique em "Try it out" → "Execute"
3. **Resultado esperado:** Lista com 5 produtos

---

## ❌ Problemas Comuns

### "mvn não é reconhecido"
- ✅ Maven não está no PATH
- **Solução:** Instale Maven e adicione ao PATH (ver seção acima)

### "java não é reconhecido"
- ✅ Java não está instalado ou não está no PATH
- **Solução:** Instale Java JDK 17+ (ver seção acima)

### Porta 8080 já em uso
- ✅ Outro aplicativo está usando a porta 8080
- **Solução:** 
  - Feche outros servidores
  - OU edite `backend/src/main/resources/application.properties`:
    ```properties
    server.port=8081
    ```

### Erro de compilação
- ✅ Dependências não baixadas
- **Solução:**
  ```powershell
  cd backend
  mvn clean install -U
  ```

---

## 📞 Suporte

Se os problemas persistirem:

1. Verifique os logs no console
2. Consulte `README.md`
3. Consulte `APRESENTACAO.md`
4. Abra uma Issue no GitHub

---

## ✅ Sistema Pronto!

Se todos os testes acima passaram, seu sistema está 100% funcional e pronto para:

- ✅ Apresentação no seminário
- ✅ Demonstração para professores
- ✅ Desenvolvimento adicional
- ✅ Testes completos

