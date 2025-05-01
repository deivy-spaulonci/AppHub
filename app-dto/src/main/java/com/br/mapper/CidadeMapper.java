package com.br.mapper;

import com.br.dto.CidadeDto;
import com.br.entity.Cidade;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CidadeMapper {
    CidadeMapper INSTANCE = Mappers.getMapper(CidadeMapper.class);
    Cidade toEntity(CidadeDto cidadeDto);

    CidadeDto toDto(Cidade cidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Cidade partialUpdate(CidadeDto cidadeDto, @MappingTarget Cidade cidade);

    List<CidadeDto> toDtoList(List<Cidade> cidades);
}
