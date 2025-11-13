@echo off
echo ========================================
echo  Vermolin.UX Frontend
echo ========================================
echo.
echo Iniciando servidor de desenvolvimento...
echo Frontend estara disponivel em: http://localhost:4200
echo.
echo IMPORTANTE: O backend deve estar rodando em http://localhost:8080
echo.

cd /d "%~dp0"

if not exist "node_modules\" (
    echo ERRO: Dependencias nao instaladas!
    echo Execute install-frontend.bat primeiro.
    echo.
    pause
    exit /b 1
)

echo Iniciando Angular...
echo.
call npm start
