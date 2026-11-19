package com.vermolinux.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Total vendido no dia (fuso America/Sao_Paulo).
 */
public class DailyTotalResponse {

    private final LocalDate date;
    private final long count;
    private final BigDecimal total;
    private final Map<String, BigDecimal> byPaymentMethod;

    public DailyTotalResponse(LocalDate date, long count, BigDecimal total, Map<String, BigDecimal> byPaymentMethod) {
        this.date = date;
        this.count = count;
        this.total = total;
        this.byPaymentMethod = byPaymentMethod;
    }

    public LocalDate getDate() { return date; }
    public long getCount() { return count; }
    public BigDecimal getTotal() { return total; }
    public Map<String, BigDecimal> getByPaymentMethod() { return byPaymentMethod; }
}
