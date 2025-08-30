package com.br.mapper;

import com.br.dto.ContaDTO;
import com.br.entity.Conta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContaMapper {
    ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);
    Conta toEntity(ContaDTO contaDto);

    ContaDTO toDto(Conta conta);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Conta partialUpdate(ContaDTO contaDto, @MappingTarget Conta conta);

    List<ContaDTO> toDtoList(List<Conta> contaList);
}