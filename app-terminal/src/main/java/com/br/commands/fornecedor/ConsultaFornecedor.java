package com.br.commands.fornecedor;

import com.br.components.FornecedorComponent;
import com.br.dto.PaginacaoDTO;
import com.br.dto.request.update.FornecedorUpdateRequestDTO;
import com.br.dto.response.CidadeResponseDTO;
import com.br.dto.response.FornecedorResponseDTO;
import com.br.entity.Estado;
import com.br.util.VTerminal;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.support.SelectorItem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class ConsultaFornecedor {

    private FornecedorComponent fornecedorCmp;
    private int largura = 200;
    private VTerminal vTr;
    private String mensagem = "";
    private PaginacaoDTO pgDTO =  new PaginacaoDTO();

    public ConsultaFornecedor(FornecedorComponent fornecedorCmp){
        this.fornecedorCmp = fornecedorCmp;
        this.vTr = new VTerminal(fornecedorCmp.getDefaultComponent().getShellHelper());
    }

    public void consulta() {

        var contFornec = true;
        this.pgDTO.setSort("nome");
        Page<FornecedorResponseDTO> pg;
        String cols = "| %-6s | %-65s | %-65s | %-16s | %-20s";
        String busca = null;

        do {
            Sort.Direction direction = (pgDTO.getOrder() == 0 ? Sort.Direction.ASC : Sort.Direction.DESC);
            Pageable pageable = PageRequest.of(pgDTO.getNumberPage() - 1, pgDTO.getSize(), Sort.by(direction, pgDTO.getSort()));
            pg = fornecedorCmp.getFornecedorService().listFornecedoresPaged(busca, pageable);
            String header = String.format(cols, "id", "Nome", "Razão Social", "CPF/CNPJ", "Cidade");
            vTr.clear();
            vTr.lnDefault(largura);
            vTr.msg(header);
            vTr.lnDefault(largura);
            if (pg.hasContent()) {
                pg.getContent().forEach(fornec -> {
                    String label = String.format(cols,
                            fornec.getId(),
                            StringUtils.abbreviate(fornec.getNome(), 65),
                            StringUtils.abbreviate(fornec.getRazaoSocial(), 65),
                            (fornec.getCnpj() == null ? fornec.getCpf() : fornec.getCnpj()),
                            fornec.getCidade().getNome() + "-" + fornec.getCidade().getUf());
                    vTr.msg(label);
                });
            } else
                vTr.msg(" !!! nenhum fornecedor encontrado... !!!");
            vTr.lnDefault(largura);
            vTr.msg("%n pagina: %-20s  paginas: %-20s tamanho: %-20s  registros: %-20s  %n".formatted(pgDTO.getNumberPage(), pg.getTotalPages(), pgDTO.getSize(), pg.getTotalElements()));
            vTr.lnDefault(largura);
            vTr.msg("| %-10s:  [ %-4s ] ".formatted("Filtros","busca"));
            vTr.msg("| %-10s:  [ %-6s |  %-10s | %-5s ] ".formatted("Paginação", "pag xx","order xx","size"));
            vTr.msg("| %-10s:  [ %-6s |  %-10s | %-10s | %-8s | %-4s ] ".formatted("Comandos", "clear", "excluir xx","detalhe xx","editar xx", "exit"));
            vTr.lnDefault(largura);

            if(pgDTO.getIdSelect() != null){
                FornecedorResponseDTO optionalFornecedor;
                try {
                    optionalFornecedor = fornecedorCmp.getFornecedorService().findById(BigInteger.valueOf(pgDTO.getIdSelect()));
                    showFornecedor(optionalFornecedor);
                }catch (EntityNotFoundException e){
                    mensagem = "Id da fornecedor não encontrado";
                }
            }

            if(!mensagem.trim().isEmpty())
                vTr.msgWarn(mensagem);
            mensagem = "";

            String resposta = fornecedorCmp.getDefaultComponent().inputTextDefault(" ->", "");
            switch (resposta){
                case "size" : pgDTO.setSize(fornecedorCmp.getDefaultComponent().selectPageDefault()); break;
                case "pag" : page(pg.getTotalPages()); break;
                case "sort": sort(); break;
                case "busca": busca = fornecedorCmp.getDefaultComponent().inputTextDefault(" Valor ->", "");
                case "editar" : editar(); break;
                case "detalhe" : pgDTO.setIdSelect(fornecedorCmp.getDefaultComponent().inputInteger(" Id  ->", "")); break;
                case "exit": contFornec = false; break;
                case "clear":  busca = "";  pgDTO.setIdSelect(null);  break;
                default: pgDTO.setNumberPage(pg.getContent().size() < pgDTO.getSize() ? 1 : pgDTO.getNumberPage()+1);
            }
        }while(!!contFornec);
    }

    public void sort(){
        List<SelectorItem<String>> ltOpc = new ArrayList<>(){{
            add(SelectorItem.of("Id", "id"));
            add(SelectorItem.of("Nome", "nome"));
            add(SelectorItem.of("Razão Social", "razaoSocial"));
            add(SelectorItem.of("CNPJ", "cnpj"));
            add(SelectorItem.of("CPF", "cpf"));
            add(SelectorItem.of("Cidade", "cidade.nome"));
        }};
        this.pgDTO.setSort(fornecedorCmp.getDefaultComponent().selectDefault(ltOpc, "Selecionar Campo"));
        List<SelectorItem<String>> opcOrder = new ArrayList<>();
        opcOrder.add(SelectorItem.of("Ascesdente", "0"));
        opcOrder.add(SelectorItem.of("Descendente", "1"));
        this.pgDTO.setOrder(Integer.valueOf(fornecedorCmp.getDefaultComponent().selectDefault(opcOrder, "Selecionar Ordem Campo")));
    }

    public void page(int totalPages){
        Integer pagina = fornecedorCmp.getDefaultComponent().inputInteger("Página : ", pgDTO.getNumberPage()+"");
        if(pagina>=1 && pagina<=totalPages)
            pgDTO.setNumberPage(pagina);
        else
            mensagem = "valor digitado tem que ser entre 1 e "+totalPages;
    }

    public void showFornecedor(FornecedorResponseDTO fornecedorResponseDTO){
        vTr.msg("%n".formatted());
        vTr.lnDefault(largura);
        vTr.lnLabelValue("Nome: ", fornecedorResponseDTO.getNome());
        vTr.lnLabelValue("Razão Social: ", fornecedorResponseDTO.getRazaoSocial());
        vTr.lnLabelValue("CNPJ: ", fornecedorResponseDTO.getCnpj());
        vTr.lnLabelValue("CPF: ", fornecedorResponseDTO.getCpf());
        vTr.lnLabelValue("Cidade: ", fornecedorResponseDTO.getCidade().getNome()+" - "+fornecedorResponseDTO.getCidade().getUf());
        vTr.lnDefault(largura);
    }

    public void editar(){
        pgDTO.setIdSelect(fornecedorCmp.getDefaultComponent().inputInteger(" Id  ->", ""));
        FornecedorUpdateRequestDTO fornecedorUpdateRequestDTO = new FornecedorUpdateRequestDTO();
        FornecedorResponseDTO fornecedorResponseDTO = fornecedorCmp.getFornecedorService().findById(BigInteger.valueOf(pgDTO.getIdSelect()));

        vTr.clear();
        vTr.lnDefault(largura);
        vTr.lnLabelValue("CNPJ", fornecedorResponseDTO.getCnpj());
        if(fornecedorCmp.getDefaultComponent().confirmationInput("Alterar CNPJ", false)){
            String cnpj = fornecedorCmp.getDefaultComponent().inputTextDefault("CNPJ", "");
            fornecedorUpdateRequestDTO.setCnpj(cnpj);
            vTr.removeLn();
            vTr.lnLabelValue("CNPJ", fornecedorUpdateRequestDTO.getCnpj());
        }

        vTr.lnLabelValue("CPF", fornecedorResponseDTO.getCpf());
        if(fornecedorCmp.getDefaultComponent().confirmationInput("Alterar CPF", false)){
            String cpf = fornecedorCmp.getDefaultComponent().inputTextDefault("CPF", "");
            fornecedorUpdateRequestDTO.setCpf(cpf);
            vTr.removeLn();
            vTr.lnLabelValue("CPF", fornecedorUpdateRequestDTO.getCpf());
        }

        vTr.lnLabelValue("Nome", fornecedorResponseDTO.getNome());
        if(fornecedorCmp.getDefaultComponent().confirmationInput("Alterar Nome", false)){
            String cpf = fornecedorCmp.getDefaultComponent().inputTextDefault("Nome", "");
            fornecedorUpdateRequestDTO.setNome(cpf);
            vTr.removeLn();
            vTr.lnLabelValue("Nome", fornecedorUpdateRequestDTO.getNome());
        }

        vTr.lnLabelValue("Razão Social", fornecedorResponseDTO.getRazaoSocial());
        if(fornecedorCmp.getDefaultComponent().confirmationInput("Alterar Razão Social", false)){
            String razaoSocial = fornecedorCmp.getDefaultComponent().inputTextDefault("Razão Social", "");
            fornecedorUpdateRequestDTO.setRazaoSocial(razaoSocial);
            vTr.removeLn();
            vTr.lnLabelValue("Razão Social", fornecedorUpdateRequestDTO.getRazaoSocial());
        }

        vTr.lnLabelValue("Cidade", fornecedorResponseDTO.getCidade().getNome()+"-"+fornecedorResponseDTO.getCidade().getUf());
        if(fornecedorCmp.getDefaultComponent().confirmationInput("Alterar Cidade", false)){
            List<SelectorItem<String>> estados = new ArrayList<>();
            Arrays.stream(Estado.values()).toList().forEach(estado ->
                    estados.add(SelectorItem.of(estado.getValue(), estado.getValue()))
            );
            String estado = fornecedorCmp.getDefaultComponent().selectDefault(estados, "Selecionar Estado");

            String cidadeSearch =  fornecedorCmp.getDefaultComponent().inputTextDefault("Cidade :", "");
            List<SelectorItem<String>> cidades = new ArrayList<>();
            List<CidadeResponseDTO> cidadeResponseDTOS =  fornecedorCmp.getCidadeService().listCidadesByUfContainingNome(estado, cidadeSearch);
            cidadeResponseDTOS.forEach(cidade -> {
                cidades.add(SelectorItem.of(cidade.getNome(), cidade.getIbgeCod()));
            });
//            if(cidades.isEmpty()){
//                contCidade = fornecedorCmp.getDefaultComponent().confirmationInput("Nenhuma cidade encontrada, continuar", true);
//            }else{
//                cidadeCodIbge = fornecedorCmp.getDefaultComponent().selectDefault(cidades, "Selecionar Cidade");
//
//                String finalCidadeCodIbge = cidadeCodIbge;
//                cidadeResponseDTO = cidadeResponseDTOS.stream()
//                        .filter(cidadeResponseDTO1 -> cidadeResponseDTO1.getIbgeCod().equals(finalCidadeCodIbge))
//                        .findFirst().orElse(null);
//
//                contCidade = false;
//            }
        }

        fornecedorCmp.getDefaultComponent().confirmationInput("teste", false);
    }
}
