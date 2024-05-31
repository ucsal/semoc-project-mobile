package com.example.semocavi2.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_palestrantes", indices = @Index(value = {"id"}, unique = true))
public class PalestranteModel {

    @PrimaryKey(autoGenerate = false)

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;
    @SerializedName("nome")
    @ColumnInfo(name = "nome")
    private String nome;

    @SerializedName("bio")
    @ColumnInfo(name = "bio")
    private String bio;


    @SerializedName("foto_url")
    @ColumnInfo(name = "foto_url")
    private String fotoUrl;


    @SerializedName("tipo")
    @ColumnInfo(name = "tipo")
    private String tipo;

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
