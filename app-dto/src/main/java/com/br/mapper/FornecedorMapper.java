package com.br.mapper;

import com.br.dto.FornecedorDTO;
import com.br.entity.Fornecedor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FornecedorMapper {
    FornecedorMapper INSTANCE = Mappers.getMapper(FornecedorMapper.class);
    Fornecedor toEntity(FornecedorDTO fornecedorDto);

    FornecedorDTO toDto(Fornecedor fornecedor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fornecedor partialUpdate(FornecedorDTO fornecedorDto, @MappingTarget Fornecedor fornecedor);
    List<FornecedorDTO> toDtoList(List<Fornecedor> fornecedorList);
}