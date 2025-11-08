package com.br.mapper;

import com.br.dto.request.TipoContaRequestDTO;
import com.br.dto.response.TipoContaResponseDTO;
import com.br.entity.TipoConta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true)
)
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper(TipoContaMapper.class);
    @Mapping(source = "nome", target = "nome")
    TipoConta toEntity(TipoContaRequestDTO tipoContaRequestDTO);

    TipoContaResponseDTO toDto(TipoConta tipoConta);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoConta partialUpdate(TipoContaResponseDTO tipoContaResponseDto, @MappingTarget TipoConta tipoConta);
    List<TipoContaResponseDTO> toDtoList(List<TipoConta> tipoContaList);
}