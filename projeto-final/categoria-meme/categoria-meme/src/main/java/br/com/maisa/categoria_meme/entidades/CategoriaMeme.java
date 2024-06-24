package br.com.maisa.categoria_meme.entidades;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "categoria_meme")
public class CategoriaMeme {

    @Id
    private String id;

    @Column
    private Date dataCadastro;

    @Column
    private String descricao;

    @Column
    private String nome;

    @Column
    private String usuarioId;

    public CategoriaMeme() {
    }

    public CategoriaMeme(String id, String nome, String descricao, Date dataCadastro, String usuarioId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
        this.usuarioId = usuarioId;
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

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
