package com.br.commands.despesa;

import com.br.components.DespesaComponent;
import com.br.dto.ref.FormaPagamentoRefDTO;
import com.br.dto.ref.FornecedorRefDTO;
import com.br.dto.ref.TipoDespesaRefDTO;
import com.br.dto.request.create.DespesaCreateRequestDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.FornecedorResponseDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.util.Util;
import com.br.util.VTerminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;

import java.math.BigDecimal;


@ShellComponent
public class CadastroDespesa {

    private DespesaComponent despesaCmp;
    private VTerminal vTr;
    private int largura = 0;

    @Autowired
    public CadastroDespesa(DespesaComponent despesaCmp) {
        this.despesaCmp = despesaCmp;
        this.vTr = new VTerminal(despesaCmp.getDefaultComponent().getShellHelper());
    }

    public void cadastrar() {
        var exit = false;
        DespesaCreateRequestDTO despesaCreateRequestDTO = new DespesaCreateRequestDTO();
        do {
            vTr.clear();
            vTr.lnDefault(largura);
            //TIPO DESPESA ----------------------------------------------------
            despesaCreateRequestDTO.setTipoDespesa(new TipoDespesaRefDTO());
            TipoDespesaResponseDTO tipoDespesaResponseDTO = despesaCmp.getTipoDespesaComponent().selectTipoDespesa();
            despesaCreateRequestDTO.getTipoDespesa().setId(tipoDespesaResponseDTO.getId());
            vTr.lnLabelValue("Tipo Despesa", tipoDespesaResponseDTO.getNome());

            //FORNECEDOR ----------------------------------------------------
            FornecedorResponseDTO fornec = despesaCmp.getFornecedorComponent().selectTableFornecedor();
            vTr.removeLn();
            despesaCreateRequestDTO.setFornecedor(new FornecedorRefDTO());
            despesaCreateRequestDTO.getFornecedor().setId(fornec.getId());
            vTr.lnLabelValue("Fornecedor", fornec.getNome());

            //DATA ----------------------------------------------------
            String datatxt = despesaCmp.getDefaultComponent().inputData("Data Pagamento");;
            despesaCreateRequestDTO.setDataPagamento(Util.getData(datatxt));
            vTr.lnLabelValue("Data Pagamento", Util.toDatePtBr(despesaCreateRequestDTO.getDataPagamento()));

            //VALOR ----------------------------------------------------
            String valortxt = despesaCmp.getDefaultComponent().inputValor("Valor");
            despesaCreateRequestDTO.setValor(new BigDecimal(valortxt));
            vTr.lnLabelValue("Valor", Util.toCurrencyPtBr(despesaCreateRequestDTO.getValor()));

            //FORMA PAGAMENTO ----------------------------------------------------
            despesaCreateRequestDTO.setFormaPagamento(new FormaPagamentoRefDTO());
            FormaPagamentoResponseDTO formaPagamentoResponseDTO = despesaCmp.getFormaPagamentoComponent().sellectFormaPagamento();
            despesaCreateRequestDTO.getFormaPagamento().setId(formaPagamentoResponseDTO.getId());
            vTr.lnLabelValue("Forma Pagamento", formaPagamentoResponseDTO.getNome());

            //OBSERVAÇÃO ----------------------------------------------------
            despesaCreateRequestDTO.setObs(despesaCmp.getDefaultComponent().inputoObs());
            vTr.lnLabelValue("Observação", despesaCreateRequestDTO.getObs());

            vTr.lnDefault(largura);

            if(despesaCmp.getDefaultComponent().confirmationInput("Salvar Despesa", true))
                despesaCmp.getDespesaService().save(despesaCreateRequestDTO);
           else
                despesaCreateRequestDTO = new DespesaCreateRequestDTO();

            exit  = despesaCmp.getDefaultComponent().confirmationInput("Incluir outra Despesa", true);
        }while(exit);
    }

}
