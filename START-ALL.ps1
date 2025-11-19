# =====================================================================
# START-ALL.ps1 - Inicia Backend + Frontend
# Vemolin UX - Sistema de Gestao para Hortifruti
# =====================================================================

Write-Host ""
Write-Host "==============================================================================="
Write-Host "                   INICIANDO VEMOLIN UX - SISTEMA COMPLETO" -ForegroundColor Cyan
Write-Host ""
Write-Host "  Backend:  http://localhost:8080"
Write-Host "  Frontend: http://localhost:4200"
Write-Host "  Database: http://localhost:8080/h2-console"
Write-Host "  Swagger:  http://localhost:8080/api/swagger-ui.html"
Write-Host ""
Write-Host "  Login: gerente / gerente123"
Write-Host "==============================================================================="
Write-Host ""

# =====================================================================
# 1. VERIFICAR PRE-REQUISITOS
# =====================================================================

Write-Host "[1/4] Verificando pre-requisitos..." -ForegroundColor Yellow
Write-Host ""

# Verificar Java
try {
    $javaVersion = java -version 2>&1
    Write-Host "[OK] Java encontrado" -ForegroundColor Green
    $javaVersion | Select-Object -First 1 | ForEach-Object { Write-Host "     $_" }
} catch {
    Write-Host "[ERRO] Java nao encontrado!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Instale Java 17 ou superior:"
    Write-Host "https://adoptium.net/"
    Write-Host ""
    Read-Host "Pressione ENTER para sair"
    exit 1
}

# Verificar Maven
try {
    $mavenVersion = mvn -version 2>&1 | Select-Object -First 1
    Write-Host "[OK] Maven encontrado" -ForegroundColor Green
} catch {
    Write-Host "[ERRO] Maven nao encontrado!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Instale Apache Maven:"
    Write-Host "https://maven.apache.org/download.cgi"
    Write-Host ""
    Read-Host "Pressione ENTER para sair"
    exit 1
}

# Verificar Node.js
try {
    $nodeVersion = node --version 2>&1
    Write-Host "[OK] Node.js encontrado" -ForegroundColor Green
    Write-Host "     Versao: $nodeVersion"
} catch {
    Write-Host "[ERRO] Node.js nao encontrado!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Instale Node.js 18+ ou superior:"
    Write-Host "https://nodejs.org/"
    Write-Host ""
    Read-Host "Pressione ENTER para sair"
    exit 1
}

# Verificar npm
try {
    $npmVersion = npm --version 2>&1
    Write-Host "[OK] npm encontrado" -ForegroundColor Green
    Write-Host "     Versao: $npmVersion"
} catch {
    Write-Host "[ERRO] npm nao encontrado!" -ForegroundColor Red
    Read-Host "Pressione ENTER para sair"
    exit 1
}

Write-Host ""
Write-Host "[OK] Todos os pre-requisitos OK!" -ForegroundColor Green
Write-Host ""

# =====================================================================
# 2. VERIFICAR PORTAS DISPONIVEIS
# =====================================================================

Write-Host "[2/4] Verificando portas disponiveis..." -ForegroundColor Yellow
Write-Host ""

# Verificar porta 8080
$port8080 = Get-NetTCPConnection -LocalPort 8080 -ErrorAction SilentlyContinue
if ($port8080) {
    Write-Host "[AVISO] Porta 8080 ja esta em uso!" -ForegroundColor Yellow
    Write-Host "     Matando processos antigos..."
    $port8080 | ForEach-Object {
        Stop-Process -Id $_.OwningProcess -Force -ErrorAction SilentlyContinue
    }
    Start-Sleep -Seconds 1
    Write-Host "     [OK] Processo finalizado" -ForegroundColor Green
}
Write-Host "[OK] Porta 8080 disponivel" -ForegroundColor Green

# Verificar porta 4200
$port4200 = Get-NetTCPConnection -LocalPort 4200 -ErrorAction SilentlyContinue
if ($port4200) {
    Write-Host "[AVISO] Porta 4200 ja esta em uso!" -ForegroundColor Yellow
    Write-Host "     Matando processos antigos..."
    $port4200 | ForEach-Object {
        Stop-Process -Id $_.OwningProcess -Force -ErrorAction SilentlyContinue
    }
    Start-Sleep -Seconds 1
    Write-Host "     [OK] Processo finalizado" -ForegroundColor Green
}
Write-Host "[OK] Porta 4200 disponivel" -ForegroundColor Green
Write-Host ""

