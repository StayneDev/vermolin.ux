package com.vermolinux.dto;

import com.vermolinux.model.Sale;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO para finalização de venda com pagamento (RF16, RF17)
 */
public class PaymentRequest {
    
    @NotNull(message = "Método de pagamento é obrigatório")
    private Sale.PaymentMethod paymentMethod;
    
    @NotNull(message = "Valor pago é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor pago deve ser maior que zero")
    private BigDecimal amountPaid;
    
    // Getters and Setters
    public Sale.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(Sale.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public BigDecimal getAmountPaid() {
        return amountPaid;
    }
    
    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
}


