package com.br.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FornecedorAWS {

    @JsonProperty("razao_social")
    public String nome;
    @JsonProperty("estabelecimento")
    public Estabelecimento estabelecimento;

    public String getFantasia(){
        return estabelecimento.getFantasia();
    }
    public String getCnpj(){
        return getEstabelecimento().getCnpj();
    }
    public String getIbgeCod(){
        return getEstabelecimento().getCidade().getIbgeCod();
    }

}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Estabelecimento{
    @JsonProperty("nome_fantasia")
    public String fantasia;
    @JsonProperty("cnpj")
    public String cnpj;
    @JsonProperty("cidade")
    public CidadeAWS cidade;
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class CidadeAWS{
    @JsonProperty("ibge_id")
    public String ibgeCod;

}
