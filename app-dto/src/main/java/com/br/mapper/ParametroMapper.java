package com.br.mapper;

import com.br.dto.ParametroDTO;
import com.br.entity.Parametro;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParametroMapper {
    ParametroMapper INSTANCE = Mappers.getMapper(ParametroMapper.class);
    Parametro toEntity(ParametroDTO parametroDto);

    ParametroDTO toDto(Parametro parametro);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Parametro partialUpdate(ParametroDTO parametroDto, @MappingTarget Parametro parametro);
    List<ParametroDTO> toDtoList(List<Parametro> parametroList);
}