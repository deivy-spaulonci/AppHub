package com.br.dto;

import com.br.entity.Documento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * DTO for {@link Documento}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDto implements Serializable {
    @NotNull(message = "Id do Documento Inválido!")
    BigInteger id;
    @NotNull(message = "Nome do Documento Inválido!")
    @Size(message = "Nome do Documento muito extenso!", min = 3, max = 255)
    @NotEmpty(message = "Nome do Documento Inválido!")
    @NotBlank(message = "Nome do Documento Inválido!")
    String nomeArquivo;
    String descricao;
    @NotNull(message = "Tipo de documento inválido!")
    TipoDocumentoDto tipoDocumento;
}