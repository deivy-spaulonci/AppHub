package com.br.dto.request.update;

import com.br.dto.ref.CidadeRefDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * DTO for {@link com.br.entity.Fornecedor}
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorUpdateRequestDTO implements Serializable {

    @NotNull
    private BigInteger id;

    @NotBlank(message = "Nome fornecedor vazio!")
    private String nome;

    @NotBlank(message = "Razão Social vazia!")
    private String razaoSocial;

    @Size(max = 14, message = "CNPJ fora do tamanho especificado")
    @Pattern(regexp = "[0-9]*")
    private String cnpj;

    private String cpf;

    private CidadeRefDTO cidade;
}