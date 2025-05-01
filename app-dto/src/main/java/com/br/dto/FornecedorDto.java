package com.br.dto;

import com.br.entity.Cidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * DTO for {@link com.br.entity.Fornecedor}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class FornecedorDto implements Serializable {

    private BigInteger id;
    @NotNull(message = "Nome fornecedor vazio!")
    @Size(message = "Nome muito extenso!", min = 3, max = 255)
    @NotEmpty(message = "Nome fornecedor vazio!")
    @NotBlank(message = "Nome fornecedor vazio!")
    private String nome;
    @NotNull(message = "Razao social vazio!")
    @Size(message = "Razao social muito extensa!", min = 3, max = 255)
    @NotEmpty(message = "Razao social vazio!")
    @NotBlank(message = "Razao social vazio!")
    private String razaoSocial;
    @Size(max = 14, message = "cnpj fora do tamanho especificado")
    @Pattern(regexp = "[0-9]*")
    private String cnpj;
    private String cpf;
    @NotNull(message = "Cidade vazia!")
    private CidadeDto cidade;
}