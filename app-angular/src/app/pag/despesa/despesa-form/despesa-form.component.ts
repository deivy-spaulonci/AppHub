import {Component, OnInit} from '@angular/core';
import {Card} from 'primeng/card';
import {DefaultService} from '../../../service/default.service';
import {MessageService} from 'primeng/api';
import {TipoDespesa} from '../../../model/tipo-despesa';
import {FormaPagamento} from '../../../model/forma-pagamento';
import {Fornecedor} from '../../../model/fornecedor';
import {Select} from 'primeng/select';
import {FormsModule} from '@angular/forms';
import {InputMask} from 'primeng/inputmask';
import {Util} from '../../../util/util';
import {InputText} from 'primeng/inputtext';
import {Button} from 'primeng/button';
import {Toast} from 'primeng/toast';
import {Despesa} from '../../../model/despesa';
import {ActivatedRoute, Router} from '@angular/router';
import {CurrencyPipe, NgIf} from '@angular/common';
import {Panel} from "primeng/panel";
import {AutoComplete, AutoCompleteCompleteEvent} from 'primeng/autocomplete';
import {firstValueFrom} from 'rxjs';
import {Message} from 'primeng/message';
import {ComboDefaultComponent} from '../../../shared/components/combo-default/combo-default.component';
import {InputDateComponent} from '../../../shared/components/input-date/input-date.component';
import {InputMoneyComponent} from '../../../shared/components/input-money/input-money.component';

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
    InputMoneyComponent
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
              private defaultService: DefaultService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.idEdicao = params['id'];
    });

    this.loadInit();
    this.getTotal();
    this.fornecedorSelecionado.nome = '';

    if(this.idEdicao){
      this.defaultService.get('despesa/'+this.idEdicao).subscribe({
        next: res => {
          this.tipoDespesaSelecionado = res.tipoDespesa;
          this.fornecedorSelecionado = res.fornecedor;
          this.formaPgtoSelecionado = res.formaPagamento;
          this.data = Util.dateToDataBR(res.dataPagamento);
          console.log(this.data)
          this.valor = Util.formatFloatToReal(res.valor.toFixed(2).toString());
          this.obs = res.obs
        },
        error: error => {},
        complete: () => {}
      });
    }

  }

  async loadInit(){
    this.tiposDespesa = await firstValueFrom(this.defaultService.get('tipo-despesa'));
    this.formasPgto = await firstValueFrom(this.defaultService.get('forma-pagamento'));
    if(!this.idEdicao){
      this.tipoDespesaSelecionado = this.tiposDespesa[0];
      this.formaPgtoSelecionado = this.formasPgto[0];
    }
  }

  async getTotal(){
    this.valorTotal = await firstValueFrom(this.defaultService.get('despesa/valorTotal'))
  }

  search(event: AutoCompleteCompleteEvent) {
    this.loading=true;
    this.defaultService.get('fornecedor/find/'+event.query).subscribe({
      next: res => {
        this.fornecedores = [];
        this.fornecedores = res;
      },
      error: error => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de fornecedores'});
      },
      complete: () => {
        this.loading=false;
      }
    });
  }

  goParaDespesalist(){
    this.router.navigate(['/despesa-table'])
  }

  save(){
    console.log(this.data)
    if(!Util.dataIsValida(this.data))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Data inválida!'});
    else if(['0,00','0','', null, undefined].indexOf(this.valor.trim()) == 0)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Valor inválida!'});
    else if(!this.fornecedorSelecionado)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Fornecedor inválida!'});
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

      this.defaultService.save(despesa, 'despesa').subscribe({
        next: res => {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Despesa salva!'});
        },
        error: error => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'erro ao salvar o despesa'});
        },
        complete: () => {
          if(this.idEdicao)
            this.router.navigate(['/despesa-table'])
          this.loading = false;
          this.valor = '0,00';
          this.obs = '';
          this.getTotal();
        }
      });
    }
  }

  maskaraMoeda($event: KeyboardEvent) {
    const element = ( $event.target as HTMLInputElement);
    element.value = Util.formatFloatToReal(element.value);
  }

}
