package br.com.maisa.usuario.repositorios;

import br.com.maisa.usuario.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioUsuario extends CrudRepository<Usuario,String> {
}
