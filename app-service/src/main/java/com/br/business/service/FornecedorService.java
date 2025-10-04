package com.br.business.service;


import com.br.dto.FornecedorDTO;
import com.br.entity.Cidade;
import com.br.entity.Fornecedor;
import com.br.dto.FornecedorCloudDTO;
import com.br.mapper.FornecedorMapper;
import com.br.repository.CidadeRepository;
import com.br.repository.FornecedorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;


@Service
public class FornecedorService {

    @PersistenceContext
    private EntityManager em;

    private String RECEITA_AWS = "https://publica.cnpj.ws/cnpj/";
    private FornecedorRepository fornecedorRepository;
    private CidadeRepository cidadeRepository;
    private static final FornecedorMapper fornecedorMapper = FornecedorMapper.INSTANCE;

    @Autowired
    public FornecedorService(FornecedorRepository fornecedorRepository,
                             CidadeRepository cidadeRepository) {
        this.fornecedorRepository = fornecedorRepository;
        this.cidadeRepository = cidadeRepository;
    }

    public FornecedorDTO findByCnpj(String cnpj) {
        Fornecedor fonecedor = fornecedorRepository.findByCnpj(cnpj).orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o CNPJ: " + cnpj));
        return fornecedorMapper.toDto(fonecedor);
    }

    public FornecedorDTO findById(BigInteger id) {
        Fornecedor fonecedor = fornecedorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o id: " + id));
        return fornecedorMapper.toDto(fonecedor);
    }

    public List<FornecedorDTO> findFornecedores() {
        return fornecedorMapper.toDtoList(fornecedorRepository.findAll());
    }

    public List<FornecedorDTO> listFornecedoresSorted(String busca, Sort sort) {
        return fornecedorMapper.toDtoList(fornecedorRepository.listFornecedorSorted(sort, busca));
    }

    public List<FornecedorDTO> listFornecedores(String busca) {
        return fornecedorMapper.toDtoList(fornecedorRepository.listFornecedor(busca));
    }

    public Page<FornecedorDTO> listFornecedoresPaged(String busca, Pageable pageable) {
        Page<Fornecedor> fornecedorPage = fornecedorRepository.listFornecedorPaged(pageable, busca);
        return fornecedorPage.map(fornecedorMapper::toDto);
    }

    @Transactional
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        return fornecedorMapper.toDto(fornecedorRepository.save(fornecedor));
    }

    @Transactional
    public void deleteById(BigInteger idFornecedor) {
        if(Objects.isNull(findById(idFornecedor)))
            fornecedorRepository.deleteById(idFornecedor);
    }

    public FornecedorDTO getFornecedorFromWeb(String cnpj){
        String json = getFornecedorApiWeb(cnpj);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            FornecedorCloudDTO fornecedorAWS = objectMapper.readValue(json, FornecedorCloudDTO.class);
            Cidade cidade = cidadeRepository.findCidadeByIbgeCod(fornecedorAWS.getIbgeCod());

            Fornecedor fornecedor = Fornecedor.builder()
                    .cnpj(fornecedorAWS.getCnpj().replaceAll("[^0-9]",""))
                    .razaoSocial(fornecedorAWS.getNome() == null ? fornecedorAWS.getFantasia() : fornecedorAWS.getNome())
                    .nome(fornecedorAWS.getFantasia()==null ? fornecedorAWS.getNome() : fornecedorAWS.getFantasia())
                    .cpf("")
                    //.ibgeCod(fornecedorAWS.getIbgeCod())
                    .cidade(cidade)
                    .build();

            return fornecedorMapper.toDto(fornecedor);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFornecedorApiWeb(String cnpj){
        try{
            String URL_API = RECEITA_AWS+cnpj;
            HttpURLConnection con = null;
            URL url = null;
            url = new URL(URL_API);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            return getJson(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String getJson(URL url) {
        if (url == null)
            throw new RuntimeException("URL é null");

        String html = null;
        StringBuilder sB = new StringBuilder();
        try (BufferedReader bR = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while ((html = bR.readLine()) != null)
                sB.append(html);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sB.toString();
    }

//    public List<Cidade> findAllCidadesByUf(String uf) {
//        return cidadeRepository.findCidadeByUf(uf);
//    }


    public boolean isCnpjValido(String cnpj) {
        if (!cnpj.substring(0, 1).equals("")) {
            try {
                cnpj = cnpj.replace('.', ' ');//onde há ponto coloca espaço
                cnpj = cnpj.replace('/', ' ');//onde há barra coloca espaço
                cnpj = cnpj.replace('-', ' ');//onde há traço coloca espaço
                cnpj = cnpj.replaceAll(" ", "");//retira espaço
                int soma = 0, dig;
                String cnpj_calc = cnpj.substring(0, 12);

                if (cnpj.length() != 14) {
                    return false;
                }
                char[] chr_cnpj = cnpj.toCharArray();
                /* Primeira parte */
                for (int i = 0; i < 4; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                        soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                /* Segunda parte */
                soma = 0;
                for (int i = 0; i < 5; i++) {
                    if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                        soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                        soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                dig = 11 - (soma % 11);
                cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                        dig);
                return cnpj.equals(cnpj_calc);
            }
            catch (Exception e) {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
