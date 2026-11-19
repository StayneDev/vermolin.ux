package com.vermolinux;

import com.vermolinux.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.autoconfigure.exclude="
        + "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,"
        + "org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration")
@AutoConfigureMockMvc
class RotaInexistenteTest {

    @Autowired MockMvc mvc;

    @MockBean UserRepository userRepository;
    @MockBean ProductRepository productRepository;
    @MockBean SaleRepository saleRepository;
    @MockBean SaleItemRepository saleItemRepository;
    @MockBean SupplierRepository supplierRepository;
    @MockBean StockMovementRepository stockMovementRepository;
    @MockBean PlatformTransactionManager transactionManager;

    @Test
    @WithMockUser(roles = "GERENTE")
    void rotaInexistenteLogadoRetorna404() throws Exception {
        mvc.perform(get("/api/nao-existe").contextPath("/api"))
                .andExpect(status().isNotFound());
    }

    @Test
    void rotaInexistenteSemLoginRetorna401Ou403() throws Exception {
        int s = mvc.perform(get("/api/nao-existe").contextPath("/api"))
                .andReturn().getResponse().getStatus();
        assertTrue(s == 401 || s == 403, "status " + s);
    }
}
