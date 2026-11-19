package com.vermolinux;

import com.vermolinux.model.Sale;
import com.vermolinux.model.User;
import com.vermolinux.repository.*;
import com.vermolinux.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.autoconfigure.exclude="
        + "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration")
@AutoConfigureMockMvc
class TotalDoDiaTest {

    static final ZoneId BR = ZoneId.of("America/Sao_Paulo");
    static final long CAIXA_A = 10L, CAIXA_B = 11L, GERENTE = 1L, ESTOQUISTA = 20L;

    @Autowired MockMvc mvc;
    @Autowired JwtTokenProvider jwt;

    @MockBean UserRepository userRepository;
    @MockBean ProductRepository productRepository;
    @MockBean SaleRepository saleRepository;
    @MockBean SaleItemRepository saleItemRepository;
    @MockBean SupplierRepository supplierRepository;
    @MockBean StockMovementRepository stockMovementRepository;
    @MockBean PlatformTransactionManager transactionManager;

    final List<Sale> vendas = new ArrayList<>();

    @BeforeEach
    void setUp() {
        vendas.clear();
        user("caixa_a", CAIXA_A, User.UserRole.CAIXA);
        user("caixa_b", CAIXA_B, User.UserRole.CAIXA);
        user("gerente", GERENTE, User.UserRole.GERENTE);
        user("estoquista", ESTOQUISTA, User.UserRole.ESTOQUISTA);

        // repositório em memória: aplica os mesmos filtros da consulta real
        when(saleRepository.findByStatusInAndPaidAtGreaterThanEqualAndPaidAtLessThan(any(), any(), any()))
                .thenAnswer(i -> filtra(null, i.getArgument(0), i.getArgument(1), i.getArgument(2)));
        when(saleRepository.findByCashierIdAndStatusInAndPaidAtGreaterThanEqualAndPaidAtLessThan(
                ArgumentMatchers.anyLong(), any(), any(), any()))
                .thenAnswer(i -> filtra(i.getArgument(0), i.getArgument(1), i.getArgument(2), i.getArgument(3)));
    }

    private void user(String username, long id, User.UserRole role) {
        User u = User.builder().id(id).username(username).password("x").role(role).active(true).build();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(u));
    }

    private String token(String username, long id, User.UserRole role) {
        return "Bearer " + jwt.generateToken(username, id, role.name());
    }

    private List<Sale> filtra(Long caixa, Collection<Sale.SaleStatus> st, LocalDateTime ini, LocalDateTime fim) {
        return vendas.stream()
                .filter(s -> caixa == null || caixa.equals(s.getCashierId()))
                .filter(s -> st.contains(s.getStatus()))
                .filter(s -> s.getPaidAt() != null && !s.getPaidAt().isBefore(ini) && s.getPaidAt().isBefore(fim))
                .toList();
    }

    private void venda(long caixa, String valor, Sale.PaymentMethod pm, Sale.SaleStatus st, LocalDateTime paidAt) {
        vendas.add(Sale.builder().cashierId(caixa).totalAmount(new BigDecimal(valor))
                .paymentMethod(pm).status(st).paidAt(paidAt).build());
    }

    private LocalDateTime hoje(int h) { return LocalDate.now(BR).atTime(h, 0); }

    @Test
    void caixaRecebeSoVendasPagasDeHojePorFormaDePagamento() throws Exception {
        venda(CAIXA_A, "10.00", Sale.PaymentMethod.DINHEIRO, Sale.SaleStatus.PAID, hoje(9));
        venda(CAIXA_A, "25.50", Sale.PaymentMethod.PIX, Sale.SaleStatus.PAID, hoje(10));
        venda(CAIXA_A, "99.00", Sale.PaymentMethod.CARTAO, Sale.SaleStatus.CANCELLED, hoje(11));

        mvc.perform(get("/api/sales/today-total").contextPath("/api")
                        .header("Authorization", token("caixa_a", CAIXA_A, User.UserRole.CAIXA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count").value(2))
                .andExpect(jsonPath("$.data.total").value(35.5))
                .andExpect(jsonPath("$.data.byPaymentMethod.DINHEIRO").value(10.0))
                .andExpect(jsonPath("$.data.byPaymentMethod.PIX").value(25.5))
                .andExpect(jsonPath("$.data.byPaymentMethod.CARTAO").value(0));
    }

    @Test
    void vendaDeOntemNaoEntraEVendaDas22hEntra() throws Exception {
        venda(CAIXA_A, "50.00", Sale.PaymentMethod.PIX, Sale.SaleStatus.PAID, hoje(0).minusMinutes(1));
        venda(CAIXA_A, "7.00", Sale.PaymentMethod.PIX, Sale.SaleStatus.PAID, hoje(22));

        mvc.perform(get("/api/sales/today-total").contextPath("/api")
                        .header("Authorization", token("caixa_a", CAIXA_A, User.UserRole.CAIXA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count").value(1))
                .andExpect(jsonPath("$.data.total").value(7.0));
    }

    @Test
    void caixaBNaoVeVendasDoCaixaA() throws Exception {
        venda(CAIXA_A, "10.00", Sale.PaymentMethod.DINHEIRO, Sale.SaleStatus.PAID, hoje(9));
        venda(CAIXA_B, "4.00", Sale.PaymentMethod.CARTAO, Sale.SaleStatus.COMPLETED, hoje(9));

        mvc.perform(get("/api/sales/today-total").contextPath("/api")
                        .header("Authorization", token("caixa_b", CAIXA_B, User.UserRole.CAIXA)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count").value(1))
                .andExpect(jsonPath("$.data.total").value(4.0));
    }

    @Test
    void gerenteRecebeLojaInteira() throws Exception {
        venda(CAIXA_A, "10.00", Sale.PaymentMethod.DINHEIRO, Sale.SaleStatus.PAID, hoje(9));
        venda(CAIXA_B, "4.00", Sale.PaymentMethod.CARTAO, Sale.SaleStatus.PAID, hoje(9));
        venda(CAIXA_B, "8.00", Sale.PaymentMethod.CARTAO, Sale.SaleStatus.OPEN, hoje(9));

        mvc.perform(get("/api/sales/today-total").contextPath("/api")
                        .header("Authorization", token("gerente", GERENTE, User.UserRole.GERENTE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.count").value(2))
                .andExpect(jsonPath("$.data.total").value(14.0));
    }

    @Test
    void estoquistaRecebe403() throws Exception {
        mvc.perform(get("/api/sales/today-total").contextPath("/api")
                        .header("Authorization", token("estoquista", ESTOQUISTA, User.UserRole.ESTOQUISTA)))
                .andExpect(status().isForbidden());
    }
}
