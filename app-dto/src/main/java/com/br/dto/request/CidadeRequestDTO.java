package com.br.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CidadeRequestDTO {
    private String ibgeCod;
    private String nome;
    private String uf;
}
