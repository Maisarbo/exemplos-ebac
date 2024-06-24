package br.com.maisa.usuario.servicos;

import br.com.maisa.usuario.entidades.Usuario;
import br.com.maisa.usuario.repositorios.RepositorioUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ServicoUsuario {

    private final RepositorioUsuario repositorioUsuario;

    public ServicoUsuario(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    public Iterable<Usuario> encontrarTodos(){
        return repositorioUsuario.findAll();
    }

    public  Usuario criarUsuario(Usuario usuario){
        return repositorioUsuario.save(usuario);
    }

    public Usuario findById(String id) {
        return repositorioUsuario.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

}
