package com.br.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CIDADE")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cidade implements Serializable {
    private static final String SEQNAME = "cidade_seq";
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @JsonProperty("ibgeCod")
    @Column(nullable = false, name = "IBGE_COD")
    private String ibgeCod;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("uf")
    private String uf;
}
