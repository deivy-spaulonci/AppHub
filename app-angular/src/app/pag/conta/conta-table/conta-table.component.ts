import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {Card} from 'primeng/card';
import {Toast} from 'primeng/toast';
import {ConfirmationService, MenuItem, MessageService} from 'primeng/api';
import {Router, RouterLink} from '@angular/router';
import {DefaultService} from '../../../service/default.service';
import {Despesa} from '../../../model/despesa';
import {Conta} from '../../../model/conta';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {Message} from 'primeng/message';
import {TipoDespesa} from '../../../model/tipo-despesa';
import {TipoConta} from '../../../model/tipo-conta';
import {FormaPagamento} from '../../../model/forma-pagamento';
import {Fornecedor} from '../../../model/fornecedor';
import {Table, TableLazyLoadEvent, TableModule} from 'primeng/table';
import {Util} from '../../../util/util';
import {Button} from 'primeng/button';
import {CurrencyPipe, DatePipe, JsonPipe, NgClass, NgForOf, NgIf, NgStyle} from '@angular/common';
import {Toolbar} from 'primeng/toolbar';
import {Tooltip} from 'primeng/tooltip';
import {Drawer} from 'primeng/drawer';
import {InputMask} from 'primeng/inputmask';
import {Select} from 'primeng/select';
import {FormsModule} from '@angular/forms';
import {StyleClass} from 'primeng/styleclass';
import {Divider} from 'primeng/divider';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {Fieldset} from 'primeng/fieldset';
import {Panel} from "primeng/panel";
import {ContextMenu} from 'primeng/contextmenu';
import {Dialog} from 'primeng/dialog';
import {Fatura} from '../../../model/fatura';
import {InputText} from 'primeng/inputtext';
import {ComboDefaultComponent} from '../../../shared/components/combo-default/combo-default.component';
import {InputDateComponent} from '../../../shared/components/input-date/input-date.component';
import {FaturaTableComponent} from '../../../shared/components/fatura-table/fatura-table.component';
import {InputMoneyComponent} from '../../../shared/components/input-money/input-money.component';

@Component({
  selector: 'app-conta-table',
  imports: [
    Card,
    Toast,
    ConfirmDialogModule,
    Message,
    TableModule,
    Button,
    CurrencyPipe,
    DatePipe,
    Tooltip,
    InputMask,
    Select,
    FormsModule,
    NgIf,
    ScrollPanelModule,
    ContextMenu,
    Dialog,
    ComboDefaultComponent,
    InputDateComponent,
    FaturaTableComponent,
    InputMoneyComponent
  ],
  templateUrl: './conta-table.component.html',
  providers: [MessageService,ConfirmationService],
  standalone: true,
  styleUrl: './conta-table.component.css'
})
export class ContaTableComponent implements OnInit{
  contas:Conta[]=[];
  totalElements = 0;
  sortField:string='vencimento';
  pageSize = 15;
  valorTotal:number=0;
  loading:boolean=false;
  contaSelecinada!:Conta;
  idFilter:string='';
  tipoContaFilter:TipoConta=new TipoConta;
  vencimentoInicialFilter:string='';
  emissaoInicialFilter:string='';
  vencimentoFinalFilter:string='';
  emissaoFinalFilter:string='';
  tiposConta:TipoConta[]=[];
  formasPgto:FormaPagamento[]=[];
  @ViewChild('dt') table?:Table;
  status:any[]=[];
  statusFilter:any={};
  items!: MenuItem[];
  setContaPagaVisible:boolean=false;

  contaPagaValor:string='0,00';
  contaPagaFormaPgto:FormaPagamento=new FormaPagamento();
  contaPagaDataPgto:string='';

