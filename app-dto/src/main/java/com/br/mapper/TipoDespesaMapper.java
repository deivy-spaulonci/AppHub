package com.br.mapper;

import com.br.dto.request.TipoDespesaRequestDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.entity.TipoDespesa;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoDespesaMapper {
    TipoDespesaMapper INSTANCE = Mappers.getMapper(TipoDespesaMapper.class);
    TipoDespesa toEntity(TipoDespesaRequestDTO tipoDespesaRequestDTO);

    TipoDespesaResponseDTO toDto(TipoDespesa tipoDespesa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoDespesa partialUpdate(TipoDespesaResponseDTO tipoDespesaResponseDto, @MappingTarget TipoDespesa tipoDespesa);
    List<TipoDespesaResponseDTO> toDtoList(List<TipoDespesa> tipoDespesaList);
}
