package com.example.semocavi2.ui.minicurso;

import androidx.lifecycle.ViewModel;

public class MiniCursoViewModel extends ViewModel {



        private int id;
        private String nome;
        private String descricao;
        private String data;
        private String hora;
        private String local;
        private int instrutor_id;
        private String tema;
        private String nivel;
        private String formato;

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

        public int getInstrutor_id() {
            return instrutor_id;
        }

        public void setInstrutor_id(int instrutor_id) {
            this.instrutor_id = instrutor_id;
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