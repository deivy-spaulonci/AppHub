package com.br.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FornecedorCloudDto {

    @JsonProperty("razao_social")
    public String nome;
    @JsonProperty("estabelecimento")
    public EstabelecimentoCloudDto estabelecimentoCloudDto;

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
class EstabelecimentoCloudDto {
    @JsonProperty("nome_fantasia")
    public String fantasia;
    @JsonProperty("cnpj")
    public String cnpj;
    @JsonProperty("cidade")
    public CidadeCloudDto cidade;
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class CidadeCloudDto {
    @JsonProperty("ibge_id")
    public String ibgeCod;

}
