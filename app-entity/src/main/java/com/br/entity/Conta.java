package com.br.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CONTA")
public class Conta extends Pagamento implements Serializable {
    private static final String SEQNAME = "conta_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Column(length = 60, nullable = false, name = "CODIGO_BARRA")
    private String codigoBarra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_TIPO_CONTA")
    private TipoConta tipoConta;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate emissao;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate vencimento;

    @Column(length = 10, nullable = false)
    private Integer parcela = 0;

    @Column(length = 10, nullable = false, columnDefinition = "0", name = "TOTAL_PARCELA")
    private Integer totalParcela = 0;

    @Column(precision = 10, scale = 2, nullable = false, columnDefinition = "0")
    private BigDecimal multa = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2, nullable = false, columnDefinition = "0")
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column
    private String titulo;

    @Column
    private String comprovante;

    @PrePersist
    public void prePersist() {
        this.setLancamento(LocalDateTime.now());
    }


}
