package com.vermolinux;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermolinux.model.User;
import com.vermolinux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

/**
 * Base dos testes de caracterização: eles registram o comportamento ATUAL do sistema,
 * inclusive o que parece defeito. Mudar um desses comportamentos deve falhar um teste de propósito.
 * Banco e JWT vêm do ambiente (SPRING_DATASOURCE_*, JWT_SECRET); nenhum segredo mora no código.
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class TestSupport {

    protected static final String SENHA = "senha-de-teste";

    @Autowired protected MockMvc mvc;
    @Autowired protected UserRepository users;
    @Autowired protected PasswordEncoder encoder;
    protected final ObjectMapper json = new ObjectMapper();

    protected record Resposta(int status, JsonNode corpo) {
        public String mensagem() {
            return corpo.path("message").asText("");
        }

        public JsonNode dados() {
            return corpo.path("data");
        }
    }

    protected String novoUsuario(User.UserRole papel, boolean ativo) {
        String nome = papel.name().toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 8);
        users.save(User.builder()
                .username(nome)
                .password(encoder.encode(SENHA))
                .fullName("Teste " + nome)
                .role(papel)
                .active(ativo)
                .build());
        return nome;
    }

    protected String token(String usuario) throws Exception {
        Resposta r = chama(HttpMethod.POST, "/auth/login", null,
                Map.of("username", usuario, "password", SENHA));
        if (r.status() != 200) {
            throw new IllegalStateException("login de teste falhou: " + r.status() + " " + r.mensagem());
        }
        return r.dados().path("token").asText();
    }

    protected String tokenDe(User.UserRole papel) throws Exception {
        return token(novoUsuario(papel, true));
    }

    protected Resposta chama(HttpMethod metodo, String caminho, String token, Object corpo) throws Exception {
        MockHttpServletRequestBuilder req = request(metodo, caminho);
        if (token != null) {
            req.header("Authorization", "Bearer " + token);
        }
        if (corpo != null) {
            req.contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(corpo));
        }
        var res = mvc.perform(req).andReturn().getResponse();
        String texto = res.getContentAsString(StandardCharsets.UTF_8);
        JsonNode arvore = texto.isBlank() ? json.createObjectNode() : json.readTree(texto);
        return new Resposta(res.getStatus(), arvore);
    }

    protected static BigDecimal dec(JsonNode no) {
        return new BigDecimal(no.asText());
    }
}
