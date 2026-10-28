package com.vermolinux;

import com.fasterxml.jackson.databind.JsonNode;
import com.vermolinux.model.User.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Caracteriza o ciclo produto -> venda -> estoque (RF14, RF18) e as regras de negócio de erro. */
class FluxoDeVendaTest extends TestSupport {

    private String gerente;
    private String caixa;

    @BeforeEach
    void papeis() throws Exception {
        gerente = tokenDe(UserRole.GERENTE);
        caixa = tokenDe(UserRole.CAIXA);
    }

    private long criaProduto(String preco, String estoque) throws Exception {
        String codigo = "T" + UUID.randomUUID().toString().substring(0, 8);
        Resposta r = chama(HttpMethod.POST, "/products", gerente, Map.of(
                "code", codigo, "name", "Produto " + codigo, "price", new BigDecimal(preco),
                "unit", "KG", "stockQuantity", new BigDecimal(estoque), "minStock", BigDecimal.ONE));
        assertTrue(r.status() == 200 || r.status() == 201, "criar produto: " + r.status() + " " + r.mensagem());
        return r.dados().path("id").asLong();
    }

    private long abreVenda() throws Exception {
        Resposta r = chama(HttpMethod.POST, "/sales", caixa, null);
        assertTrue(r.status() == 200 || r.status() == 201, "abrir venda: " + r.status() + " " + r.mensagem());
        assertEquals("OPEN", r.dados().path("status").asText());
        return r.dados().path("id").asLong();
    }

    private Resposta adiciona(long venda, long produto, String quantidade) throws Exception {
        return chama(HttpMethod.POST, "/sales/" + venda + "/items", caixa,
                Map.of("productId", produto, "quantity", new BigDecimal(quantidade)));
    }

    private Resposta finaliza(long venda, String metodo, String pago) throws Exception {
        return chama(HttpMethod.POST, "/sales/" + venda + "/finalize", caixa,
                Map.of("paymentMethod", metodo, "amountPaid", new BigDecimal(pago)));
    }

    private BigDecimal estoqueDe(long produto) throws Exception {
        return dec(chama(HttpMethod.GET, "/products/" + produto, gerente, null).dados().path("stockQuantity"));
    }

    @Test
    void vendaEmDinheiroCalculaTrocoEBaixaOEstoqueComMovimentoDeVenda() throws Exception {
        long produto = criaProduto("10.00", "10");
        long venda = abreVenda();
        Resposta item = adiciona(venda, produto, "2");
        assertTrue(item.status() == 200 || item.status() == 201, "item: " + item.status() + " " + item.mensagem());
        assertEquals(0, new BigDecimal("20.00").compareTo(dec(item.dados().path("totalAmount"))));

        Resposta pago = finaliza(venda, "DINHEIRO", "50.00");
        assertEquals(200, pago.status());
        assertEquals("PAID", pago.dados().path("status").asText());
        assertEquals(0, new BigDecimal("30.00").compareTo(dec(pago.dados().path("changeAmount"))));

        assertEquals(0, new BigDecimal("8").compareTo(estoqueDe(produto)), "estoque 10 - 2");
        JsonNode movimentos = chama(HttpMethod.GET, "/stock/movements/product/" + produto, gerente, null).dados();
        assertEquals(1, movimentos.size());
        assertEquals("VENDA", movimentos.get(0).path("movementType").asText());
        assertEquals(0, new BigDecimal("10").compareTo(dec(movimentos.get(0).path("previousQuantity"))));
        assertEquals(0, new BigDecimal("8").compareTo(dec(movimentos.get(0).path("newQuantity"))));
    }

    @Test
    void pagamentoEmCartaoNaoGeraTroco() throws Exception {
        long produto = criaProduto("10.00", "10");
        long venda = abreVenda();
        adiciona(venda, produto, "2");
        Resposta pago = finaliza(venda, "CARTAO", "30.00");
        assertEquals(200, pago.status());
        assertEquals(0, BigDecimal.ZERO.compareTo(dec(pago.dados().path("changeAmount"))));
    }

    @Test
    void naoAdicionaItemAcimaDoEstoque() throws Exception {
        long produto = criaProduto("10.00", "1");
        long venda = abreVenda();
        assertEquals(400, adiciona(venda, produto, "2").status());
        assertEquals(0, BigDecimal.ONE.compareTo(estoqueDe(produto)), "estoque intacto");
    }

    @Test
    void naoFinalizaVendaSemItens() throws Exception {
        long venda = abreVenda();
        Resposta r = finaliza(venda, "DINHEIRO", "10.00");
        assertEquals(400, r.status());
        assertEquals("Não é possível finalizar uma venda sem itens", r.mensagem());
    }

    @Test
    void naoFinalizaComValorPagoMenorQueOTotal() throws Exception {
        long produto = criaProduto("10.00", "10");
        long venda = abreVenda();
        adiciona(venda, produto, "2");
        Resposta r = finaliza(venda, "DINHEIRO", "19.99");
        assertEquals(400, r.status());
        assertEquals("Valor pago é menor que o total da venda", r.mensagem());
        assertEquals(0, new BigDecimal("10").compareTo(estoqueDe(produto)), "estoque intacto");
    }

    @Test
    void vendaJaPagaNaoFinalizaDeNovoNemRecebeItem() throws Exception {
        long produto = criaProduto("10.00", "10");
        long venda = abreVenda();
        adiciona(venda, produto, "1");
        assertEquals(200, finaliza(venda, "PIX", "10.00").status());
        assertEquals(400, finaliza(venda, "PIX", "10.00").status());
        assertEquals(400, adiciona(venda, produto, "1").status());
        assertEquals(0, new BigDecimal("9").compareTo(estoqueDe(produto)), "baixou uma vez só");
    }

    @Test
    void vendaCanceladaNaoRecebeItemNemBaixaEstoque() throws Exception {
        long produto = criaProduto("10.00", "10");
        long venda = abreVenda();
        assertEquals(200, chama(HttpMethod.POST, "/sales/" + venda + "/cancel?reason=teste", caixa, null).status());
        assertEquals(400, adiciona(venda, produto, "1").status());
        assertEquals(0, new BigDecimal("10").compareTo(estoqueDe(produto)));
    }

    @Test
    void duasVendasAbertasPodemDeixarOEstoqueNegativoPoisAFinalizacaoNaoReverifica() throws Exception {
        long produto = criaProduto("10.00", "1");
        long a = abreVenda();
        long b = abreVenda();
        assertEquals(200, adiciona(a, produto, "1").status());
        assertEquals(200, adiciona(b, produto, "1").status(), "a checagem de estoque é só na inclusão do item");
        assertEquals(200, finaliza(a, "PIX", "10.00").status());
        assertEquals(200, finaliza(b, "PIX", "10.00").status(), "a segunda finaliza mesmo sem estoque");
        assertEquals(0, new BigDecimal("-1").compareTo(estoqueDe(produto)), "estoque fica negativo");
    }
}
