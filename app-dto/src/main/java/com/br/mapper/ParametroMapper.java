package com.br.mapper;

import com.br.dto.FornecedorDto;
import com.br.dto.ParametroDto;
import com.br.entity.Fornecedor;
import com.br.entity.Parametro;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParametroMapper {
    ParametroMapper INSTANCE = Mappers.getMapper(ParametroMapper.class);
    Parametro toEntity(ParametroDto parametroDto);

    ParametroDto toDto(Parametro parametro);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Parametro partialUpdate(ParametroDto parametroDto, @MappingTarget Parametro parametro);
    List<ParametroDto> toDtoList(List<Parametro> parametroList);
}