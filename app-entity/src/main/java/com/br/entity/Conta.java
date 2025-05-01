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
import java.util.List;

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

    @Column(precision = 10, scale = 2, nullable = true)
    private BigDecimal multa;

    @Column
    private String titulo;

    @Column
    private String comprovante;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(name = "CONTA_FATURA",
            joinColumns = @JoinColumn(name = "ID_CONTA"),
            inverseJoinColumns = @JoinColumn(name = "ID_FATURA"))
    private List<Fatura> faturas;

    @PrePersist
    public void prePersist() {
        this.setLancamento(LocalDateTime.now());
    }

    @Transient
    public int getIntStatus() {
        if(getVencimento()!=null && getDataPagamento()==null){
            if(getVencimento().isAfter(LocalDate.now()))
                return 1;
            if(getVencimento().isEqual(LocalDate.now()))
                return 0;
            if(getVencimento().isBefore(LocalDate.now()))
                return -1;
        }
        return 2;
    }

    @Transient
    public String getStatus() {
        return switch (getIntStatus()) {
            case 1 -> ContaStatus.ABERTO.getNome();
            case 0 -> ContaStatus.HOJE.getNome();
            case -1 -> ContaStatus.ATRASADO.getNome();
            case 2 -> ContaStatus.PAGO.getNome();
            default -> ContaStatus.PAGO.getNome();
        };
    }

}
