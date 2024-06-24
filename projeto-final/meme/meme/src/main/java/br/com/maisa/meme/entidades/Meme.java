package br.com.maisa.meme.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;

@Entity
@Table(name = "meme")
public class Meme {
    @Id
    private String id;
    private String nome;
    private String descricao;
    @NotNull
    private Date dataCadastro;
//referencia ao id servicos externos
    @NotNull
    private String categoriaMemeId;
    private String usuarioId;
    @NotNull
    private String urlImagem;

    public Meme() {
    }

    public Meme(String id, String nome, String descricao, Date dataCadastro, String categoriaMemeId, String usuarioId, String urlImagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.categoriaMemeId = categoriaMemeId;
        this.usuarioId = usuarioId;
        this.urlImagem = urlImagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getCategoriaMemeId() {
        return categoriaMemeId;
    }

    public void setCategoriaMemeId(String categoriaMemeId) {
        this.categoriaMemeId = categoriaMemeId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
