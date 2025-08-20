package com.br.commands.fornecedor;

import com.br.business.service.CidadeService;
import com.br.business.service.FornecedorService;
import com.br.commands.DefaultComponent;
import com.br.config.ShellHelper;
import com.br.entity.Estado;
import com.br.entity.Fornecedor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.support.SelectorItem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
public class CadastroFornecedor {

    private DefaultComponent defaultComponent;
    private ShellHelper shellHelper;
    private FornecedorService fornecedorService;

    public CadastroFornecedor(DefaultComponent defaultComponent,
                              ShellHelper shellHelper) {
        this.defaultComponent = defaultComponent;
        this.shellHelper = shellHelper;
    }

    public void printFornecedorApi(Fornecedor f){
        shellHelper.printInfo("Fornecedor da api!");
        shellHelper.printInfo("Nome_________: " + f.getNome());
        shellHelper.printInfo("Razão Social_: " + f.getRazaoSocial());
        shellHelper.printInfo("CNPJ / CPF __: " + (Objects.nonNull(f.getCnpj()) ? f.getCnpj() : f.getCpf()));
        shellHelper.printInfo("Ibge_________: " + f.getCidade().getIbgeCod());
    }

    public void printFornecedor(Fornecedor f){
        shellHelper.printInfo("Fornecedor já cadastrado!");
        shellHelper.printInfo("Nome_________: " + f.getNome());
        shellHelper.printInfo("Razão Social_: " + f.getRazaoSocial());
        shellHelper.printInfo("CNPJ / CPF __: " + (Objects.nonNull(f.getCnpj()) ? f.getCnpj() : f.getCpf()));
        shellHelper.printInfo("Cidade_______: " + f.getCidade().getNome());
    }

    public Fornecedor cadastro(FornecedorService fornecedorService,
                               CidadeService cidadeService) {
        //FORNECEDOR ----------------------------------------------------
        List<SelectorItem<String>> tpPerson = new ArrayList<>();
        tpPerson.add(SelectorItem.of("Pessoa Jurídica", "1"));
        tpPerson.add(SelectorItem.of("Pessoa Física", "2"));
        String pessoa = defaultComponent.selectDefault(tpPerson, "Selecionar Tipo Pessoa");

        Fornecedor fornecedor = null;

        if(pessoa.equals("1")){//pessoa juridica
            var contForn = true;
            String cnpj = "";
            do{
                cnpj = defaultComponent.inputCnpjFornecedor();
                if(!fornecedorService.isCnpjValido(cnpj)){
                    shellHelper.printError('⛔' + " CNPJ inválido !!!");
                    contForn = defaultComponent.confirmationInput("Fornecedor não encontrado!  Deseja continuar", true);
                }else{
                    shellHelper.printInfo(" Buscando fornecedor no banco...");
                    fornecedor = fornecedorService.findByCnpj(cnpj);
                    if(fornecedor == null){
                        System.out.print("\033[F\033[K");//apago a linha anterior
                        shellHelper.printWarning(" Fornecedor não encontrado no banco, buscando na internet...");
                        fornecedor = fornecedorService.getFornecedorFromWeb(cnpj);
                        if(fornecedor == null){
                            System.out.print("\033[F\033[K");
                            contForn = defaultComponent.confirmationInput("Fornecedor não encontrado!  Deseja continuar", true);
                        }else {
                            System.out.print("\033[F\033[K");
                            shellHelper.printSuccess(" Fornecedor econtrado!");
                            shellHelper.printInfo(String.format("Nome : %s", fornecedor.getNome()));
                            shellHelper.printInfo(String.format("Razao Social : %s", fornecedor.getRazaoSocial()));
                            shellHelper.printInfo(String.format("CNPJ : %s", fornecedor.getCnpj()));
                            shellHelper.printInfo(String.format("Cidade : %s = %s", fornecedor.getCidade().getNome(), fornecedor.getCidade().getUf()));
                            if(defaultComponent.confirmationInput("Cadastar", true)){
                                shellHelper.printInfo("salvando novo fornecedor...");
                                fornecedor  = fornecedorService.save(fornecedor);
                                contForn = false;
                            }else{
                                contForn = defaultComponent.confirmationInput("Deseja continuar", true);
                            }
                        }
                    }else{
                        System.out.print("\033[F\033[K");
                        shellHelper.printInfo(" Fornecedor econtrado!");
                        //shellHelper.printInfo('✅'+ " ["+fornecedor.getId()+"] " + fornecedor.getNome());
                        contForn = false;
                    }
                }
            } while (contForn == true);

        }else{//PESSOA FISICA

            String pfbusca =  defaultComponent.inputTextDefault("Fornecedor Pessoa Fisica :", "");
            List<SelectorItem<String>> pfs = new ArrayList<>();
            List<Fornecedor> forncs = fornecedorService.listFornecedoresSorted(pfbusca, Sort.by("nome"));
            if(!forncs.isEmpty()){
                forncs.forEach(pf ->{
                    String leg = String.format("%-50s | CPF : %-20s | %s - %s", pf.getNome(), pf.getCpf(), pf.getCidade().getNome(), pf.getCidade().getUf());
                    pfs.add(SelectorItem.of(leg , pf.getId().toString()));
                });
                String idPFF = defaultComponent.selectDefault(pfs, "Selecionar Fornecedor");
                fornecedor = forncs.stream().filter(fornec -> fornec.getId().equals(new BigInteger(idPFF))).findFirst().get();

            }else{//novo fornecedor pessoa fisica
                if(defaultComponent.confirmationInput("Nenhum forencedor pessoa fisica econtrado cadastrar manualmente", true)){
                    List<SelectorItem<String>> estados = new ArrayList<>();
                    Arrays.stream(Estado.values()).toList().forEach(estado ->
                            estados.add(SelectorItem.of(estado.getValue(), estado.getValue()))
                    );
                    String estado = defaultComponent.selectDefault(estados, "Selecionar Estado");

                    boolean contCidade = true;
                    String cidadeCodIbge = null;
                    do{
                        String cidadeSearch =  defaultComponent.inputTextDefault("Cidade :", "");
                        List<SelectorItem<String>> cidades = new ArrayList<>();
                        cidadeService.listCidadesByUfContainingNome(estado, cidadeSearch).forEach(cidade -> {
                            cidades.add(SelectorItem.of(cidade.getNome(), cidade.getIbgeCod()));
                        });
                        if(cidades.isEmpty()){
                            contCidade = defaultComponent.confirmationInput("Nenhuma cidade encontrada, continuar", true);
                        }else{
                            cidadeCodIbge = defaultComponent.selectDefault(cidades, "Selecionar Cidade");
                            contCidade = false;
                        }
                    }while (contCidade==true);

                    fornecedor = new Fornecedor().builder()
                            .cpf(defaultComponent.inputTextDefault("CPF :", ""))
                            .nome(defaultComponent.inputTextDefault("Nome :", ""))
                            .razaoSocial(defaultComponent.inputTextDefault("Razão Social :", ""))
//							.ibgeCod(cidadeCodIbge)
                            .build();

                    shellHelper.printInfo("salvando novo fornecedor pessoa física...");
                    fornecedor  = fornecedorService.save(fornecedor);
                }
            }
        }
        return fornecedor;
    }
}
