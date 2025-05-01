package com.br.mapper;

import com.br.dto.DocumentoDto;
import com.br.entity.Documento;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentoMapper {
    DocumentoMapper INSTANCE = Mappers.getMapper(DocumentoMapper.class);
    Documento toEntity(DocumentoDto documentoDto);

    DocumentoDto toDto(Documento documento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Documento partialUpdate(DocumentoDto documentoDto, @MappingTarget Documento documento);

    List<DocumentoDto> toDtoList(List<Documento> documentoList);
}
