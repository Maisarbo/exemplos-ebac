package br.com.maisa.categoria_meme;

import br.com.maisa.categoria_meme.entidades.CategoriaMeme;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServicoCategoriaMemeIntegrationTest {

    @LocalServerPort
    private int port = 8082;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testChamadaServicoExterno() {
        String url = "http://localhost:8082/categorias"; // URL do endpoint que você deseja testar
        CategoriaMeme categoriaMeme = new CategoriaMeme(); // Objeto a ser enviado na requisição

        ResponseEntity<CategoriaMeme> response = restTemplate.postForEntity(url, categoriaMeme, CategoriaMeme.class);

        assertEquals(200, response.getStatusCodeValue()); // Verifica se o status da resposta é 200 OK
        assertEquals("Categoria Meme Criada", response.getBody().getDescricao()); // Verifica a resposta esperada
    }
}