  constructor(private confirmationService: ConfirmationService,
              private router: Router,
              private defaultService: DefaultService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.items = [
      { label: 'Detalhes', icon: 'pi pi-fw pi-search',
        command: () => this.router.navigate(['/conta-form'+this.contaSelecinada.id]) },
    ];

    this.status = [
      { name: 'Em Aberto', code: 'ABERTO' },
      { name: 'Pago', code: 'PAGO' },
      { name: 'Atrasado', code: 'ATRASADO' },
      { name: 'Vencimento Hoje', code: 'HOJE' },
    ];

    this.defaultService.get('conta/page').subscribe({
      next: res =>{ this.contas = res.content; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de contas' }); },
      complete: () => {}
    });
    this.defaultService.get('tipo-conta?sort=nome,asc').subscribe({
      next: res =>{ this.tiposConta = res; },
      error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'consulta de tipo contas' }); },
      complete: () => {}
    });
    this.defaultService.get('forma-pagamento').subscribe({
      next: res =>{ this.formasPgto = res; },
      error: error => {},
      complete: () => {}
    });
  }

  editConta(param:number){
    this.router.navigate(['/conta-form'],{ queryParams: { id: param} })
  }

  setPago(event: Event, conta:Conta){
    this.contaSelecinada = conta;
    this.contaPagaValor = Util.formatFloatToReal(conta.valor.toFixed(2).toString());
    this.contaPagaDataPgto = Util.dateToDataBR(conta.vencimento);
    this.setContaPagaVisible=true;
  }

  pagarConta(){

    if(!Util.dataIsValida(this.contaPagaDataPgto))
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Data pagamento inválida!'});
    else if(['0,00','0','', null, undefined].indexOf(this.contaPagaValor.trim()) == 0)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Valor inválido!'});
    else if(!this.contaPagaFormaPgto || !this.contaPagaFormaPgto.id)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Forma de Pagamento inválida!'});
    else{
      this.loading = true;
      this.contaSelecinada.valor = Util.formatMoedaToFloat(Util.formatFloatToReal(this.contaPagaValor));
      this.contaSelecinada.dataPagamento = Util.dataBRtoDataIso(this.contaPagaDataPgto);
      this.contaSelecinada.formaPagamento = this.contaPagaFormaPgto;

      this.defaultService.save(this.contaSelecinada, 'conta').subscribe({
        next: res => {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Conta salva!'});
        },
        error: error => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'erro ao salvar o conta'});
        },
        complete: () => {
          this.loading = false;
          this.setContaPagaVisible=false;
          this.table?._filter();
        }
      });
    }
  }

  delConta(event: Event, id:number){
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      rejectButtonProps: {
        label: 'Cancelar',
        severity: 'secondary',
        outlined: true,
      },
      acceptButtonProps: {
        label: 'Excluir',
        severity: 'warn',
      },
      accept: () => {
        this.defaultService.delete(id,'conta').subscribe({
          next: res =>{
            this.messageService.add({severity: 'success', summary: 'Success', detail: 'Conta excluída!'});
            this.table?._filter();
          },
          error: error => { this.messageService.add({ severity: 'error', summary: 'Error', detail: 'exlcuir conta' }); },
          complete: () => {}
        })
      },
      reject: () => {},
    });
  }

  clearFilter(){
    this.idFilter='';
    this.tipoContaFilter=new TipoConta;
    this.vencimentoInicialFilter='';
    this.vencimentoFinalFilter='';
    this.emissaoInicialFilter='';
    this.emissaoFinalFilter='';
    this.statusFilter={};
    this.table?._filter();
  }

  loadData(event: TableLazyLoadEvent) {
    let urlfiltros: string = '';
    // let prefix: string =''
    this.loading = true;

    if(this.idFilter)
      urlfiltros += '&id='+this.idFilter;
    if(this.tipoContaFilter && this.tipoContaFilter.id)
      urlfiltros += '&tipoConta.id='+ this.tipoContaFilter.id;
    if(this.vencimentoInicialFilter)
      urlfiltros += '&vencimentoInicial='+Util.dataStringBrToDateString(this.vencimentoInicialFilter);
    if(this.vencimentoFinalFilter)
      urlfiltros += '&vencimentoFinal='+Util.dataStringBrToDateString(this.vencimentoFinalFilter);
    if(this.emissaoInicialFilter)
      urlfiltros += '&emissaoInicial='+Util.dataStringBrToDateString(this.emissaoInicialFilter);
    if(this.emissaoFinalFilter)
      urlfiltros += '&emissaoFinal='+Util.dataStringBrToDateString(this.vencimentoFinalFilter);
    if(this.statusFilter && this.statusFilter.code)
      urlfiltros += '&contaStatus='+this.statusFilter.code;

    event.rows = (event.rows ? event.rows : this.pageSize);
    event.sortField = (event.sortField ? event.sortField : 'dataPagamento');

    const url: string = 'conta/page?page=' + (event.first! / this.pageSize)
      + '&size=' + event.rows
      + '&sort=' + event.sortField + ',' + (event.sortOrder == 1 ? 'asc' : 'desc')
      + urlfiltros;

    this.defaultService.get(url).subscribe({
      next: resultado => {
        this.contas = [];
        this.totalElements = 0;
        if(resultado) {
          this.contas = resultado.content;
          this.totalElements = resultado.totalElements;
          this.defaultService.get('conta/valorTotal?'+urlfiltros).subscribe({
            next: resultado => {
              this.valorTotal = resultado;
            }
          })
        }
        this.messageService.add({
          severity: 'info',
          summary: 'Info',
          detail: `${this.totalElements} contas carregadas`,
          life: 3000
        });

      },
      error: error => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Erro ao carregar as contas',
          life: 3000
        });
      },
      complete: () => {
        this.loading = false;
      }
    });
  }


  // getStatusTheme(conta:Conta){
  //   switch (conta.status){
  //     case 'Em Aberto': return 'success'; break;
  //     case 'Pago': return 'info'; break;
  //     default: return 'secondary';
  //   }
  // }

  // rowClass(conta: Conta) {
  //   return { '!bg-primary !text-primary-contrast': !conta.tipoConta.ativo };
  // }

  // rowStyle(conta: Conta) {
  //   if (!conta.tipoConta.ativo) {
  //     return { textDecoration: 'line-through', fontStyle: 'italic' };
  //   }
  //   return null;
  // }
}
