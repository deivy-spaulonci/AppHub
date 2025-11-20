package com.br.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstabelecimentoCloudResponseDTO implements Serializable {
    @JsonProperty("nome_fantasia")
    public String nomeFantasia;
    @JsonProperty("cnpj")
    public String cnpj;
    @JsonProperty("cidade")
    public CidadeCloudResponseDTO cidadeCloudResponseDTO;
    @JsonProperty("estado")
    public EstadoCloudResponseDTO estadoCloudResponseDTO;
}
