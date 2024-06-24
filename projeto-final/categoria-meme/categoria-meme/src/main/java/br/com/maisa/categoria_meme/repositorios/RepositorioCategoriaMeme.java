package br.com.maisa.categoria_meme.repositorios;

import br.com.maisa.categoria_meme.entidades.CategoriaMeme;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioCategoriaMeme extends CrudRepository<CategoriaMeme,String> {
}
