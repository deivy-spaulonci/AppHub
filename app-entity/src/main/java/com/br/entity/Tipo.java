package com.br.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class Tipo implements Serializable {
    @Column(length = 255, nullable = false)
    private String nome;

}
