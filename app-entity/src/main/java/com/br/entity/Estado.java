package com.br.entity;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Estado  implements Serializable {
    RO(11),
    AC(12),
    AM(13),
    RR(14),
    PA(15),
    AP(16),
    TO(17),
    MA(21),
    PI(22),
    CE(23),
    RN(24),
    PB(25),
    PE(26),
    AL(27),
    SE(28),
    BA(29),
    MG(31),
    ES(32),
    RJ(33),
    SP(35),
    PR(41),
    SC(42),
    RS(43),
    MS(50),
    MT(51),
    GO(52),
    DF(53);


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
