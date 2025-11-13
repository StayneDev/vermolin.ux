@echo off
REM ========================================
REM  Vermolin.UX - Sistema Hortifruti
REM  Script de Inicialização (Windows)
REM ========================================

echo ================================================
echo       VERMOLIN.UX - SISTEMA HORTIFRUTI         
echo ================================================
echo.

REM Verificar Java
echo [*] Verificando Java...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Java 17+ nao encontrado!
    echo Instale o Java JDK 17 ou superior: https://adoptium.net/
    pause
    exit /b 1
)
echo [OK] Java encontrado
echo.

REM Verificar Maven
echo [*] Verificando Maven...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERRO] Maven nao encontrado!
    echo Instale o Apache Maven: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)
echo [OK] Maven encontrado
echo.

REM Navegar para backend
echo [*] Navegando para pasta do backend...
cd backend
echo.

REM Compilar projeto
echo [*] Compilando projeto (mvn clean install -DskipTests)...
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo.
    echo [ERRO] Falha na compilacao!
    cd ..
    pause
    exit /b 1
)
echo.
echo [OK] Compilacao concluida!
echo.

REM Exibir informações
echo ================================================
echo            SERVIDOR INICIANDO...                
echo ================================================
echo.
echo ACESSE O SISTEMA:
echo   Swagger UI: http://localhost:8080/api/swagger-ui.html
echo   API Base:   http://localhost:8080/api
echo.
echo USUARIOS DE TESTE:
echo   Gerente:    gerente / gerente123    (Acesso Total)
echo   Estoquista: estoquista / estoque123 (Produtos/Estoque)
echo   Caixa:      caixa / caixa123        (Vendas/PDV)
echo.
echo DADOS PRE-CADASTRADOS:
echo   * 5 Produtos (Banana, Tomate, Alface, Laranja, Ovos)
echo   * 3 Fornecedores
echo   * 3 Usuarios (1 de cada role)
echo.
echo CONTROLES:
echo   Pressione Ctrl+C para parar o servidor
echo   Aguarde a mensagem 'Started VermolinUxApplication'
echo.
echo ================================================
echo.

REM Executar aplicação
call mvn spring-boot:run

REM Voltar ao diretório original
cd ..