# =====================================================================
# 3. INICIAR BACKEND
# =====================================================================

Write-Host "[3/4] Iniciando Backend (Spring Boot)..." -ForegroundColor Yellow
Write-Host ""
Write-Host "     Compilando projeto Maven..." -ForegroundColor Cyan

Push-Location backend

Write-Host "     (isto pode levar alguns minutos...)" -ForegroundColor Gray

$buildOutput = mvn clean install -DskipTests 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Host "[ERRO] Falha ao compilar Backend!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Tente rodar manualmente:"
    Write-Host "  cd backend"
    Write-Host "  mvn clean install -DskipTests"
    Write-Host ""
    Read-Host "Pressione ENTER para sair"
    exit 1
}

Write-Host "     [OK] Build concluido com sucesso" -ForegroundColor Green
Write-Host ""
Write-Host "     Iniciando servidor Spring Boot em nova janela..." -ForegroundColor Cyan
Write-Host ""

# Iniciar Maven em nova janela PowerShell
Start-Process PowerShell -ArgumentList "-NoExit", "-Command", "mvn spring-boot:run" -WindowStyle Normal

Write-Host "     Aguardando Backend iniciar (30 segundos)..." -ForegroundColor Gray

for ($i = 30; $i -gt 0; $i--) {
    if ($i -eq 30 -or $i -eq 15 -or $i -eq 5 -or $i -eq 1) {
        Write-Host "     $i segundos..." -ForegroundColor Gray
    }
    Start-Sleep -Seconds 1
}

Write-Host "     [OK] Backend iniciado!" -ForegroundColor Green
Write-Host ""

Pop-Location

# =====================================================================
# 4. INICIAR FRONTEND
# =====================================================================

Write-Host "[4/4] Iniciando Frontend (Angular)..." -ForegroundColor Yellow
Write-Host ""
Write-Host "     Verificando dependencias npm..." -ForegroundColor Cyan

Push-Location frontend

if (-not (Test-Path "node_modules")) {
    Write-Host "     Instalando dependencias npm (primeira vez)..." -ForegroundColor Cyan
    Write-Host "     (isto pode levar alguns minutos...)" -ForegroundColor Gray
    
    $npmOutput = npm install 2>&1
    if ($LASTEXITCODE -ne 0) {
        Write-Host "[ERRO] Falha ao instalar dependencias!" -ForegroundColor Red
        Write-Host ""
        Write-Host "Tente rodar manualmente:"
        Write-Host "  cd frontend"
        Write-Host "  npm install"
        Write-Host ""
        Read-Host "Pressione ENTER para sair"
        exit 1
    }
    Write-Host "     [OK] Dependencias instaladas" -ForegroundColor Green
} else {
    Write-Host "     [OK] Dependencias ja instaladas" -ForegroundColor Green
}

Write-Host ""
Write-Host "     Iniciando servidor Angular em nova janela..." -ForegroundColor Cyan
Write-Host ""

# Iniciar npm em nova janela PowerShell
Start-Process PowerShell -ArgumentList "-NoExit", "-Command", "npm start" -WindowStyle Normal

Pop-Location

# =====================================================================
# CONCLUSAO
# =====================================================================

Write-Host ""
Write-Host "==============================================================================="
Write-Host "                  [OK] SISTEMA INICIADO COM SUCESSO!" -ForegroundColor Green
Write-Host "==============================================================================="
Write-Host ""
Write-Host "Aguardando inicializacao completa (1-2 minutos)..." -ForegroundColor Yellow
Write-Host ""
Write-Host "Apos concluir, acesse:" -ForegroundColor Cyan
Write-Host ""
Write-Host "  Frontend:  http://localhost:4200"
Write-Host "  Backend:   http://localhost:8080"
Write-Host "  Swagger:   http://localhost:8080/api/swagger-ui.html"
Write-Host "  Database:  http://localhost:8080/h2-console"
Write-Host ""
Write-Host "Credenciais padrao:"
Write-Host "  Usuario: gerente"
Write-Host "  Senha:   gerente123"
Write-Host ""
Write-Host "Pressione ENTER para fechar este terminal..."
Read-Host
