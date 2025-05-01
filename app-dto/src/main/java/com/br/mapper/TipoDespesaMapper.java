package com.br.mapper;

import com.br.dto.TipoDespesaDto;
import com.br.entity.TipoDespesa;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoDespesaMapper {
    TipoDespesaMapper INSTANCE = Mappers.getMapper(TipoDespesaMapper.class);
    TipoDespesa toEntity(TipoDespesaDto tipoDespesaDto);

    TipoDespesaDto toDto(TipoDespesa tipoDespesa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoDespesa partialUpdate(TipoDespesaDto tipoDespesaDto, @MappingTarget TipoDespesa tipoDespesa);
    List<TipoDespesaDto> toDtoList(List<TipoDespesa> tipoDespesaList);
}
