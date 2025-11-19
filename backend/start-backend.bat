@echo off
REM ============================================
REM Iniciar Backend Vermolin.UX com PostgreSQL
REM ============================================

ECHO.
ECHO ========================================
ECHO  Iniciando Backend - PostgreSQL
ECHO ========================================
ECHO.

REM Verificar se o banco de dados PostgreSQL está rodando
ECHO Verificando conexão com PostgreSQL...
tasklist /FI "IMAGENAME eq postgres.exe" 2>NUL | find /I /N "postgres.exe">NUL

IF "%ERRORLEVEL%"=="0" (
    ECHO ✓ PostgreSQL está rodando
) ELSE (
    ECHO ✗ PostgreSQL não está rodando!
    ECHO Inicie o PostgreSQL antes de continuar.
    PAUSE
    EXIT /B 1
)

REM Diretório do projeto
SET BACKEND_DIR=%~dp0

REM Iniciar o backend
cd /d "%BACKEND_DIR%"

ECHO.
ECHO Iniciando aplicação Spring Boot...
ECHO Banco de dados: postgresql://localhost:5432/vermolinux
ECHO Usuário: postgres
ECHO.

java -jar target\vermolin-ux-1.0.0.jar

PAUSE
