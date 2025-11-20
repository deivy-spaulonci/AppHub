package com.br.commands.despesa;

import com.br.components.DespesaComponent;
import com.br.dto.PaginacaoDTO;
import com.br.dto.ref.FormaPagamentoRefDTO;
import com.br.dto.ref.FornecedorRefDTO;
import com.br.dto.ref.TipoDespesaRefDTO;
import com.br.dto.request.update.DespesaUpdateRequestDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.FornecedorResponseDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.filter.DespesaFilter;
import com.br.util.Util;
import com.br.util.VTerminal;
import com.br.util.Validate;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.ShellComponent;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ShellComponent
public class ConsultaDespesa {

    private DespesaComponent despesaCmp;
    private int largura = 200;
    private VTerminal vTr;
    private PaginacaoDTO pgDTO =  new PaginacaoDTO();
    private String mensagem = "";
    private DespesaFilter despesaFilter;

    @Autowired
    public ConsultaDespesa(DespesaComponent despesaCmp) {
        this.despesaCmp = despesaCmp;
        this.vTr = new VTerminal(despesaCmp.getDefaultComponent().getShellHelper());
    }

    public void consulta(){
        boolean contEdtDespesa = true;
        despesaFilter = DespesaFilter.builder().build();
        Page<DespesaResponseDTO> pg;
        String cols = "|  %-5s |  %-21s | %-50s | %-10s | %12s | %-34s |  %-50s";

        do{
            Sort.Direction direction = (pgDTO.getOrder()==0 ? Sort.Direction.ASC : Sort.Direction.DESC);
            Pageable pageable = PageRequest.of(pgDTO.getNumberPage()-1, pgDTO.getSize(), Sort.by(direction, pgDTO.getSort()));
            pg = despesaCmp.getDespesaService().listDespesasPaged(despesaFilter, pageable);
            String header = String.format(cols,"Id","Tipo Despesa", "Fonrecedor", "Data", "Valor", "Forma Pagamento", "Obs");
            vTr.clear();
            vTr.lnDefault(largura);
            vTr.msg(header);
            vTr.lnDefault(largura);
            if(pg.hasContent()){
                pg.getContent().forEach(despesa -> {
                    String label = String.format(cols,
                            despesa.getId(),
                            despesa.getTipoDespesa().getNome(),
                            StringUtils.abbreviate(despesa.getFornecedor().getNome(), 50),
                            Util.toDatePtBr(despesa.getDataPagamento()),
                            Util.toCurrencyPtBr(despesa.getValor()),
                            despesa.getFormaPagamento().getNome(),
                            StringUtils.abbreviate(despesa.getObs(),40));
                    vTr.msg(label);
                });
            }else
                vTr.msgWarn("nenhum despesa encontrado");
            vTr.lnDefault(largura);
            BigDecimal total = despesaCmp.getDespesaService().getSumDespesa(despesaFilter);
            String legend = "%n pagina: %-20s  paginas: %-20s tamanho: %-20s  registros: %-20s  total: %-20s %n";
            vTr.msg(legend.formatted(pgDTO.getNumberPage(), pg.getTotalPages(), pgDTO.getSize(), pg.getTotalElements(), Util.toCurrencyPtBr(total)));

            vTr.lnDefault(largura);
            vTr.msg("| %-10s:  [ %-6s | %-10s | %-9s | %-7s ] ".formatted("Filtros","tipo", "forma","periodo","fornecedor"));
            vTr.msg("| %-10s:  [ %-6s | %-10s | %-4s ] ".formatted("Paginação", "pag xx","sort xx","size"));
            vTr.msg("| %-10s:  [ %-6s | %-10s | %-9s | %-8s | %-4s ] ".formatted("Comandos", "clear", "excluir xx", "editar xx", "detalhe xx", "exit"));
            vTr.lnDefault(largura);

            if(pgDTO.getIdSelect() != null){
                DespesaResponseDTO optionalDespesa;
                try {
                    optionalDespesa = despesaCmp.getDespesaService().findById(BigInteger.valueOf(pgDTO.getIdSelect()));
                    showDespesa(optionalDespesa);
                }catch (EntityNotFoundException e){
                    mensagem = "Id da despesa não encontrado !";
                }
            }

            if(!mensagem.trim().isEmpty())
                vTr.msgWarn(mensagem);
            mensagem = "";

            String resposta = despesaCmp.getDefaultComponent().inputTextDefault(" ->", "");

            if(resposta.equals("exit")){
                contEdtDespesa = false;
            }else{
                String[] resparr = resposta.split(" ");
                switch (resparr[0]){
                    case "size" : pgDTO.setSize(despesaCmp.getDefaultComponent().selectPageDefault()); break;
                    case "pag" : page(pg.getTotalPages()); break;
                    case "sort": sort(); break;
					case "tipo": despesaFilter.setIdTipoDespesa(despesaCmp.getTipoDespesaComponent().selectTipoDespesa().getId()); break;
					case "forma": despesaFilter.setIdFormaPagamento(despesaCmp.getFormaPagamentoComponent().sellectFormaPagamento().getId()); break;
                    case "periodo": periodo(); break;
                    case "fornecedor": despesaFilter.setIdFornecedor(despesaCmp.getFornecedorComponent().selectTableFornecedor().getId()); break;
                    case "editar" : editar(); break;
                    case "detalhe" : pgDTO.setIdSelect(despesaCmp.getDefaultComponent().inputInteger(" Id  ->", "")); break;
                    case "clear": despesaFilter = DespesaFilter.builder().build(); pgDTO.setIdSelect(null); break;
                    default: pgDTO.setNumberPage(pg.getContent().size() < pgDTO.getSize() ? 1 : pgDTO.getNumberPage()+1);
                }
            }
        }while (!!contEdtDespesa);
    }

