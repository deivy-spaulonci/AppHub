package com.br.dto.request;

import com.br.dto.response.CidadeResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class FornecedorRequestDTO implements Serializable {

    @NotBlank(message = "Nome fornecedor vazio!")
    private String nome;

    @NotBlank(message = "Razão Social vazia!")
    private String razaoSocial;

    @Size(max = 14, message = "CNPJ fora do tamanho especificado")
    @Pattern(regexp = "[0-9]*")
    private String cnpj;

    private String cpf;

    @NotBlank(message = "Cidade inválida!")
    private CidadeRequestDTO cidade;
}