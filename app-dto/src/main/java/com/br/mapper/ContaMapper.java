package com.br.mapper;

import com.br.dto.ContaDto;
import com.br.entity.Conta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContaMapper {
    ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);
    Conta toEntity(ContaDto contaDto);

    ContaDto toDto(Conta conta);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Conta partialUpdate(ContaDto contaDto, @MappingTarget Conta conta);

    List<ContaDto> toDtoList(List<Conta> contaList);
}