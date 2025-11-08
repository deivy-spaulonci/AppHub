package com.br.dto.request;

import com.br.dto.response.DespesaResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoteDespesaRequestDTO implements Serializable  {
    private static final long serialVersionUID = 1L;
    private ArrayList<DespesaRequestDTO> despesaRequestDTOs;
}
