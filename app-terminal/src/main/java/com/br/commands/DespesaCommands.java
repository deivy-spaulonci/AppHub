package com.br.commands;

import com.br.business.service.*;
import com.br.config.ShellHelper;
import com.br.entity.*;
import com.br.filter.DespesaFilter;
import com.br.repository.FornecedorRepository;
import com.br.util.Util;
import com.br.util.Validate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.jline.terminal.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.*;

@Log4j2
@ShellComponent
public class DespesaCommands extends AbstractShellComponent {

	@Autowired
	private ComponentFlow.Builder componentFlowBuilder;
	@Setter
	@Getter
	@Autowired
	private Terminal terminal;
	@Autowired
	private TipoContaService tipoContaService;
	@Autowired
	private FornecedorService fornecedorService;
	@Autowired
	private DespesaService despesaService;

	private DefaultComponent defaultComponent;
	private FornecedorComp fornecedorComp;

	@Autowired
	private ShellHelper shellHelper;
    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private TipoDespesaService tipoDespesaService;
    @Autowired
    private FormaPagamentoService formaPagamentoService;

	@ShellMethod("Status despesas")
	public void statusDespesa(){
		DespesaFilter despesaFilter = DespesaFilter.builder().build();
		shellHelper.printInfo("-".repeat(50));
		shellHelper.printInfo(String.format("> Registros: %-10s", despesaService.getCountDespesa()));
		shellHelper.printInfo(String.format("> Total: %-10s", Util.toCurrencyPtBr(despesaService.getSumDespesa(despesaFilter))));
		shellHelper.printInfo("-".repeat(50));
	}

