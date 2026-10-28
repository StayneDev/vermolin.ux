package com.vermolinux;

import com.vermolinux.model.User.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Matriz papel x rota (só leituras, nada é alterado). A regra efetiva é a soma de duas camadas:
 * as regras de URL do SecurityConfig e o @PreAuthorize dos controllers.
 * Colunas de esperado: GERENTE, ESTOQUISTA, CAIXA, sem token.
 */
class AutorizacaoTest extends TestSupport {

    private record Linha(String caminho, int gerente, int estoquista, int caixa, int semToken) { }

    // DEFEITO CONHECIDO: onde só o @PreAuthorize nega (low-stock e sales para CAIXA), o AccessDeniedException
    // cai no @ExceptionHandler(Exception.class) do GlobalExceptionHandler e vira 500 em vez de 403.
    // Quando for corrigido, troque os dois 500 por 403.
    private static final List<Linha> MATRIZ = List.of(
            new Linha("/products", 200, 200, 200, 403),
            new Linha("/products/low-stock", 200, 200, 500, 403),
            new Linha("/stock/movements", 200, 200, 403, 403),
            new Linha("/sales", 200, 403, 500, 403),
            new Linha("/users", 200, 403, 403, 403),
            new Linha("/suppliers", 200, 403, 403, 403));

    @Test
    void matrizDePapeisPorRota() throws Exception {
        String gerente = tokenDe(UserRole.GERENTE);
        String estoquista = tokenDe(UserRole.ESTOQUISTA);
        String caixa = tokenDe(UserRole.CAIXA);
        List<String> divergencias = new ArrayList<>();
        for (Linha l : MATRIZ) {
            confere(divergencias, l.caminho(), "GERENTE", gerente, l.gerente());
            confere(divergencias, l.caminho(), "ESTOQUISTA", estoquista, l.estoquista());
            confere(divergencias, l.caminho(), "CAIXA", caixa, l.caixa());
            confere(divergencias, l.caminho(), "sem token", null, l.semToken());
        }
        assertTrue(divergencias.isEmpty(), "divergências: " + divergencias);
    }

    @Test
    void estoquistaNaoCriaNemAlteraProdutoApesarDoPreAuthorizeDoControllerPermitir() throws Exception {
        String estoquista = tokenDe(UserRole.ESTOQUISTA);
        var corpo = java.util.Map.of("code", "X", "name", "X", "price", 1, "unit", "KG");
        List<String> divergencias = new ArrayList<>();
        confereMetodo(divergencias, HttpMethod.POST, "/products", estoquista, corpo, 403);
        confereMetodo(divergencias, HttpMethod.PUT, "/products/1", estoquista, corpo, 403);
        confereMetodo(divergencias, HttpMethod.DELETE, "/products/1", estoquista, null, 403);
        assertTrue(divergencias.isEmpty(), "divergências: " + divergencias);
    }

    @Test
    void caixaNaoMexeEmEstoqueNemEmProdutos() throws Exception {
        String caixa = tokenDe(UserRole.CAIXA);
        var movimento = java.util.Map.of("productId", 1, "movementType", "ENTRADA", "quantity", 1);
        List<String> divergencias = new ArrayList<>();
        confereMetodo(divergencias, HttpMethod.POST, "/stock/entry", caixa, movimento, 403);
        confereMetodo(divergencias, HttpMethod.POST, "/stock/exit", caixa, movimento, 403);
        confereMetodo(divergencias, HttpMethod.POST, "/stock/adjustment", caixa, movimento, 403);
        confereMetodo(divergencias, HttpMethod.DELETE, "/products/1", caixa, null, 403);
        assertTrue(divergencias.isEmpty(), "divergências: " + divergencias);
    }

    private void confere(List<String> divergencias, String caminho, String quem, String token, int esperado)
            throws Exception {
        confereMetodo(divergencias, HttpMethod.GET, caminho, token, null, esperado, quem);
    }

    private void confereMetodo(List<String> divergencias, HttpMethod metodo, String caminho, String token,
                               Object corpo, int esperado) throws Exception {
        confereMetodo(divergencias, metodo, caminho, token, corpo, esperado, "token informado");
    }

    private void confereMetodo(List<String> divergencias, HttpMethod metodo, String caminho, String token,
                               Object corpo, int esperado, String quem) throws Exception {
        int obtido = chama(metodo, caminho, token, corpo).status();
        if (obtido != esperado) {
            divergencias.add(metodo + " " + caminho + " como " + quem + ": esperado " + esperado + ", obtido " + obtido);
        }
    }
}
