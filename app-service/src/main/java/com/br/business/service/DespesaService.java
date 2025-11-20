package com.br.business.service;

import com.br.dto.request.create.DespesaCreateRequestDTO;
import com.br.dto.request.update.DespesaUpdateRequestDTO;
import com.br.dto.response.DespesaByTipoResponseDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.entity.Despesa;
import com.br.filter.DespesaFilter;
import com.br.mapper.DespesaMapper;
import com.br.mapper.FormaPagamentoMapper;
import com.br.mapper.FornecedorMapper;
import com.br.mapper.TipoDespesaMapper;
import com.br.repository.DespesaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Service
public class DespesaService {
    private final TipoDespesaMapper tipoDespesaMapper;
    @PersistenceContext
    private EntityManager em;

    private DespesaRepository despesaRepository;
    private FornecedorService fornecedorService;
    public static final DespesaMapper despesaMapper = DespesaMapper.INSTANCE;
    private FornecedorMapper fornecedorMapper;
    private FormaPagamentoMapper formaPagamentoMapper;

    @Autowired
    public DespesaService(DespesaRepository despesaRepository,
                          FornecedorService fornecedorService,
                          FornecedorMapper fornecedorMapper,
                          FormaPagamentoMapper formaPagamentoMapper,
                          TipoDespesaMapper tipoDespesaMapper) {
        this.despesaRepository = despesaRepository;
        this.fornecedorMapper = fornecedorMapper;
        this.fornecedorService = fornecedorService;
        this.tipoDespesaMapper = tipoDespesaMapper;
        formaPagamentoMapper = formaPagamentoMapper;
    }

    public List<DespesaResponseDTO> findDespesas() {
        return despesaMapper.toDtoList(despesaRepository.findAll());
    }

    public DespesaResponseDTO findById(BigInteger id) {
        Despesa despesa = despesaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Despesa não encontrado com o ID: " + id));
        return despesaMapper.toDto(despesa);
    }

    public List<DespesaResponseDTO> listDespesasSorted(DespesaFilter despesaFilter, Sort sort) {
        return despesaMapper.toDtoList(despesaRepository.listDespesaSorted(sort, despesaFilter));
    }

    public Page<DespesaResponseDTO> listDespesasPaged(DespesaFilter despesaFilter, Pageable pageable) {
        Page<Despesa> despesaPage = despesaRepository.listDespesaPaged(pageable, despesaFilter);
        return despesaPage.map(despesaMapper::toDto);
    }

    @Transactional
    public DespesaResponseDTO update(DespesaUpdateRequestDTO despesaUpdateRequestDTO) {
        Despesa despesa = despesaMapper.toEntity(despesaUpdateRequestDTO);
        return despesaMapper.toDto(despesaRepository.save(despesa));
    }

    @Transactional
    public DespesaResponseDTO save(DespesaCreateRequestDTO despesaCreateRequestDTO) {
        Despesa despesa = despesaMapper.toEntity(despesaCreateRequestDTO);
        despesa.setLancamento(LocalDateTime.now());
        return despesaMapper.toDto(despesaRepository.save(despesa));
    }

    @Transactional
    public void deleteById(BigInteger idDespesa) {
        if(Objects.nonNull(findById(idDespesa)))
            despesaRepository.deleteById(idDespesa);
    }

    public List getDespesaByTipo() {

        List<DespesaByTipoResponseDTO> lista = new ArrayList<>();

        for(Object item : despesaRepository.getDespesaByTipo(em)){
            DespesaByTipoResponseDTO despesaByTipoResponseDto = new DespesaByTipoResponseDTO();
            var subitem = (Object[]) item;
            despesaByTipoResponseDto.setNomeTipoDespesa(subitem[0].toString());
            despesaByTipoResponseDto.setSubTotal(new BigDecimal(subitem[1].toString()));
            lista.add(despesaByTipoResponseDto);
        }
        return lista;
    }

    public BigDecimal getSumDespesa(DespesaFilter despesaFilter) {
        return despesaRepository.getSumTotalDespesa(despesaFilter, em);
    }

