package com.br.mapper;

import com.br.dto.ParametroDto;
import com.br.dto.TipoContaDto;
import com.br.entity.Parametro;
import com.br.entity.TipoConta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoContaMapper {
    TipoContaMapper INSTANCE = Mappers.getMapper(TipoContaMapper.class);
    TipoConta toEntity(TipoContaDto tipoContaDto);

    TipoContaDto toDto(TipoConta tipoConta);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoConta partialUpdate(TipoContaDto tipoContaDto, @MappingTarget TipoConta tipoConta);
    List<TipoContaDto> toDtoList(List<TipoConta> tipoContaList);
}