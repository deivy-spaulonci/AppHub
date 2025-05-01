package com.br.entity;

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
@Entity(name = "DOCUMENTO")
public class Documento implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQNAME = "doc_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Column(length = 255, nullable = false)
    private String arquivo;

    @Column(length = 255, nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_TIPO_DOCUMENTO")
    private TipoDocumento tipoDocumento;
}
