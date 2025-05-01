package com.br.mapper;

import com.br.dto.TipoContaDto;
import com.br.dto.TipoDocumentoDto;
import com.br.entity.TipoConta;
import com.br.entity.TipoDocumento;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoDocumentoMapper {
    TipoDocumentoMapper INSTANCE = Mappers.getMapper(TipoDocumentoMapper.class);
    TipoDocumento toEntity(TipoDocumentoDto tipoDocumentoDto);

    TipoDocumentoDto toDto(TipoDocumento tipoDocumento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoDocumento partialUpdate(TipoDocumentoDto tipoDocumentoDto, @MappingTarget TipoDocumento tipoDocumento);
    List<TipoDocumentoDto> toDtoList(List<TipoDocumento> tipoDocumentoList);
}