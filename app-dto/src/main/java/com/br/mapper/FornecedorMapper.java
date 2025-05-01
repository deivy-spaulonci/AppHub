package com.br.mapper;

import com.br.dto.FornecedorDto;
import com.br.entity.Fornecedor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FornecedorMapper {
    FornecedorMapper INSTANCE = Mappers.getMapper(FornecedorMapper.class);
    Fornecedor toEntity(FornecedorDto fornecedorDto);

    FornecedorDto toDto(Fornecedor fornecedor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fornecedor partialUpdate(FornecedorDto fornecedorDto, @MappingTarget Fornecedor fornecedor);
    List<FornecedorDto> toDtoList(List<Fornecedor> fornecedorList);
}