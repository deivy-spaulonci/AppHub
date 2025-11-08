package com.br.mapper;

import com.br.dto.request.FornecedorRequestDTO;
import com.br.dto.response.FornecedorResponseDTO;
import com.br.entity.Fornecedor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FornecedorMapper {
    FornecedorMapper INSTANCE = Mappers.getMapper(FornecedorMapper.class);
    Fornecedor toEntity(FornecedorRequestDTO fornecedorRequestDTO);

    FornecedorResponseDTO toDto(Fornecedor fornecedor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fornecedor partialUpdate(FornecedorResponseDTO fornecedorResponseDTO, @MappingTarget Fornecedor fornecedor);
    List<FornecedorResponseDTO> toDtoList(List<Fornecedor> fornecedorList);
}