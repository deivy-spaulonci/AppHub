package com.br.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FornecedorCloudResponseDTO {

    @JsonProperty("razao_social")
    public String nome;
    @JsonProperty("estabelecimento")
    public EstabelecimentoCloudResponseDTO estabelecimentoCloudResponseDTO;

    public String getFantasia(){
        return getEstabelecimentoCloudResponseDTO().getFantasia();
    }
    public String getCnpj(){
        return getEstabelecimentoCloudResponseDTO().getCnpj();
    }
    public String getIbgeCod(){
        return getEstabelecimentoCloudResponseDTO().getCidade().getIbgeCod();
    }
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class EstabelecimentoCloudResponseDTO {
    @JsonProperty("nome_fantasia")
    public String fantasia;
    @JsonProperty("cnpj")
    public String cnpj;
    @JsonProperty("cidade")
    public CidadeCloudResponseDTO cidade;
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class CidadeCloudResponseDTO {
    @JsonProperty("ibge_id")
    public String ibgeCod;

}