	@ShellMethod("consulta despesas")
	public void findDespesa(){
		this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
		this.fornecedorComp = new FornecedorComp(terminal, getTemplateExecutor(), getResourceLoader());

		boolean contEdtDespesa = true;
		DespesaFilter despesaFilter = DespesaFilter.builder().build();
		int size = 20;
		int numberPage = 1;
		int order = 1;
		String campoOrder = "dataPagamento";
		Page<Despesa> pg;
		do{
			Sort.Direction direction = (order==0 ? Sort.Direction.ASC : Sort.Direction.DESC);
			Pageable pageable = PageRequest.of(numberPage-1, size, Sort.by(direction, campoOrder));
			pg = despesaService.listDespesasPaged(despesaFilter, pageable);
			String header = String.format(" %-6s | %-25s | %-80s | %-10s | %15s | %34s |  %-30s","id", "Tipo Despesa", "Fonrecedor", "Data", "Valor", "Forma Pagamento", "Obs");
			System.out.print("\033\143");
			shellHelper.printInfo("-".repeat(230));
			shellHelper.printInfo(header);
			shellHelper.printInfo("-".repeat(230));
			if(pg.hasContent()){
				pg.getContent().forEach(despesa -> {
					String label = String.format(" %-6s | %-25s | %-80s | %-10s | %15s | %34s |  %-30s",
							despesa.getId(),
							despesa.getTipoDespesa().getNome(),
							despesa.getFornecedor().getNome(),
							Util.toDatePtBr(despesa.getDataPagamento()),
							Util.toCurrencyPtBr(despesa.getValor()),
							despesa.getFormaPagamento().getNome(),
							despesa.getObs());
					shellHelper.printInfo(label);
				});
			}else
				shellHelper.printInfo(" !!! nenhum despesa encontrado... !!!");
			shellHelper.printInfo("-".repeat(230));
			BigDecimal total = despesaService.getSumDespesa(despesaFilter);
			shellHelper.printInfo("");
			shellHelper.printInfo(String.format(" pagina: %-10s  paginas: %-10s tamanho: %-10s  registros: %-10s  total: $ %-10s",
					numberPage, pg.getTotalPages(), size, pg.getTotalElements(), Util.toCurrencyPtBr(total)));
			shellHelper.printInfo("");
			shellHelper.printInfo("Filtros:");
			if(despesaFilter.getId()!=null) shellHelper.printInfo("	-> id = " + despesaFilter.getId());
			if(despesaFilter.getTipoDespesa()!=null) shellHelper.printInfo("	-> Tipo despesa = " + despesaFilter.getTipoDespesa().getNome());
			if(despesaFilter.getFormaPagamento()!=null) shellHelper.printInfo("	-> Forma Pagamento = " + despesaFilter.getFormaPagamento().getNome());
			if(despesaFilter.getDataInicial()!=null) shellHelper.printInfo("	-> Data Inicial = " + Util.toDatePtBr(despesaFilter.getDataInicial()));
			if(despesaFilter.getDataFinal()!=null) shellHelper.printInfo("	-> Data Final = " + Util.toDatePtBr(despesaFilter.getDataFinal()));
			if(despesaFilter.getFornecedor()!=null) shellHelper.printInfo("	-> Fornecedor = "
					+ fornecedorService.findById(despesaFilter.getFornecedor().getId()).get().getNome());
			shellHelper.printInfo("");
			shellHelper.printInfo("paginação > [exit ]  [pag xx  ]  [order xx ]  [size]");
			shellHelper.printInfo("filtros   > [id xx]  [tipo    ]  [forma    ]  [periodo 00/00/0000 00/00/0000] [fornecedor] [clear]");
			shellHelper.printInfo("comandos  > [clear]  [pagar xx]  [editar xx]");
			shellHelper.printInfo("");

			String resposta = this.defaultComponent.inputTextDefault(" ->", "");
			if(resposta.equals("exit")){
				contEdtDespesa = false;
			}else{
				String[] resparr = resposta.split(" ");
				switch (resparr[0]){
					case "size" :
						size = this.defaultComponent.selectPageDefault();
						break;
					case "pag" : {
						if(resparr[1]!=null && resparr[1].matches("[0-9]+")) {
							Integer retInt = Integer.parseInt(resparr[1]);
							if(retInt>=1 && retInt<=pg.getTotalPages()) {
								numberPage = pg.getContent().size() < size ? 1 : retInt;
							}else
								shellHelper.printError(String.format("valor digitado tem que ser entre 1 e %d",pg.getTotalPages()));
						}else
							shellHelper.printError("valor digitado nao corresponde a um numero");
						break;
					}
					case "order":{
						List<SelectorItem<String>> ltOpc = new ArrayList<>(){{
							add(SelectorItem.of("Id", "id"));
							add(SelectorItem.of("Tipo Despesa", "tipoDespesa"));
							add(SelectorItem.of("Forma Pagamento", "formaPagamento"));
							add(SelectorItem.of("Data Pagamento", "dataPagamento"));
							add(SelectorItem.of("Fonrecedor", "fornecedor.nome"));
						}};
						campoOrder = this.defaultComponent.selectDefault(ltOpc, "Selecionar Campo");
						List<SelectorItem<String>> opcOrder = new ArrayList<>();
						opcOrder.add(SelectorItem.of("Ascesdente", "0"));
						opcOrder.add(SelectorItem.of("Descendente", "1"));
						order = Integer.valueOf(this.defaultComponent.selectDefault(opcOrder, "Selecionar Ordem Campo"));
						break;
					}
					case "id": {
						//String id = re//this.defaultComponent.inputTextDefault(" id ->", "");
                        if ((resparr[1] != null && resparr[1].matches("[0-9]+"))) {
                            despesaFilter.setId(Long.valueOf(resparr[1]));
                        } else {
                            shellHelper.printError("valor digitado nao corresponde a um numero !");
                        }
                        break;
					}
//					case "tipo": despesaFilter.setTipoDespesa(TipoDespesa.forValues(this.defaultComponent.selectTipoDespesa())); break;
//					case "forma": despesaFilter.setFormaPagamento(FormaPagamento.forValues(this.defaultComponent.selectFormaPagamento())); break;
					case "periodo":{
						if(resparr[1]!=null && Validate.isValidDate(resparr[1])) {
							despesaFilter.setDataInicial(Util.getData(resparr[1]));
							if(resparr.length==3 && Validate.isValidDate(resparr[2]))
								despesaFilter.setDataFinal(Util.getData(resparr[2]));
							else
								shellHelper.printError("data final inválida!");
						}else
							shellHelper.printError("data incial inválida!");
						break;
					}
					case "fornecedor":{
						despesaFilter.setFornecedor(new Fornecedor());
						String fornec = fornecedorComp.selectTableFornecedor(fornecedorService.findFornecedores());
						despesaFilter.getFornecedor().setId(new BigInteger(fornec));
						break;
					}
					case "editar" :{
						if (resparr[1] != null && resparr[1].matches("[0-9]+")) {
							Optional<Despesa> optionalDespesa = this.despesaService.findById(new BigInteger(resparr[1].toString()));
							optionalDespesa.ifPresent(this::editarDespesa);
							if(optionalDespesa.isEmpty())
								shellHelper.printError("Id da despesa não econtrado !");
						}else
							shellHelper.printError("valor digitado nao corresponde a um numero");
						this.defaultComponent.confirmationInput("enter para continuar", true);
						break;
					}
					case "clear": {
						despesaFilter = DespesaFilter.builder().build();
						break;
					}
					default: numberPage = pg.getContent().size() < size ? 1 : numberPage+1;
				}
			}
		}while (!!contEdtDespesa);
	}