    public void page(int totalPages){
        Integer pagina = despesaCmp.getDefaultComponent().inputInteger("Página : ", pgDTO.getNumberPage()+"");
        if(pagina>=1 && pagina<=totalPages)
            pgDTO.setNumberPage(pagina);
        else
            mensagem = "valor digitado tem que ser entre 1 e "+totalPages;
    }

    public void periodo(){
        String inicio = despesaCmp.getDefaultComponent().inputTextDefault(" Data Inicial (ddmmyyyy) ->", "");
        Optional.ofNullable(inicio).filter(Validate::isValidDate).map(Util::getData).ifPresentOrElse(despesaFilter::setDataInicial,() -> mensagem = "Data Inicial inválida!");
        String termino = despesaCmp.getDefaultComponent().inputTextDefault(" Data Final (ddmmyyyy) ->", "");
        Optional.ofNullable(termino).filter(Validate::isValidDate).map(Util::getData).ifPresentOrElse(despesaFilter::setDataFinal,() -> mensagem = "Data final inválida!");
    }

    public void sort(){
        List<SelectorItem<String>> ltOpc = new ArrayList<>(){{
            add(SelectorItem.of("Id", "id"));
            add(SelectorItem.of("Tipo Despesa", "tipoDespesa"));
            add(SelectorItem.of("Forma Pagamento", "formaPagamento"));
            add(SelectorItem.of("Data Pagamento", "dataPagamento"));
            add(SelectorItem.of("Fonrecedor", "fornecedor.nome"));
        }};
        this.pgDTO.setSort(despesaCmp.getDefaultComponent().selectDefault(ltOpc, "Selecionar Campo"));
        List<SelectorItem<String>> opcOrder = new ArrayList<>();
        opcOrder.add(SelectorItem.of("Ascesdente", "0"));
        opcOrder.add(SelectorItem.of("Descendente", "1"));
        this.pgDTO.setOrder(Integer.valueOf(despesaCmp.getDefaultComponent().selectDefault(opcOrder, "Selecionar Ordem Campo")));
    }

    public void showDespesa(DespesaResponseDTO despesaResponseDTO){
        vTr.msg("");
        vTr.lnDefault(largura);
        vTr.msg("| Detalhamento");
        vTr.msg("|");
        vTr.lnLabelValue("Tipo Despesa", despesaResponseDTO.getTipoDespesa().getNome());
        vTr.lnLabelValue("Fornecedor",
                        despesaResponseDTO.getFornecedor().getNome()+ " -  " +
                        despesaResponseDTO.getFornecedor().getCnpj()+ " -  " +
                        despesaResponseDTO.getFornecedor().getCidade().getNome()+ " -  " +
                        despesaResponseDTO.getFornecedor().getCidade().getUf());
        vTr.lnLabelValue( "Data", Util.toDatePtBr(despesaResponseDTO.getDataPagamento()));
        vTr.lnLabelValue("Valor", Util.toCurrencyPtBr(despesaResponseDTO.getValor()));
        vTr.lnLabelValue("Forma Pgto", despesaResponseDTO.getFormaPagamento().getNome());
        vTr.lnLabelValue( "Obs", despesaResponseDTO.getObs());
        vTr.lnDefault(largura);
	}

