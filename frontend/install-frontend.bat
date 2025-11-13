@echo off
echo ========================================
echo  Vermolin.UX Frontend - Instalacao
echo ========================================
echo.

echo Verificando Node.js...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Node.js nao encontrado!
    echo Por favor, instale Node.js 18+ em https://nodejs.org
    pause
    exit /b 1
)

echo Node.js encontrado: 
node --version

echo.
echo Instalando dependencias do Angular...
echo Isso pode levar alguns minutos...
echo.

cd /d "%~dp0"
call npm install

if %errorlevel% neq 0 (
    echo.
    echo ERRO: Falha na instalacao das dependencias!
    pause
    exit /b 1
)

echo.
echo ========================================
echo  Instalacao concluida com sucesso!
echo ========================================
echo.
echo Para iniciar o frontend, execute:
echo   start-frontend.bat
echo.
pause
