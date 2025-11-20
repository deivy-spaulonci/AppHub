package com.br.mapper;

import com.br.dto.request.create.FormaPagamentoCreateRequestDTO;
import com.br.dto.request.update.FormaPagamentoUpdateRequestDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.entity.FormaPagamento;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormaPagamentoMapper {
    FormaPagamentoMapper INSTANCE = Mappers.getMapper(FormaPagamentoMapper.class);

    FormaPagamento toEntity(FormaPagamentoCreateRequestDTO formaPagamentoCreateRequestDTO);

    FormaPagamento toEntity(FormaPagamentoUpdateRequestDTO formaPagamentoUpdateRequestDTO);

    FormaPagamentoResponseDTO toDto(FormaPagamento formaPagamento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FormaPagamento partialUpdate(FormaPagamentoResponseDTO formaPagamentoResponseDto, @MappingTarget FormaPagamento formaPagamento);
    List<FormaPagamentoResponseDTO> toDtoList(List<FormaPagamento> formaPagamentoList);

}
