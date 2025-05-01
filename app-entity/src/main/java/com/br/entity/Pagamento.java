package com.br.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SuperBuilder
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Pagamento  implements Serializable {

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_FORMA_PAGAMENTO")
    private FormaPagamento formaPagamento;

    @Column(nullable = true, columnDefinition = "DATE", name = "DATA_PAGAMENTO")
    private LocalDate dataPagamento;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime lancamento = LocalDateTime.now();

    @Column(length = 255, nullable = true)
    private String obs;
}
