package com.br.mapper;

import com.br.dto.TipoContaDTO;
import com.br.entity.TipoConta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper(TipoContaMapper.class);
    TipoConta toEntity(TipoContaDTO tipoContaDto);

    TipoContaDTO toDto(TipoConta tipoConta);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoConta partialUpdate(TipoContaDTO tipoContaDto, @MappingTarget TipoConta tipoConta);
    List<TipoContaDTO> toDtoList(List<TipoConta> tipoContaList);
}