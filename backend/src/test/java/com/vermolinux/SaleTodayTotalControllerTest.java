package com.vermolinux;

import com.vermolinux.config.SecurityConfig;
import com.vermolinux.controller.SaleController;
import com.vermolinux.dto.DailyTotalResponse;
import com.vermolinux.security.JwtTokenProvider;
import com.vermolinux.security.UserDetailsServiceImpl;
import com.vermolinux.service.SaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SaleController.class)
@Import(SecurityConfig.class)
class SaleTodayTotalControllerTest {

    @Autowired MockMvc mvc;
    @MockBean SaleService saleService;
    @MockBean JwtTokenProvider jwtTokenProvider;
    @MockBean UserDetailsServiceImpl userDetailsService;

    private void stub() {
        when(saleService.getTodayTotal(any(), any()))
                .thenReturn(new DailyTotalResponse(0, BigDecimal.ZERO, Map.of()));
    }

    @Test
    @WithMockUser(username = "2", roles = "CAIXA")
    void caixaConsultaSoOProprioId() throws Exception {
        stub();
        mvc.perform(get("/sales/today-total")).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.quantity").value(0));
        verify(saleService).getTodayTotal(eq(2L), any());
    }

    @Test
    @WithMockUser(username = "9", roles = "GERENTE")
    void gerenteConsultaLojaInteira() throws Exception {
        stub();
        mvc.perform(get("/sales/today-total")).andExpect(status().isOk());
        verify(saleService).getTodayTotal(isNull(), any());
    }

    @Test
    @WithMockUser(username = "3", roles = "ESTOQUISTA")
    void estoquistaRecebe403() throws Exception {
        mvc.perform(get("/sales/today-total")).andExpect(status().isForbidden());
        verifyNoInteractions(saleService);
    }
}