    public BigInteger getCountDespesa() {
        return BigInteger.valueOf(despesaRepository.count());
    }

//    public List<String> saveLote(LoteDespesaRequestDTO loteDespesaRequestDTO) {
//        List<String> listStatus = new ArrayList<>();
//        List<String> listErro = new ArrayList<>();
//        List<Despesa> listDespesa = new ArrayList<>();
//
//        loteDespesaRequestDTO.getDespesaRequestDTOs().forEach(desp -> {
//            log.info("VALIDANDO CNPJ..." + desp.getFornecedor().getCnpj());
//            if(!fornecedorService.isCnpjValido(desp.getFornecedor().getCnpj())){
//                log.error("CNPJ INVÁLIDO!..." + desp.getFornecedor().getCnpj());
//                listErro.add(
//                        String.format("erro item: %s (cnpj inváslido %s)",
//                                loteDespesaRequestDTO.getDespesaRequestDTOs().indexOf(desp),
//                                desp.getFornecedor().getCnpj())
//                );
//            }
//
//            FornecedorResponseDTO f = null;
//
//            log.info("BUSCANDO FORNECEDOR NO BANCO...");
//            f = fornecedorService.findByCnpj(desp.getFornecedor().getCnpj());
//            if (Objects.isNull(f)) {
//                log.warn("FORNECEDOR DO BANCO NAO ENCONTRADO!");
//                log.info("BUSCANDO FORNECEDOR NA WEB...");
//                f = fornecedorService.getFornecedorFromWeb(desp.getFornecedor().getCnpj());
//
//                if (Objects.isNull(f)) {
//                    log.warn("FORNECEDOR NAO ECONTRADO NA WEB TAMBÉM!");
//                    listErro.add(
//                            String.format("erro item: %s (cnpj não econtrado %s)",
//                                    loteDespesaRequestDTO.getDespesaRequestDTOs().indexOf(desp),
//                                    desp.getFornecedor().getCnpj())
//                    );
//                }else{
//                    log.info("SALVANDO NOVO FORNECEDOR ECONTRADO NA WEB!..");
//                    f = fornecedorService.save(f);
//                }
//            }
//
//            if (Objects.isNull(f) || f.getCnpj().isEmpty()) {
//                log.error("FORNECEDOR NAO ECONTRADDO REJEITANDO INCLUSAO.. ");
//                String.format("erro item: %s (fornecedor/cnpj não econtrado %s)",
//                        loteDespesaRequestDTO.getDespesaRequestDTOs().indexOf(desp),
//                        desp.getFornecedor().getCnpj());
//            }else{
//                Despesa despesa = Despesa.builder()
//                        .tipoDespesa(tipoDespesaMapper.toEntity(desp.getTipoDespesa()))
//                        .fornecedor(fornecedorMapper.toEntity(f))
//                        .valor(desp.getValor())
//                        .dataPagamento(desp.getDataPagamento())
//                        .lancamento(LocalDateTime.now())
//                        .formaPagamento(formaPagamentoMapper.toEntity(desp.getFormaPagamento()))
//                        .obs(desp.getObs())
//                        .build();
//                listDespesa.add(despesa);
//            }
//        });
//
//        if(listErro.isEmpty()){
//           List<Despesa> listSaved = despesaRepository.saveAll(listDespesa);
//           listSaved.forEach(despesa ->  listStatus.add(String.format("item salvo id: %s", despesa.getId())));
//        }else{
//            listStatus.add(listErro.get(0)+1);
//        }
//        return listStatus;
//    }

    public List gastosDespesaAnual(int ano){
        return despesaRepository.findGastoPorAno(ano);
    }

// AJUSTAR DEPOIS PORUQE O TIPO DE DESPESA E A FORMA DE PAGAMENTO NAO SAO MAIS ENUNS
//    public String importFromResource() {
//        CSVFormat csvFormat = CSVFormat.DEFAULT.builder().build();
//        try {
//            log.info("IMPORTANDO ARQUIVO CSS");
//            File file = new File(this.getClass().getClassLoader().getResource("test.csv").toURI());
//            Reader in = new FileReader(file);
//            Iterable<CSVRecord> records = csvFormat.parse(in);
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
//            LoteDespesaDto loteDespesaDto = new LoteDespesaDto();
//            loteDespesaDto.setDespesaDtos(new ArrayList<>());
//            for (CSVRecord record : records) {
//                FornecedorDto fornecedorDto = FornecedorDto.builder().build();
//                fornecedorDto.setCnpj(record.get(0));
//                DespesaDto despesaDto = DespesaDto.builder()
//                        .fornecedor(fornecedorDto)
//                        .dataPagamento(LocalDate.parse(record.get(1), formatter))
//                        .valor(new BigDecimal(record.get(2)))
//                        .tipoDespesa(TipoDespesa.forValues(record.get(3)))
//                        .formaPagamento(FormaPagamento.valueOf(record.get(4)))
//                        .lancamento(LocalDate.now())
//                        .obs(record.get(5))
//                        .build();
//                loteDespesaDto.getDespesaDtos().add(despesaDto);
//            }
//            StringBuffer sb = new StringBuffer();
//            saveLote(loteDespesaDto).forEach(sb::append);
//            return sb.toString();
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
}
