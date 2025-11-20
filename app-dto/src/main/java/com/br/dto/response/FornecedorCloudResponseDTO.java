package com.br.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FornecedorCloudResponseDTO implements Serializable {

    @JsonProperty("razao_social")
    public String razaoSocial;
    @JsonProperty("estabelecimento")
    public EstabelecimentoCloudResponseDTO estabelecimentoCloudResponseDTO;
}
