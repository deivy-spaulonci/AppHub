package com.br.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginacaoDTO {
    private Integer size = 30;
    private Integer numberPage = 1;
    private Integer order = 1;
    private Integer idSelect;
    private String sort;
}

