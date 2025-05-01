package com.br.mapper;

import com.br.dto.DespesaDto;
import com.br.entity.Despesa;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DespesaMapper {
    DespesaMapper INSTANCE = Mappers.getMapper(DespesaMapper.class);
    Despesa toEntity(DespesaDto despesaDto);

    DespesaDto toDto(Despesa despesa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Despesa partialUpdate(DespesaDto despesaDto, @MappingTarget Despesa despesa);

    List<DespesaDto> toDtoList(List<Despesa> despesaList);
}