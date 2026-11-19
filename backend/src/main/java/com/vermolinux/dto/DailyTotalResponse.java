package com.vermolinux.dto;

import com.vermolinux.model.Sale;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Total vendido no dia: quantidade, soma e soma por forma de pagamento
 */
public class DailyTotalResponse {

    private final long quantity;
    private final BigDecimal total;
    private final Map<Sale.PaymentMethod, BigDecimal> totalByPaymentMethod;

    public DailyTotalResponse(long quantity, BigDecimal total, Map<Sale.PaymentMethod, BigDecimal> totalByPaymentMethod) {
        this.quantity = quantity;
        this.total = total;
        this.totalByPaymentMethod = totalByPaymentMethod;
    }

    public long getQuantity() { return quantity; }
    public BigDecimal getTotal() { return total; }
    public Map<Sale.PaymentMethod, BigDecimal> getTotalByPaymentMethod() { return totalByPaymentMethod; }
}
