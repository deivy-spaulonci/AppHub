package com.br.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "DESPESA")
public class Despesa extends Pagamento implements Serializable {
    private static final String SEQNAME = "despesa_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_TIPO_DESPESA")
    private TipoDespesa tipoDespesa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_FORNECEDOR")
    private Fornecedor fornecedor;
}