	@ShellMethod("cadastro despesas")
	public void addDespesa() {
		this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
		var cont = true;
		Despesa despesaCadastro = new Despesa();
		do {
			System.out.print("\033\143");

			//TIPO DESPESA ----------------------------------------------------
			BigInteger codTipo  = new BigInteger(defaultComponent.selectTipoDespesa(tipoDespesaService));
			Optional<TipoDespesa> tipoDespesaOptional = tipoDespesaService.findById(codTipo);
			if(tipoDespesaOptional.isEmpty())
				return;

			TipoDespesa tipoDespesa = tipoDespesaOptional.get();
			despesaCadastro.setTipoDespesa(tipoDespesa);
			shellHelper.printInfo('✅'+ " Tipo Despesa : ["+tipoDespesa.getId()+"] "+tipoDespesa.getNome());

			//FORNECEDOR ----------------------------------------------------
			Fornecedor fornecedor = this.inputFornecedor();
			if(fornecedor==null)
				return;
			despesaCadastro.setFornecedor(fornecedor);

			//DATA ----------------------------------------------------
			this.inputDataPgto(despesaCadastro);
			shellHelper.printInfo('✅'+ " Data pagamento : "+ Util.toDatePtBr(despesaCadastro.getDataPagamento()));

			//VALOR ----------------------------------------------------
			this.inputValor(despesaCadastro);
			shellHelper.printInfo('✅'+ " Valor : "+ Util.toCurrencyPtBr(despesaCadastro.getValor()));

			//FORMA PAGAMENTO ----------------------------------------------------
			BigInteger codForma = new BigInteger(defaultComponent.selectFormaPagamento(formaPagamentoService));
			Optional<FormaPagamento> formaPagamentoOptional = formaPagamentoService.findById(codForma);
			if(formaPagamentoOptional.isEmpty())
				return;

			FormaPagamento formaPagamento = formaPagamentoOptional.get();
			despesaCadastro.setFormaPagamento(formaPagamento);
			shellHelper.printInfo('✅'+ " Forma Pagamento : ["+formaPagamento.getId()+"] "+formaPagamento.getNome());

			//OBSERVAÇÃO ----------------------------------------------------
			despesaCadastro.setObs(this.defaultComponent.inputoObs());
			shellHelper.printInfo('✅'+ " Observação : "+despesaCadastro.getObs());

			shellHelper.printInfo("-".repeat(230));
			this.showDespesa(despesaCadastro);
			shellHelper.printInfo("-".repeat(230));

			cont = this.defaultComponent.confirmationInput("Salvar Despesa", true);
			if(cont==true)
				despesaService.save(despesaCadastro);
			else
				despesaCadastro = new Despesa();
			cont = this.defaultComponent.confirmationInput("Incluir outra Despesa", true);
		}while(cont);
	}

