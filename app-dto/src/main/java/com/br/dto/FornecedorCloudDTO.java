package com.br.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FornecedorCloudDTO {

    @JsonProperty("razao_social")
    public String nome;
    @JsonProperty("estabelecimento")
    public EstabelecimentoCloudDTO estabelecimentoCloudDto;

    public String getFantasia(){
        return estabelecimentoCloudDto.getFantasia();
    }
    public String getCnpj(){
        return getEstabelecimentoCloudDto().getCnpj();
    }
    public String getIbgeCod(){
        return getEstabelecimentoCloudDto().getCidade().getIbgeCod();
    }

}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class EstabelecimentoCloudDTO {
    @JsonProperty("nome_fantasia")
    public String fantasia;
    @JsonProperty("cnpj")
    public String cnpj;
    @JsonProperty("cidade")
    public CidadeCloudDTO cidade;
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class CidadeCloudDTO {
    @JsonProperty("ibge_id")
    public String ibgeCod;

}
