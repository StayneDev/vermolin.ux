package com.vermolinux;

import com.vermolinux.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Caracteriza login, token e mensagens de erro (RF1-RF4). */
class AutenticacaoTest extends TestSupport {

    private Resposta login(String usuario, String senha) throws Exception {
        return chama(HttpMethod.POST, "/auth/login", null, Map.of("username", usuario, "password", senha));
    }

    @Test
    void loginValidoDevolveTokenBearerEPapel() throws Exception {
        String usuario = novoUsuario(User.UserRole.CAIXA, true);
        Resposta r = login(usuario, SENHA);
        assertEquals(200, r.status());
        assertTrue(r.corpo().path("success").asBoolean());
        assertEquals("Bearer", r.dados().path("type").asText());
        assertEquals("CAIXA", r.dados().path("role").asText());
        assertEquals(usuario, r.dados().path("username").asText());
        assertEquals(3, r.dados().path("token").asText().split("\\.").length, "JWT tem 3 segmentos");
    }

    @Test
    void senhaErradaDevolve403ComMensagemGenerica() throws Exception {
        String usuario = novoUsuario(User.UserRole.CAIXA, true);
        Resposta r = login(usuario, "outra-senha");
        assertEquals(403, r.status(), "credencial inválida é 403, não 401");
        assertEquals("Credenciais inválidas", r.mensagem());
    }

    @Test
    void usuarioInexistenteTemAMesmaMensagemDeSenhaErrada() throws Exception {
        Resposta r = login("nao_existe_" + System.nanoTime(), SENHA);
        assertEquals(403, r.status());
        assertEquals("Credenciais inválidas", r.mensagem());
    }

    @Test
    void usuarioInativoRecebeMensagemDiferenteEPermiteEnumerarUsuarios() throws Exception {
        String usuario = novoUsuario(User.UserRole.CAIXA, false);
        Resposta r = login(usuario, SENHA);
        assertEquals(403, r.status());
        assertEquals("Usuário inativo", r.mensagem());
    }

    @Test
    void camposInvalidosDevolve400() throws Exception {
        assertEquals(400, login("ab", SENHA).status(), "usuário com menos de 3 caracteres");
        assertEquals(400, login("usuario_valido", "123").status(), "senha com menos de 4 caracteres");
        assertEquals(400, chama(HttpMethod.POST, "/auth/login", null, Map.of()).status(), "corpo vazio");
    }

    @Test
    void rotaProtegidaSemTokenDevolve403() throws Exception {
        assertEquals(403, chama(HttpMethod.GET, "/products", null, null).status());
    }

    @Test
    void tokenAdulteradoDevolve403() throws Exception {
        String token = tokenDe(User.UserRole.GERENTE);
        String adulterado = token.substring(0, token.length() - 2) + (token.endsWith("AA") ? "BB" : "AA");
        assertEquals(200, chama(HttpMethod.GET, "/products", token, null).status());
        assertEquals(403, chama(HttpMethod.GET, "/products", adulterado, null).status());
    }

    @Test
    void logoutNaoInvalidaOTokenPorqueOSistemaESemEstado() throws Exception {
        String token = tokenDe(User.UserRole.GERENTE);
        assertEquals(200, chama(HttpMethod.POST, "/auth/logout", token, null).status());
        assertEquals(200, chama(HttpMethod.GET, "/products", token, null).status(),
                "o mesmo token continua válido depois do logout");
    }
}