	public void editarDespesa(Despesa despesa){
		this.defaultComponent = new DefaultComponent(terminal, getTemplateExecutor(), getResourceLoader());
		System.out.print("\033\143");

		this.showDespesa(despesa);

//		if(defaultComponent.confirmationInput("Alterar Tipo Despesa", false))
//			despesa.setTipoDespesa(TipoDespesa.forValues(this.defaultComponent.selectTipoDespesa()));
		if(defaultComponent.confirmationInput("Alterar Fornecedor", false)){
			Fornecedor fornecedor = this.inputFornecedor();
			if(fornecedor!=null)
				despesa.setFornecedor(fornecedor);
		}
		if(defaultComponent.confirmationInput("Alterar Data Pagamento", false))
			this.inputDataPgto(despesa);
		if(defaultComponent.confirmationInput("Alterar Valor", false))
			this.inputValor(despesa);
//		if(defaultComponent.confirmationInput("Alterar Forma Pagamento", false))
//			despesa.setFormaPagamento(FormaPagamento.forValues(this.defaultComponent.selectFormaPagamento()));
		if(defaultComponent.confirmationInput("Alterar Observação", false))
			despesa.setObs(this.defaultComponent.inputoObs());

		System.out.print("\033\143");
		this.showDespesa(despesa);
		if(this.defaultComponent.confirmationInput("Salvar Despesa", true)){
			this.despesaService.save(despesa);
		}
	}

	public void showDespesa(Despesa despesa){
		shellHelper.printInfo(String.format("Tipo Despesa___: %s", despesa.getTipoDespesa()));
		shellHelper.printInfo(
				String.format("Fornecedor_____: %s - %s - %s-%s",
						despesa.getFornecedor().getNome(),
						despesa.getFornecedor().getCnpj(),
						despesa.getFornecedor().getCidade().getNome(),
						despesa.getFornecedor().getCidade().getUf()));
		shellHelper.printInfo(String.format("Data___________: %s", Util.toDatePtBr(despesa.getDataPagamento())));
		shellHelper.printInfo(String.format("Valor__________: %s", Util.toCurrencyPtBr(despesa.getValor())));
		shellHelper.printInfo(String.format("Forma Pgto_____: %s", despesa.getFormaPagamento()));
		shellHelper.printInfo(String.format("Obs____________: %s", despesa.getObs()));
	}

