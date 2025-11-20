package com.br.mapper;

import com.br.dto.request.create.DespesaCreateRequestDTO;
import com.br.dto.request.update.DespesaUpdateRequestDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.entity.Despesa;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DespesaMapper {
    DespesaMapper INSTANCE = Mappers.getMapper(DespesaMapper.class);

    Despesa toEntity(DespesaCreateRequestDTO despesaCreateRequestDTO);

    Despesa toEntity(DespesaUpdateRequestDTO despesaUpdateRequestDTO);

    DespesaResponseDTO toDto(Despesa despesa);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Despesa partialUpdate(DespesaResponseDTO despesaResponseDto, @MappingTarget Despesa despesa);

    List<DespesaResponseDTO> toDtoList(List<Despesa> despesaList);
}