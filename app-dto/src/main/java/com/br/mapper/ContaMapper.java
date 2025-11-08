package com.br.mapper;

import com.br.dto.request.ContaRequestDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.entity.Conta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContaMapper {
    ContaMapper INSTANCE = Mappers.getMapper(ContaMapper.class);
    Conta toEntity(ContaRequestDTO contaRequestDTO);

    ContaResponseDTO toDto(Conta conta);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Conta partialUpdate(ContaResponseDTO contaResponseDto, @MappingTarget Conta conta);

    List<ContaResponseDTO> toDtoList(List<Conta> contaList);
}