package com.br.entity;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Estado  implements Serializable {
    AC(12),
    AL(27),
    AM(13),
    AP(16),
    BA(29),
    CE(23),
    DF(53),
    ES(32),
    GO(52),
    MA(21),
    MG(31),
    MS(50),
    MT(51),
    PA(15),
    PB(25),
    PE(26),
    PI(22),
    PR(41),
    RJ(33),
    RN(24),
    RO(11),
    RR(14),
    RS(43),
    SC(42),
    SE(28),
    SP(35),
    TO(17);

    private final Integer nome;
    private final String value;

    Estado(Integer nome) {
        this.nome = nome;
        this.value = this.toString();
    }
    public static Estado forValues(String value) {
        for (Estado estateEnum : Estado.values()) {
            if (estateEnum.getValue().equals(value)) {
                return estateEnum;
            }
        }
        return null;
    }
}
