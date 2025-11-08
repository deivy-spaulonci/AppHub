package com.br.dto.response;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.br.entity.Fornecedor}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class FornecedorResponseDTO implements Serializable {
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private String cpf;
    private CidadeResponseDTO cidade;
}