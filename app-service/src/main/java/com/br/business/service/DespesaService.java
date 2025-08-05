package com.br.business.service;

import com.br.dto.LoteDespesaDto;
import com.br.entity.Despesa;
import com.br.entity.Fornecedor;
import com.br.filter.DespesaFilter;
import com.br.repository.DespesaRepository;
import com.br.repository.FornecedorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
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
    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private FornecedorService fornecedorService;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    public List<Despesa> findDespesas() {
        return despesaRepository.findAll();
    }

    public Optional<Despesa> findById(BigInteger id) {
        return despesaRepository.findById(id);
    }

    public List<Despesa> listDespesasSorted(DespesaFilter despesaFilter, Sort sort) {
        return despesaRepository.listDespesaSorted(sort, despesaFilter, em);
    }

    public Page<Despesa> listDespesasPaged(DespesaFilter despesaFilter, Pageable pageable) {
        return despesaRepository.listDespesaPaged(pageable, despesaFilter, em);
    }

    public Despesa save(Despesa despesa) {
        despesa.setLancamento(LocalDateTime.now());
        return Optional.ofNullable(despesaRepository.save(despesa))
                .orElseThrow(() -> new RuntimeException("Erro ao salvar a despesa"));
    }

    public boolean deleteById(BigInteger idDespesa) {
        Optional<Despesa> despesaOptional = findById(idDespesa);
        despesaOptional.ifPresent(despesa -> despesaRepository.deleteById(idDespesa));
        return despesaRepository.findById(idDespesa).isPresent();
    }

    public List getDespesaByTipo() {
        return despesaRepository.getDespesaByTipo(em);
    }

    public BigDecimal getSumDespesa(DespesaFilter despesaFilter) {
        return despesaRepository.getSumTotalDespesa(despesaFilter, em);
    }

    public BigInteger getCountDespesa() {
        return BigInteger.valueOf(despesaRepository.count());
    }

    public List<String> saveLote(LoteDespesaDto loteDespesaDtos) {
        List<String> listStatus = new ArrayList<>();
        List<String> listErro = new ArrayList<>();
        List<Despesa> listDespesa = new ArrayList<>();

        loteDespesaDtos.getDespesaDtos().forEach(desp -> {
            log.info("VALIDANDO CNPJ..." + desp.getFornecedor().getCnpj());
            if(!fornecedorService.isCnpjValido(desp.getFornecedor().getCnpj())){
                log.error("CNPJ INVÁLIDO!..." + desp.getFornecedor().getCnpj());
                listErro.add(
                        String.format("erro item: %s (cnpj inváslido %s)",
                                loteDespesaDtos.getDespesaDtos().indexOf(desp),
                                desp.getFornecedor().getCnpj())
                );
            }

            Fornecedor f = null;

            log.info("BUSCANDO FORNECEDOR NO BANCO...");
            f = fornecedorService.findByCnpj(desp.getFornecedor().getCnpj());
            if (Objects.isNull(f)) {
                log.warn("FORNECEDOR DO BANCO NAO ENCONTRADO!");
                log.info("BUSCANDO FORNECEDOR NA WEB...");
                f = fornecedorService.getFornecedorFromWeb(desp.getFornecedor().getCnpj());

                if (Objects.isNull(f)) {
                    log.warn("FORNECEDOR NAO ECONTRADO NA WEB TAMBÉM!");
                    listErro.add(
                            String.format("erro item: %s (cnpj não econtrado %s)",
                                    loteDespesaDtos.getDespesaDtos().indexOf(desp),
                                    desp.getFornecedor().getCnpj())
                    );
                }else{
                    log.info("SALVANDO NOVO FORNECEDOR ECONTRADO NA WEB!..");
                    f = fornecedorService.save(f);
                }
            }

            if (Objects.isNull(f) || f.getCnpj().isEmpty()) {
                log.error("FORNECEDOR NAO ECONTRADDO REJEITANDO INCLUSAO.. ");
                String.format("erro item: %s (fornecedor/cnpj não econtrado %s)",
                        loteDespesaDtos.getDespesaDtos().indexOf(desp),
                        desp.getFornecedor().getCnpj());
            }else{
                Despesa despesa = Despesa.builder()
                        .tipoDespesa(desp.getTipoDespesa())
                        .fornecedor(f)
                        .valor(desp.getValor())
                        .dataPagamento(desp.getDataPagamento())
                        .lancamento(LocalDateTime.now())
                        .formaPagamento(desp.getFormaPagamento())
                        .obs(desp.getObs())
                        .build();
                listDespesa.add(despesa);
            }
        });

        if(listErro.isEmpty()){
           List<Despesa> listSaved = despesaRepository.saveAll(listDespesa);
           listSaved.forEach(despesa ->  listStatus.add(String.format("item salvo id: %s", despesa.getId())));
        }else{
            listStatus.add(listErro.get(0)+1);
        }
        return listStatus;
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
