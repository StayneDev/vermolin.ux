package com.vermolinux;

import com.vermolinux.dto.DailyTotalResponse;
import com.vermolinux.model.Sale;
import com.vermolinux.repository.*;
import com.vermolinux.service.SaleService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SaleTodayTotalServiceTest {

    private static final ZoneId BR = ZoneId.of("America/Sao_Paulo");

    private static Sale sale(long cashier, Sale.SaleStatus st, Sale.PaymentMethod pm, String amount, LocalDateTime paidAt) {
        return Sale.builder().cashierId(cashier).status(st).paymentMethod(pm)
                .totalAmount(new BigDecimal(amount)).paidAt(paidAt).build();
    }

    /** Repositório em memória: aplica os mesmos filtros (status, intervalo de paidAt, caixa) da consulta real. */
    private SaleService serviceWith(List<Sale> db) {
        SaleRepository repo = mock(SaleRepository.class);
        when(repo.findByStatusInAndPaidAtGreaterThanEqualAndPaidAtLessThan(any(), any(), any()))
                .thenAnswer(i -> db.stream()
                        .filter(s -> i.<List<Sale.SaleStatus>>getArgument(0).contains(s.getStatus())
                                && !s.getPaidAt().isBefore(i.getArgument(1))
                                && s.getPaidAt().isBefore(i.getArgument(2)))
                        .toList());
        when(repo.findByCashierIdAndStatusInAndPaidAtGreaterThanEqualAndPaidAtLessThan(any(), any(), any(), any()))
                .thenAnswer(i -> db.stream()
                        .filter(s -> s.getCashierId().equals(i.getArgument(0))
                                && i.<List<Sale.SaleStatus>>getArgument(1).contains(s.getStatus())
                                && !s.getPaidAt().isBefore(i.getArgument(2))
                                && s.getPaidAt().isBefore(i.getArgument(3)))
                        .toList());
        return new SaleService(repo, mock(SaleItemRepository.class), mock(ProductRepository.class),
                mock(UserRepository.class), mock(StockMovementRepository.class));
    }

    private final ZonedDateTime now = ZonedDateTime.of(2026, 11, 19, 22, 30, 0, 0, BR);
    private final LocalDateTime today = LocalDateTime.of(2026, 11, 19, 10, 0);

    @Test
    void caixaComDuasPagasEUmaCanceladaSomaSoAsPagas() {
        List<Sale> db = List.of(
                sale(1, Sale.SaleStatus.PAID, Sale.PaymentMethod.DINHEIRO, "10.50", today),
                sale(1, Sale.SaleStatus.PAID, Sale.PaymentMethod.PIX, "20.00", today),
                sale(1, Sale.SaleStatus.CANCELLED, Sale.PaymentMethod.PIX, "99.00", today));
        DailyTotalResponse r = serviceWith(db).getTodayTotal(1L, now);
        assertEquals(2, r.getQuantity());
        assertEquals(new BigDecimal("30.50"), r.getTotal());
        assertEquals(new BigDecimal("10.50"), r.getTotalByPaymentMethod().get(Sale.PaymentMethod.DINHEIRO));
        assertEquals(new BigDecimal("20.00"), r.getTotalByPaymentMethod().get(Sale.PaymentMethod.PIX));
        assertEquals(BigDecimal.ZERO, r.getTotalByPaymentMethod().get(Sale.PaymentMethod.CARTAO));
    }

    @Test
    void vendaDeOntemNaoEntraEVendaDas22hDeBrasiliaEntra() {
        List<Sale> db = List.of(
                sale(1, Sale.SaleStatus.PAID, Sale.PaymentMethod.PIX, "5.00", LocalDateTime.of(2026, 11, 18, 23, 59)),
                sale(1, Sale.SaleStatus.PAID, Sale.PaymentMethod.PIX, "7.00", LocalDateTime.of(2026, 11, 19, 22, 0)));
        // 22:30 em Brasília = 01:30 UTC do dia seguinte: "hoje" continua sendo 19/11
        ZonedDateTime utcView = now.withZoneSameInstant(ZoneOffset.UTC);
        DailyTotalResponse r = serviceWith(db).getTodayTotal(null, utcView);
        assertEquals(1, r.getQuantity());
        assertEquals(new BigDecimal("7.00"), r.getTotal());
    }

    @Test
    void completedContaComoPaga() {
        List<Sale> db = List.of(sale(1, Sale.SaleStatus.COMPLETED, Sale.PaymentMethod.CARTAO, "8.00", today));
        assertEquals(1, serviceWith(db).getTodayTotal(null, now).getQuantity());
    }

    @Test
    void caixaBNaoVeVendasDoCaixaAEGerenteVeTudo() {
        List<Sale> db = List.of(
                sale(1, Sale.SaleStatus.PAID, Sale.PaymentMethod.PIX, "10.00", today),
                sale(2, Sale.SaleStatus.PAID, Sale.PaymentMethod.DINHEIRO, "4.00", today));
        SaleService svc = serviceWith(db);
        assertEquals(new BigDecimal("4.00"), svc.getTodayTotal(2L, now).getTotal());
        assertEquals(new BigDecimal("14.00"), svc.getTodayTotal(null, now).getTotal());
    }
}
