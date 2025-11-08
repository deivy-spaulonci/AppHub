package com.br.mapper;

import com.br.dto.response.CidadeResponseDTO;
import com.br.entity.Cidade;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CidadeMapper {
    CidadeMapper INSTANCE = Mappers.getMapper(CidadeMapper.class);
    Cidade toEntity(CidadeResponseDTO cidadeResponseDto);

    CidadeResponseDTO toDto(Cidade cidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cidade partialUpdate(CidadeResponseDTO cidadeResponseDto, @MappingTarget Cidade cidade);

    List<CidadeResponseDTO> toDtoList(List<Cidade> cidades);
}
