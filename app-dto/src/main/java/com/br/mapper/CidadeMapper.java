package com.br.mapper;

import com.br.dto.CidadeDTO;
import com.br.entity.Cidade;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CidadeMapper {
    CidadeMapper INSTANCE = Mappers.getMapper(CidadeMapper.class);
    Cidade toEntity(CidadeDTO cidadeDto);

    CidadeDTO toDto(Cidade cidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cidade partialUpdate(CidadeDTO cidadeDto, @MappingTarget Cidade cidade);

    List<CidadeDTO> toDtoList(List<Cidade> cidades);
}
