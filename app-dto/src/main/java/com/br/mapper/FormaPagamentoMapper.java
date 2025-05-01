package com.br.mapper;

import com.br.dto.FormaPagamentoDto;
import com.br.dto.TipoDespesaDto;
import com.br.entity.FormaPagamento;
import com.br.entity.TipoDespesa;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormaPagamentoMapper {
    FormaPagamentoMapper INSTANCE = Mappers.getMapper(FormaPagamentoMapper.class);
    FormaPagamento toEntity(FormaPagamentoDto formaPagamentoDto);

    FormaPagamentoDto toDto(FormaPagamento formaPagamento);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FormaPagamento partialUpdate(FormaPagamentoDto formaPagamentoDto, @MappingTarget FormaPagamento formaPagamento);
    List<FormaPagamentoDto> toDtoList(List<FormaPagamento> formaPagamentoList);

}
