package br.com.maisa.meme.servicos;

import br.com.maisa.meme.entidades.Meme;
import br.com.maisa.meme.repositorios.RepositorioMeme;
import org.apache.el.stream.Optional;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ServicoMeme {

    private final RepositorioMeme repositorioMeme;
    private final RestTemplate restTemplate;

    public ServicoMeme(RepositorioMeme repositorioMeme, RestTemplate restTemplate){
        this.repositorioMeme = repositorioMeme;
        this.restTemplate = restTemplate;
    }

    public Iterable<Meme> encontrarTodos(){
        return repositorioMeme.findAll();
    }

    public Meme criarMeme(Meme meme) {
        try {
            // Verificar se o usuário existe no serviço externo
            ResponseEntity<Void> responseUsuario = restTemplate.exchange(
                    "http://localhost:8081/usuarios/" + meme.getUsuarioId(),
                    HttpMethod.HEAD,
                    null,
                    Void.class
            );

            // Verificar se a categoria de meme existe
            ResponseEntity<Void> responseCategoria = restTemplate.exchange(
                    "http://localhost:8082/categoria-meme/"+ meme.getCategoriaMemeId(),
                    HttpMethod.HEAD,
                    null,
                    Void.class
            );

            if (responseUsuario.getStatusCode() == HttpStatus.OK && responseCategoria.getStatusCode() == HttpStatus.OK) {
                return repositorioMeme.save(meme);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário ou Categoria não encontrado");
            }
        } catch (HttpClientErrorException.NotFound | ResponseStatusException e) {
            throw e; // Lança a exceção para ser tratada externamente
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao verificar usuário ou categoria", e);
        }
    }
    public Meme findById(String id) {
        return repositorioMeme.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meme não encontrado"));
    }


}
