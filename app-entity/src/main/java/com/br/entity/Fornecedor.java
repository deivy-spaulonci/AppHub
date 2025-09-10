package com.br.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Entity
public class Fornecedor implements Serializable {
    private static final String SEQNAME = "fornecedor_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Column(length = 255, nullable = false)
    private String nome;

    @Column(length = 255, nullable = false, name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Pattern(regexp = "[0-9]*")
    @Size(max = 14)
    @Column(length = 60, nullable = true)
    private String cnpj;

    @Column(length = 60, nullable = true)
    private String cpf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IBGE_COD", referencedColumnName = "IBGE_COD"/*, insertable = false, updatable = false*/)
    private Cidade cidade;

    @Override
    public String toString() {
        return getNome();
    }
}
