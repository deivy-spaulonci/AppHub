package com.br.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fatura implements Serializable {
    private static final String SEQNAME = "fatura_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Column(length = 10, nullable = false, columnDefinition = "0")
    private Integer parcela;

    @Column(length = 10, nullable = false, name = "TOTAL_PARCELA", columnDefinition = "0")
    private Integer totalParcela;

    @Column(nullable = false, columnDefinition = "DATE", name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "ID_FORNECEDOR", nullable = true)
    private Fornecedor fornecedor;
}
