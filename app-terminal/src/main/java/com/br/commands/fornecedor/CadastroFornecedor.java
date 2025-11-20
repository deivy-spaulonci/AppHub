package com.br.commands.fornecedor;

import com.br.components.FornecedorComponent;
import com.br.config.ShellHelper;
import com.br.dto.ref.CidadeRefDTO;
import com.br.dto.request.create.FornecedorCreateRequestDTO;
import com.br.dto.response.CidadeResponseDTO;
import com.br.dto.response.FornecedorResponseDTO;
import com.br.entity.Estado;
import com.br.util.VTerminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.ShellComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ShellComponent
public class CadastroFornecedor {

    private FornecedorComponent fornecedorCmp;
    private VTerminal vTr;
    private int largura = 100;

    @Autowired
    public CadastroFornecedor(FornecedorComponent fornecedorCmp) {
        this.fornecedorCmp = fornecedorCmp;
        this.vTr = new VTerminal(fornecedorCmp.getDefaultComponent().getShellHelper());
    }

    public void cadastrar() {
        var contForn = true;

        List<SelectorItem<String>> tpPerson = new ArrayList<>();
        tpPerson.add(SelectorItem.of("Pessoa Jurídica", "1"));
        tpPerson.add(SelectorItem.of("Pessoa Física", "2"));

        do{
            vTr.clear();
            String pessoa = fornecedorCmp.getDefaultComponent().selectDefault(tpPerson, "Selecionar Tipo Pessoa");

            if(pessoa.equals("1")){
                cadastrarPessoaJuridica();
                contForn = fornecedorCmp.getDefaultComponent().confirmationInput("Salvar outro Fornecedor", true);
            }else{
                String cpf =  fornecedorCmp.getDefaultComponent().inputTextDefault("CPF :", "");
                List<FornecedorResponseDTO> forncs = fornecedorCmp.getFornecedorService().listFornecedoresSorted(cpf, Sort.by("nome"));
                if(forncs.isEmpty()){
                    cadastrarPessoaFisica(cpf);
                    vTr.clear();
                    contForn = fornecedorCmp.getDefaultComponent().confirmationInput("Salvar outro Fornecedor", true);
                }else{
                    vTr.msgErro("Fornecedor com esse CPF já existe");
                    contForn = false;
                }
            }
        }while (contForn == true);
        vTr.clear();
    }

    public void cadastrarPessoaFisica(String cpf) {
        vTr.lnDefault(largura);
        vTr.lnLabelValue("CPF", cpf);
        String nome = fornecedorCmp.getDefaultComponent().inputTextDefault("Nome:", "");
        vTr.lnLabelValue("Nome", nome);
        String rasaoSocial = fornecedorCmp.getDefaultComponent().inputTextDefault("Razão Social :", nome);
        vTr.lnLabelValue("Razão Social ", rasaoSocial);

        List<SelectorItem<String>> estados = new ArrayList<>();
        Arrays.stream(Estado.values()).toList().forEach(estado ->
                estados.add(SelectorItem.of(estado.getValue(), estado.getValue()))
        );
        String estado = fornecedorCmp.getDefaultComponent().selectDefault(estados, "Selecionar Estado");

        boolean contCidade = true;
        String cidadeCodIbge = null;
        List<CidadeResponseDTO> cidadeResponseDTOS;
        CidadeResponseDTO cidadeResponseDTO = null;
        do{
            String cidadeSearch =  fornecedorCmp.getDefaultComponent().inputTextDefault("Cidade :", "");
            List<SelectorItem<String>> cidades = new ArrayList<>();
            cidadeResponseDTOS =  fornecedorCmp.getCidadeService().listCidadesByUfContainingNome(estado, cidadeSearch);
            cidadeResponseDTOS.forEach(cidade -> {
                cidades.add(SelectorItem.of(cidade.getNome(), cidade.getIbgeCod()));
            });
            if(cidades.isEmpty()){
                contCidade = fornecedorCmp.getDefaultComponent().confirmationInput("Nenhuma cidade encontrada, continuar", true);
            }else{
                cidadeCodIbge = fornecedorCmp.getDefaultComponent().selectDefault(cidades, "Selecionar Cidade");

                String finalCidadeCodIbge = cidadeCodIbge;
                cidadeResponseDTO = cidadeResponseDTOS.stream()
                        .filter(cidadeResponseDTO1 -> cidadeResponseDTO1.getIbgeCod().equals(finalCidadeCodIbge))
                        .findFirst().orElse(null);

                contCidade = false;
            }

        }while (contCidade==true);

        if(cidadeResponseDTO!=null)
            vTr.lnLabelValue("Cidade", cidadeResponseDTO.getNome() +" - " + cidadeResponseDTO.getUf());

        vTr.lnDefault(largura);

        if(fornecedorCmp.getDefaultComponent().confirmationInput("Salvar Fornecedor", true)){
            FornecedorCreateRequestDTO fornecedorCreateRequestDTO = FornecedorCreateRequestDTO.builder()
                    .cpf(cpf)
                    .nome(nome)
                    .cidade(new CidadeRefDTO())
                    .razaoSocial(rasaoSocial)
                    .build();
            fornecedorCreateRequestDTO.getCidade().setIbgeCod(cidadeCodIbge);
            fornecedorCmp.getFornecedorService().save(fornecedorCreateRequestDTO);
        }
    }

