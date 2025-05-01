package com.br.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContaStatus implements Serializable {
    ABERTO("Em Aberto"),
    HOJE("Vencimento Hoje"),
    ATRASADO("Atrasado"),
    PAGO("Pago");

    private final String nome;
    private final String value;

    ContaStatus(String nome) {
        this.nome = nome;
        this.value = this.toString();
    }
    public static ContaStatus forValues(String value) {
        for (ContaStatus contaStatus : ContaStatus.values()) {
            if (contaStatus.value.equals(value)) {
                return contaStatus;
            }
        }
        return null;
    }
}
