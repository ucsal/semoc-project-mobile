package com.example.semocavi2.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
// uma ideia aqui seria ter apenas uma classe de eventos, com um enum definindo se eh minicurso ou palestra, mas fica pra uma proxima

@Entity(tableName = "tb_palestra", indices = @Index(value = {"id"}, unique = true))
public class PalestraModel {
// vou receber esse cara por request, ent acho q n vou precisar gerar o id auto
    @PrimaryKey(autoGenerate = false)


    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;


    @SerializedName("isScheduled")
    @ColumnInfo(name = "isScheduled")
    private boolean isSchedule;

    @SerializedName("nome")
    @ColumnInfo(name = "nome")
    private String nome;

    @SerializedName("descricao")
    @ColumnInfo(name = "descricao")
    private String descricao;

    @SerializedName("data")
    @ColumnInfo(name = "data")
    private String data;

    @SerializedName("hora")
    @ColumnInfo(name = "hora")
    private String hora;

    @SerializedName("local")
    @ColumnInfo(name = "local")
    private String local;

    // fazer um pessoa dot get by id na hora que for listar esse cara. vai ser mais facil assim, por favor nao me julgue, se for pra fazer algo comigo que seja sexo
    @SerializedName("palestrante_id")
    @ColumnInfo(name = "palestrante_id")
    private int palestrante_id;

    @SerializedName("tema")
    @ColumnInfo(name = "tema")
    private String tema;

    @SerializedName("nivel")
    @ColumnInfo(name = "nivel")
    private String nivel;

    @SerializedName("formato")
    @ColumnInfo(name = "formato")
    private String formato;


    public boolean isSchedule() {
        return isSchedule;
    }

    public void setSchedule(boolean schedule) {
        isSchedule = schedule;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getPalestrante_id() {
        return palestrante_id;
    }

    public void setPalestrante_id(int palestrante_id) {
        this.palestrante_id = palestrante_id;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
