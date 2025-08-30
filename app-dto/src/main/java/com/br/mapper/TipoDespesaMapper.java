package com.br.mapper;

import com.br.dto.TipoDespesaDTO;
import com.br.entity.TipoDespesa;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoDespesaMapper {
    TipoDespesaMapper INSTANCE = Mappers.getMapper(TipoDespesaMapper.class);
    TipoDespesa toEntity(TipoDespesaDTO tipoDespesaDto);

    TipoDespesaDTO toDto(TipoDespesa tipoDespesa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoDespesa partialUpdate(TipoDespesaDTO tipoDespesaDto, @MappingTarget TipoDespesa tipoDespesa);
    List<TipoDespesaDTO> toDtoList(List<TipoDespesa> tipoDespesaList);
}
