package com.br.mapper;

import com.br.dto.TipoDocumentoDTO;
import com.br.entity.TipoDocumento;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TipoDocumentoMapper {
    TipoDocumentoMapper INSTANCE = Mappers.getMapper(TipoDocumentoMapper.class);
    TipoDocumento toEntity(TipoDocumentoDTO tipoDocumentoDto);

    TipoDocumentoDTO toDto(TipoDocumento tipoDocumento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TipoDocumento partialUpdate(TipoDocumentoDTO tipoDocumentoDto, @MappingTarget TipoDocumento tipoDocumento);
    List<TipoDocumentoDTO> toDtoList(List<TipoDocumento> tipoDocumentoList);
}