    public FornecedorResponseDTO findByCnpj(String cnpj) {
        try {
            return  fornecedorCmp.getFornecedorService().findByCnpj(cnpj);
        } catch (Exception ex) {
            return null;
        }
    }

    public void cadastrarPessoaJuridica(){
        boolean cont = false;
        FornecedorResponseDTO  fornecedorResponseDTO;

        do{
            vTr.clear();
            String cnpj = fornecedorCmp.getDefaultComponent().inputTextDefault("CNPJ", "");
            if(!fornecedorCmp.getFornecedorService().isCnpjValido(cnpj)){
                vTr.msgErro("CNPJ inválido");
                cont = fornecedorCmp.getDefaultComponent().confirmationInput("Deseja continuar", true);
            } else {
                vTr.msg(" Buscando fornecedor no banco...");

                if (this.findByCnpj(cnpj) == null) {
                    vTr.removeLn();
                    vTr.msgWarn("Fornecedor não encontrado no banco, buscando na internet");
                    fornecedorResponseDTO = fornecedorCmp.getFornecedorService().getFornecedorFromWeb(cnpj);
                    if (fornecedorResponseDTO == null) {
                        vTr.removeLn();
                        cont = fornecedorCmp.getDefaultComponent().confirmationInput("Fornecedor não encontrado!  Deseja continuar", true);
                    }else {
                        vTr.clear();
                        vTr.lnDefault(largura);
                        vTr.msgSucess("|  Fornecedor econtrado!");
                        vTr.lnLabelValue("Nome", fornecedorResponseDTO.getNome());
                        vTr.lnLabelValue("Razao Social", fornecedorResponseDTO.getRazaoSocial());
                        vTr.lnLabelValue("CNPJ", fornecedorResponseDTO.getCnpj());
                        vTr.lnLabelValue("Cidade", fornecedorResponseDTO.getCidade().getNome()+ " - "+ fornecedorResponseDTO.getCidade().getUf());
                        vTr.lnDefault(largura);

                        if (fornecedorCmp.getDefaultComponent().confirmationInput("Cadastrar", true)) {
                            CidadeRefDTO cidadeRefDTO = CidadeRefDTO.builder().ibgeCod(fornecedorResponseDTO.getCidade().getIbgeCod()).build();
                            FornecedorCreateRequestDTO fornecedorCreateRequestDTO = FornecedorCreateRequestDTO.builder()
                                    .nome(fornecedorResponseDTO.getNome())
                                    .razaoSocial(fornecedorResponseDTO.getRazaoSocial())
                                    .cnpj(fornecedorResponseDTO.getCnpj())
                                    .cpf(fornecedorResponseDTO.getCpf())
                                    .cidade(cidadeRefDTO)
                                    .build();
                            fornecedorResponseDTO = fornecedorCmp.getFornecedorService().save(fornecedorCreateRequestDTO);

                            vTr.msgSucess("Fornecedor salvo");
                        }
                    }

                }else{
                    cont = fornecedorCmp.getDefaultComponent().confirmationInput("Fornecedor ja existe ! Deseja continuar", true);
                }
            }
        }while(cont);
    }
}
