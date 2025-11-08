package com.br.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiretorioResponseDTO implements Serializable {
    private String nome;
    private List<DiretorioResponseDTO> subDiretorioResponsDTOS;
    private List<String> arquivos;

    public DiretorioResponseDTO(String nome) {
        this.nome = nome;
        this.subDiretorioResponsDTOS = new ArrayList<>();
        this.arquivos = new ArrayList<>();
    }

    // Adiciona um subdiretório
    public void addSubDiretorio(DiretorioResponseDTO subdiretorio) {
        this.subDiretorioResponsDTOS.add(subdiretorio);
    }

    // Adiciona um arquivo
    public void addArquivo(String arquivo) {
        this.arquivos.add(arquivo);
    }
}
