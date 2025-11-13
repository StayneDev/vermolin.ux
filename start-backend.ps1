# ========================================
#  Vermolin.UX - Sistema Hortifruti
#  Script de Inicialização Automática
# ========================================

$ErrorActionPreference = "Stop"

function Write-Step {
    param([string]$Message)
    Write-Host "`n[*] $Message" -ForegroundColor Cyan
}

function Write-Success {
    param([string]$Message)
    Write-Host "✅ $Message" -ForegroundColor Green
}

function Write-Error {
    param([string]$Message)
    Write-Host "❌ $Message" -ForegroundColor Red
}

function Write-Info {
    param([string]$Message)
    Write-Host "   $Message" -ForegroundColor White
}

Clear-Host
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "      VERMOLIN.UX - SISTEMA HORTIFRUTI         " -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan

# Verificar pré-requisitos
Write-Step "Verificando pré-requisitos..."

# Verificar Java
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Success "Java encontrado: $javaVersion"
} catch {
    Write-Error "Java 17+ não encontrado!"
    Write-Info "Instale o Java JDK 17 ou superior"
    Write-Info "Download: https://adoptium.net/"
    exit 1
}

# Verificar Maven
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
    Write-Success "Maven encontrado: $mavenVersion"
} catch {
    Write-Error "Maven não encontrado!"
    Write-Info "Instale o Apache Maven"
    Write-Info "Download: https://maven.apache.org/download.cgi"
    exit 1
}

# Navegar para backend
Write-Step "Navegando para pasta do backend..."
$originalPath = Get-Location
Set-Location -Path "backend"
Write-Success "Diretório: $(Get-Location)"

# Compilar projeto
Write-Step "Compilando projeto (mvn clean install -DskipTests)..."
$compileOutput = mvn clean install -DskipTests 2>&1

if ($LASTEXITCODE -ne 0) {
    Write-Error "Falha na compilação!"
    Write-Info "Verifique os logs acima para detalhes do erro"
    Set-Location -Path $originalPath
    exit 1
}

Write-Success "Compilação concluída!"

# Exibir informações importantes
Write-Host "`n================================================" -ForegroundColor Cyan
Write-Host "           SERVIDOR INICIANDO...                " -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan

Write-Host "`n📍 ACESSE O SISTEMA:" -ForegroundColor Yellow
Write-Info "Swagger UI: http://localhost:8080/api/swagger-ui.html"
Write-Info "API Base:   http://localhost:8080/api"

Write-Host "`n👥 USUÁRIOS DE TESTE:" -ForegroundColor Yellow
Write-Info "Gerente:    gerente / gerente123    (Acesso Total)"
Write-Info "Estoquista: estoquista / estoque123 (Produtos/Estoque)"
Write-Info "Caixa:      caixa / caixa123        (Vendas/PDV)"

Write-Host "`n📦 DADOS PRÉ-CADASTRADOS:" -ForegroundColor Yellow
Write-Info "• 5 Produtos (Banana, Tomate, Alface, Laranja, Ovos)"
Write-Info "• 3 Fornecedores"
Write-Info "• 3 Usuários (1 de cada role)"

Write-Host "`n🔧 CONTROLES:" -ForegroundColor Yellow
Write-Info "Pressione Ctrl+C para parar o servidor"
Write-Info "Aguarde a mensagem 'Started VermolinUxApplication'"

Write-Host "`n================================================`n" -ForegroundColor Cyan

# Executar aplicação
mvn spring-boot:run

# Voltar ao diretório original ao encerrar
Set-Location -Path $originalPath