    public void editar() {
        pgDTO.setIdSelect(despesaCmp.getDefaultComponent().inputInteger(" Id  ->", ""));
        DespesaUpdateRequestDTO despesaUpdateRequestDTO = new DespesaUpdateRequestDTO();
        DespesaResponseDTO despesaResponseDTO = despesaCmp.getDespesaService().findById(BigInteger.valueOf(pgDTO.getIdSelect()));

        vTr.clear();
        vTr.lnDefault(largura);
        //TIPO DESPESA ----------------------------------------------------
        vTr.lnLabelValue("Tipo Despesa", despesaResponseDTO.getTipoDespesa().getNome());
        if(despesaCmp.getDefaultComponent().confirmationInput("Alterar Tipo de Despesa", false)){
            TipoDespesaResponseDTO tipoDespesaResponseDTO = despesaCmp.getTipoDespesaComponent().selectTipoDespesa();
            despesaUpdateRequestDTO.setTipoDespesa(new TipoDespesaRefDTO());
            despesaUpdateRequestDTO.getTipoDespesa().setId(tipoDespesaResponseDTO.getId());
            vTr.removeLn();
            vTr.lnLabelValue("Tipo Despesa", tipoDespesaResponseDTO.getNome());
        }

        //FORNECEDOR ----------------------------------------------------
        vTr.lnLabelValue("Fornecedor", despesaResponseDTO.getFornecedor().getNome());
        if(despesaCmp.getDefaultComponent().confirmationInput("Alterar Fornecedor", false)){
            vTr.removeLn();
            FornecedorResponseDTO fornecedorResponseDTO = despesaCmp.getFornecedorComponent().selectTableFornecedor();
            despesaUpdateRequestDTO.setFornecedor(new FornecedorRefDTO());
            despesaUpdateRequestDTO.getFornecedor().setId(fornecedorResponseDTO.getId());
            vTr.removeLn();
            vTr.lnLabelValue("Fornecedor", fornecedorResponseDTO.getNome());
        }

        //DATA ----------------------------------------------------
        vTr.lnLabelValue("Data Pagamento", Util.toDatePtBr(despesaResponseDTO.getDataPagamento()));
        if(despesaCmp.getDefaultComponent().confirmationInput("Alterar Data Pagamento", false)){
            String datatxt = despesaCmp.getDefaultComponent().inputData("Data Pagamento");
            despesaUpdateRequestDTO.setDataPagamento(Util.getData(datatxt));
            vTr.removeLn();
            vTr.lnLabelValue("Data Pagamento", Util.toDatePtBr(despesaUpdateRequestDTO.getDataPagamento()));
        }

        //VALOR ----------------------------------------------------
        vTr.lnLabelValue("Valor", Util.toCurrencyPtBr(despesaResponseDTO.getValor()));
        if(despesaCmp.getDefaultComponent().confirmationInput("Alterar Valor", false)){
            String valortxt = despesaCmp.getDefaultComponent().inputValor("Valor");
            despesaUpdateRequestDTO.setValor(new BigDecimal(valortxt));
            vTr.removeLn();
            vTr.lnLabelValue("Valor", Util.toCurrencyPtBr(despesaUpdateRequestDTO.getValor()));
        }

        //FORMA PAGAMENTO ----------------------------------------------------
        vTr.lnLabelValue("Forma Pagamento", despesaResponseDTO.getFormaPagamento().getNome());
        if(despesaCmp.getDefaultComponent().confirmationInput("Alterar Forma Pagamento", false)){
            FormaPagamentoResponseDTO formaPagamentoResponseDTO = despesaCmp.getFormaPagamentoComponent().sellectFormaPagamento();
            despesaUpdateRequestDTO.setFormaPagamento(new FormaPagamentoRefDTO());
            despesaUpdateRequestDTO.getFormaPagamento().setId(formaPagamentoResponseDTO.getId());
            vTr.removeLn();
            vTr.lnLabelValue("Forma Pagamento", formaPagamentoResponseDTO.getNome());
        }

        //OBSERVAÇÃO ----------------------------------------------------
        vTr.lnLabelValue("Observação", despesaResponseDTO.getFormaPagamento().getNome());
        if(despesaCmp.getDefaultComponent().confirmationInput("Alterar Observação", false)){
            despesaUpdateRequestDTO.setObs(despesaCmp.getDefaultComponent().inputoObs());
            vTr.removeLn();
            vTr.lnLabelValue("Observação", despesaUpdateRequestDTO.getObs());
        }

        if(despesaCmp.getDefaultComponent().confirmationInput("Salvar alterações", false)){
            despesaCmp.getDespesaService().update(despesaUpdateRequestDTO);
        }
    }
}
