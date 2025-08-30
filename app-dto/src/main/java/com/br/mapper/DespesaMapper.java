package com.br.mapper;

import com.br.dto.DespesaDTO;
import com.br.entity.Despesa;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DespesaMapper {
    DespesaMapper INSTANCE = Mappers.getMapper(DespesaMapper.class);
    Despesa toEntity(DespesaDTO despesaDto);

    DespesaDTO toDto(Despesa despesa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Despesa partialUpdate(DespesaDTO despesaDto, @MappingTarget Despesa despesa);

    List<DespesaDTO> toDtoList(List<Despesa> despesaList);
}