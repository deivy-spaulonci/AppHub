package com.br.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigInteger;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TIPO_DESPESA")
public class TipoDespesa extends Tipo implements Serializable {
    private static final String SEQNAME = "tipo_despesa_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Override
    public String toString() {
        return getNome(); // O JComboBox exibirá o nome
    }
}
