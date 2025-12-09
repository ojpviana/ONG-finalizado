package com.protecao.animais.model; 

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especie; 
    private String raca;
    private int idade;
    private String statusAdocao = "DISPONIVEL"; 

    private Boolean vacinado;
    private Boolean castrado;
    private String localizacao;


    @ManyToOne 
    @JoinColumn(name = "ong_id", nullable = false)
    @JsonBackReference 
    private Ong ong;


    @Lob 
    @Column(columnDefinition = "LONGTEXT") 
    private String fotoUrl;
    

    
    public Animal() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getStatusAdocao() {
        return statusAdocao;
    }

    public void setStatusAdocao(String statusAdocao) {
        this.statusAdocao = statusAdocao;
    }

    public Ong getOng() {
        return ong;
    }

    public void setOng(Ong ong) {
        this.ong = ong;
    }

    public Boolean getVacinado() { return vacinado; }
    public void setVacinado(Boolean vacinado) { this.vacinado = vacinado; }

    public Boolean getCastrado() { return castrado; }
    public void setCastrado(Boolean castrado) { this.castrado = castrado; }

    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
}