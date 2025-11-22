import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {TipoDespesa} from '@model/tipo-despesa';
import {FormaPagamento} from '@model/forma-pagamento';
import {Fornecedor} from '@model/fornecedor';
import {FormsModule} from '@angular/forms';
import {Util} from '@util/util';
import {Button} from 'primeng/button';
import {Toast} from 'primeng/toast';
import {Despesa} from '@model/despesa';
import {ActivatedRoute, Router} from '@angular/router';
import {CurrencyPipe, NgIf} from '@angular/common';
import {AutoComplete, AutoCompleteCompleteEvent} from 'primeng/autocomplete';
import {firstValueFrom} from 'rxjs';
import {Message} from 'primeng/message';
import {ComboDefaultComponent} from '@shared/components/combo-default/combo-default.component';
import {InputDateComponent} from '@shared/components/input-date/input-date.component';
import {InputMoneyComponent} from '@shared/components/input-money/input-money.component';
import {LoadingModalComponent} from '@shared/loading-modal/loading-modal.component';
import {DespesaService} from '@service/DespesaService';
import {TipoDespesaService} from '@service/TipoDespesaService';
import {FormaPagamentoService} from '@service/FormaPagamentoService';
import {FornecedorService} from '@service/FornecedorService';

@Component({
  selector: 'app-despesa-form',
  imports: [
    FormsModule,
    Button,
    Toast,
    NgIf,
    AutoComplete,
    Message,
    CurrencyPipe,
    ComboDefaultComponent,
    InputDateComponent,
    InputMoneyComponent,
    LoadingModalComponent
  ],
  templateUrl: './despesa-form.component.html',
  standalone: true,
  styleUrl: './despesa-form.component.css',
  providers: [MessageService],
})
export class DespesaFormComponent implements OnInit{
  tiposDespesa:TipoDespesa[]=[];
  formasPgto:FormaPagamento[]=[];
  fornecedores:Fornecedor[]=[];
  loading:boolean=false;
  tipoDespesaSelecionado:TipoDespesa = new TipoDespesa();
  formaPgtoSelecionado:FormaPagamento = new FormaPagamento()
  fornecedorSelecionado:Fornecedor = new Fornecedor();
  data:string='';
  valor:string='0,00';
  obs:string='';
  idEdicao!:number;
  valorTotal:number=0;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private despesaService: DespesaService,
              private tipoDespesaService: TipoDespesaService,
              private formaPagamentoService: FormaPagamentoService,
              private fornecedorService: FornecedorService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.idEdicao = params['id'];
    });

    this.loadInit();
    this.getTotal();
    this.fornecedorSelecionado.nome = '';

    if(this.idEdicao) {
      this.despesaService.getDespesaById(this.idEdicao).subscribe({
        next: (res: Despesa) => {
          this.tipoDespesaSelecionado = res.tipoDespesa;
          this.tipoDespesaSelecionado = res.tipoDespesa;
          this.fornecedorSelecionado = res.fornecedor;
          this.formaPgtoSelecionado = res.formaPagamento;
          this.data = Util.dateToDataBR(res.dataPagamento);
          this.valor = Util.formatFloatToReal(res.valor.toFixed(2).toString());
          this.obs = res.obs
        },
        error: () => Util.showMsgErro(this.messageService, 'Erro ao consulta despesa'),
        complete: () => {
        }
      });
    }
  }

  async loadInit(){
    this.tiposDespesa = await firstValueFrom(this.tipoDespesaService.getTiposDespesa());
    this.formasPgto = await firstValueFrom(this.formaPagamentoService.getFormasPagamento());
    if(!this.idEdicao){
      this.tipoDespesaSelecionado = this.tiposDespesa[0];
      this.formaPgtoSelecionado = this.formasPgto[0];
    }
  }

  async getTotal(){
    this.despesaService.getValorTotal('').subscribe({
      next: (res: any) => this.valorTotal = Number(res)
    });
  }

  search(event: AutoCompleteCompleteEvent) {
    this.loading=true;
    this.fornecedorService.getFornecedorBusca(event.query).subscribe({
      next: (res:Fornecedor[]) => {
        this.fornecedores = [];
        this.fornecedores = res;
      },
      error: () => Util.showMsgErro(this.messageService,'Erro ao consultar de fornecedores'),
      complete: () => this.loading = false
    });
  }

  save(){
    if(!Util.dataIsValida(this.data))
      Util.showMsgErro(this.messageService, 'Data inválida!');
    else if(['0,00','0','', null, undefined].indexOf(this.valor.trim()) == 0)
      Util.showMsgErro(this.messageService, 'Valor inválido!');
    else if(!this.fornecedorSelecionado)
      Util.showMsgErro(this.messageService, 'Fornecedor inválida!');
    else{
      this.loading=true;
      var despesa:Despesa = new Despesa();
      if(this.idEdicao)
        despesa.id = this.idEdicao;
      despesa.tipoDespesa = this.tipoDespesaSelecionado;
      despesa.fornecedor = this.fornecedorSelecionado;
      despesa.formaPagamento = this.formaPgtoSelecionado;
      despesa.dataPagamento = Util.dataBRtoDataIso(this.data);
      despesa.valor =  Util.formatMoedaToFloat(Util.formatFloatToReal(this.valor));
      despesa.obs = this.obs;
      if(despesa.id)
        this.updateDespesa(despesa);
      else
        this.createDespesa(despesa);
    }
  }

  updateDespesa(despesa:Despesa){
    this.despesaService.update(despesa).subscribe({
      next: (res:Despesa) => Util.showMsgSuccess(this.messageService,'Despesa atualizada!'),
      error: () => Util.showMsgErro(this.messageService,'Erro ao salvar o despesa!'),
      complete: () => {
        this.loading = false;
        this.router.navigate(['/despesa-table'])
        this.valor = '0,00';
        this.obs = '';
        this.getTotal();
      }
    });
  }

  createDespesa(despesa:Despesa){
    this.despesaService.create(despesa).subscribe({
      next: () => Util.showMsgSuccess(this.messageService,'Despesa salva!'),
      error: () => Util.showMsgErro(this.messageService,'Erro ao salvar o despesa'),
      complete: () => {
        this.loading = false;
        this.valor = '0,00';
        this.obs = '';
        this.getTotal();
      }
    });
  }
}
