package com.br.mapper;

import com.br.dto.DocumentoDTO;
import com.br.entity.Documento;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DocumentoMapper {
    DocumentoMapper INSTANCE = Mappers.getMapper(DocumentoMapper.class);
    Documento toEntity(DocumentoDTO documentoDto);

    DocumentoDTO toDto(Documento documento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Documento partialUpdate(DocumentoDTO documentoDto, @MappingTarget Documento documento);

    List<DocumentoDTO> toDtoList(List<Documento> documentoList);
}
