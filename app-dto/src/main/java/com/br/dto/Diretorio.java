package com.br.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diretorio implements Serializable {
    private String nome;
    private List<Diretorio> subDiretorios;
    private List<String> arquivos;

    public Diretorio(String nome) {
        this.nome = nome;
        this.subDiretorios = new ArrayList<>();
        this.arquivos = new ArrayList<>();
    }

    // Adiciona um subdiretório
    public void addSubDiretorio(Diretorio subdiretorio) {
        this.subDiretorios.add(subdiretorio);
    }

    // Adiciona um arquivo
    public void addArquivo(String arquivo) {
        this.arquivos.add(arquivo);
    }
}
