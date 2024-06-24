package br.com.maisa.meme.repositorios;

import br.com.maisa.meme.entidades.Meme;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioMeme extends CrudRepository<Meme,String> {
}