	public Fornecedor inputFornecedor(){
		//FORNECEDOR ----------------------------------------------------
		List<SelectorItem<String>> tpPerson = new ArrayList<>();
		tpPerson.add(SelectorItem.of("Pessoa Jurídica", "1"));
		tpPerson.add(SelectorItem.of("Pessoa Física", "2"));
		String pessoa = this.defaultComponent.selectDefault(tpPerson, "Selecionar Tipo Pessoa");

		Fornecedor fornecedor = null;

		if(pessoa.equals("1")){//pessoa juridica
			var contForn = true;
			String cnpj = "";
			do{
				cnpj = this.defaultComponent.inputCnpjFornecedor();
				if(!fornecedorService.isCnpjValido(cnpj)){
					shellHelper.printError('⛔' + " CNPJ inválido !!!");
					contForn = this.defaultComponent.confirmationInput("Fornecedor não encontrado!  Deseja continuar", true);
				}else{
					shellHelper.printInfo(" Buscando fornecedor no banco...");
					fornecedor = fornecedorService.findByCnpj(cnpj);
					if(fornecedor == null){
						shellHelper.printWarning(" Fornecedor não encontrado no banco, buscando na internet...");
						fornecedor = fornecedorService.getFornecedorFromWeb(cnpj);
						if(fornecedor == null){
							contForn = this.defaultComponent.confirmationInput("Fornecedor não encontrado!  Deseja continuar", true);
						}else {
							shellHelper.printSuccess(" Fornecedor econtrado!");
							shellHelper.printInfo(String.format("Nome : %s", fornecedor.getNome()));
							shellHelper.printInfo(String.format("Razao Social : %s", fornecedor.getRazaoSocial()));
							shellHelper.printInfo(String.format("CNPJ : %s", fornecedor.getCnpj()));
							shellHelper.printInfo(String.format("Cidade : %s = %s", fornecedor.getCidade().getNome(), fornecedor.getCidade().getUf()));
							if(this.defaultComponent.confirmationInput("Cadastar", true)){
								shellHelper.printInfo("salvando novo fornecedor...");
								fornecedor  = fornecedorService.save(fornecedor);
								contForn = false;
							}else{
								contForn = this.defaultComponent.confirmationInput("Deseja continuar", true);
							}
						}
					}else{
						shellHelper.printInfo(" Fornecedor no banco econtrado!");
						shellHelper.printInfo('✅'+ " ["+fornecedor.getId()+"] " + fornecedor.getNome());
						contForn = false;
					}
				}
			} while (contForn == true);

		}else{//PESSOA FISICA

			String pfbusca =  this.defaultComponent.inputTextDefault("Fornecedor Pessoa Fisica :", "");
			List<SelectorItem<String>> pfs = new ArrayList<>();
			List<Fornecedor> forncs = fornecedorService.listFornecedoresSorted(pfbusca, Sort.by("nome"));
			if(!forncs.isEmpty()){
				forncs.forEach(pf ->{
					String leg = String.format("%-50s | CPF : %-20s | %s - %s", pf.getNome(), pf.getCpf(), pf.getCidade().getNome(), pf.getCidade().getUf());
					pfs.add(SelectorItem.of(leg , pf.getId().toString()));
				});
				String idPFF = this.defaultComponent.selectDefault(pfs, "Selecionar Fornecedor");
				fornecedor = forncs.stream().filter(fornec -> fornec.getId().equals(new BigInteger(idPFF))).findFirst().get();

			}else{//novo fornecedor pessoa fisica
				if(this.defaultComponent.confirmationInput("Nenhum forencedor pessoa fisica econtrado cadastrar manualmente", true)){
					List<SelectorItem<String>> estados = new ArrayList<>();
					Arrays.stream(Estado.values()).toList().forEach(estado ->
							estados.add(SelectorItem.of(estado.getValue(), estado.getValue()))
					);
					String estado = this.defaultComponent.selectDefault(estados, "Selecionar Estado");

					boolean contCidade = true;
					String cidadeCodIbge = null;
					do{
						String cidadeSearch =  this.defaultComponent.inputTextDefault("Cidade :", "");
						List<SelectorItem<String>> cidades = new ArrayList<>();
						cidadeService.listCidadesByUfContainingNome(estado, cidadeSearch).forEach(cidade -> {
							cidades.add(SelectorItem.of(cidade.getNome(), cidade.getIbgeCod()));
						});
						if(cidades.isEmpty()){
							contCidade = this.defaultComponent.confirmationInput("Nenhuma cidade encontrada, continuar", true);
						}else{
							cidadeCodIbge = this.defaultComponent.selectDefault(cidades, "Selecionar Cidade");
							contCidade = false;
						}
					}while (contCidade==true);

					fornecedor = new Fornecedor().builder()
							.cpf(this.defaultComponent.inputTextDefault("CPF :", ""))
							.nome(this.defaultComponent.inputTextDefault("Nome :", ""))
							.razaoSocial(this.defaultComponent.inputTextDefault("Razão Social :", ""))
//							.ibgeCod(cidadeCodIbge)
							.build();

					shellHelper.printInfo("salvando novo fornecedor pessoa física...");
					fornecedor  = fornecedorService.save(fornecedor);
				}
			}
		}
		return fornecedor;
	}


	public boolean inputDataPgto(Despesa despesa){
		boolean valid = false;
		do{
			String data = this.defaultComponent.inputTextDefault("Data Pgto -> ", "");
			valid = !data.isEmpty() && Validate.isValidDate(data);
			if(valid)
				despesa.setDataPagamento(Util.getData(data));
		} while (valid ? false :  this.defaultComponent.confirmationInput('⛔' + " Data Pgto inválida! Continuar", true));
		return valid;
	}

	public boolean inputValor(Despesa despesa){
		boolean valid = false;
		do{
			var valor = this.defaultComponent.inputValor().replaceAll(",", ".");
			try{
				despesa.setValor(new BigDecimal(valor));
				valid = despesa.getValor().compareTo(BigDecimal.ZERO) == 1;
			}catch(NumberFormatException e){
				valid  = false;
			}
		}while (valid ? false :  this.defaultComponent.confirmationInput('⛔' + "Valor inválido! Continuar", true));
		return valid;
	}

}
