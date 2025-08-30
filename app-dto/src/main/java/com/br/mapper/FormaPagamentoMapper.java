package com.br.mapper;

import com.br.dto.FormaPagamentoDTO;
import com.br.entity.FormaPagamento;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormaPagamentoMapper {
    FormaPagamentoMapper INSTANCE = Mappers.getMapper(FormaPagamentoMapper.class);
    FormaPagamento toEntity(FormaPagamentoDTO formaPagamentoDto);

    FormaPagamentoDTO toDto(FormaPagamento formaPagamento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FormaPagamento partialUpdate(FormaPagamentoDTO formaPagamentoDto, @MappingTarget FormaPagamento formaPagamento);
    List<FormaPagamentoDTO> toDtoList(List<FormaPagamento> formaPagamentoList);

}
