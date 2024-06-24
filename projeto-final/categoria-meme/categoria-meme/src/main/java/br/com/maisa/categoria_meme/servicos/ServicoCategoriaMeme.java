package br.com.maisa.categoria_meme.servicos;

import br.com.maisa.categoria_meme.entidades.CategoriaMeme;
import br.com.maisa.categoria_meme.repositorios.RepositorioCategoriaMeme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import org.springframework.web.server.ResponseStatusException;


@Component
public class ServicoCategoriaMeme {

    @Autowired
    private final RepositorioCategoriaMeme repositorioCategoriaMeme;
    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    public ServicoCategoriaMeme(RepositorioCategoriaMeme repositorioCategoriaMeme, RestTemplate restTemplate) {
        this.repositorioCategoriaMeme = repositorioCategoriaMeme;
        this.restTemplate = restTemplate;
    }

    public CategoriaMeme criarCategoriaMeme(CategoriaMeme categoriaMeme) {
        try {
            // Verifica se o usuário existe no serviço externo
            ResponseEntity<Void> response = restTemplate.exchange(
                    "http://localhost:8081/usuarios/" + categoriaMeme.getUsuarioId(),
                    HttpMethod.HEAD,
                    null,
                    Void.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return repositorioCategoriaMeme.save(categoriaMeme);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
            }
        } catch (ResponseStatusException e) {
            throw e; // Lança a exceção para ser tratada externamente
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao verificar usuário", e);
        }
    }


    public Iterable<CategoriaMeme> encontrarTodos() {
        return repositorioCategoriaMeme.findAll();
    }
    public CategoriaMeme findById(String id) {
        return repositorioCategoriaMeme.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

}