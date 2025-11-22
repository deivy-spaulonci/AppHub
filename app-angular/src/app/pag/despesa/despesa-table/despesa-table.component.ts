import {Component, OnInit, ViewChild} from '@angular/core';
import {Card} from 'primeng/card';
import {Despesa} from '@model/despesa';
import {ConfirmationService, MessageService} from 'primeng/api';
import {Table, TableLazyLoadEvent, TableModule} from 'primeng/table';
import {CurrencyPipe, DatePipe} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Button} from 'primeng/button';
import {InputGroupAddonModule} from 'primeng/inputgroupaddon';
import {InputGroupModule} from 'primeng/inputgroup';
import {Toast} from 'primeng/toast';
import {TipoDespesa} from '@model/tipo-despesa';
import {FormaPagamento} from '@model/forma-pagamento';
import {Fornecedor} from '@model/fornecedor';
import {Util} from '@util/util';
import {Tooltip} from 'primeng/tooltip';
import {Router} from '@angular/router';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {Message} from 'primeng/message';
import {ComboDefaultComponent} from '@shared/components/combo-default/combo-default.component';
import {InputDateComponent} from '@shared/components/input-date/input-date.component';
import {LoadingModalComponent} from '@shared/loading-modal/loading-modal.component';
import {TipoDespesaService} from '@service/TipoDespesaService';
import {FormaPagamentoService} from '@service/FormaPagamentoService';
import {FornecedorService} from '@service/FornecedorService';
import {DespesaService} from '@service/DespesaService';

@Component({
  selector: 'app-despesa-table',
  imports: [
    Card,
    TableModule,
    DatePipe,
    CurrencyPipe,
    ReactiveFormsModule,
    FormsModule,
    Button,
    InputGroupModule,
    InputGroupAddonModule,
    Toast,
    Tooltip,
    ConfirmDialogModule,
    Message,
    ComboDefaultComponent,
    InputDateComponent,
    LoadingModalComponent
  ],
  templateUrl: './despesa-table.component.html',
  providers: [MessageService,ConfirmationService],
  standalone: true,
  styleUrl: './despesa-table.component.css'
})
export class DespesaTableComponent implements OnInit{
  despesas:Despesa[]=[];
  totalElements = 0;
  sortField:string='dataPagamento';
  pageSize = 10;
  valorTotal:number=0;
  loading:boolean=false;
  despesaSelecinada!:Despesa;
  idFilter:string='';
  tipoDespesaFilter:TipoDespesa=new TipoDespesa;
  formaPgtoFilter:FormaPagamento=new FormaPagamento;
  fornecedorFilter:Fornecedor=new Fornecedor;
  dataInicialFilter:string='';
  dataFinalFilter:string='';
  tiposDespesa:TipoDespesa[]=[];
  formasPgto:FormaPagamento[]=[];
  fornecedores:Fornecedor[]=[];
  @ViewChild('dt') table?:Table;

  constructor(private confirmationService: ConfirmationService,
              private despesaService:DespesaService,
              private tipoDespesaService:TipoDespesaService,
              private formaPagamentoService:FormaPagamentoService,
              private fornecedorService:FornecedorService,
              private router: Router,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.tipoDespesaService.getTiposDespesa().subscribe({
      next: (res: TipoDespesa[]) => this.tiposDespesa = res,
      error: () => Util.showMsgErro(this.messageService,'consulta de tipo despesas'),
      complete: () => {}
    });
    this.formaPagamentoService.getFormasPagamento().subscribe({
      next: (res: FormaPagamento[]) => this.formasPgto = res,
      error: () => Util.showMsgErro(this.messageService,'consulta de formas de pagamento'),
      complete: () => {}
    });
    this.fornecedorService.getFornecedores().subscribe({
      next: (res: Fornecedor[]) =>{ this.fornecedores = res; },
      error: () => Util.showMsgErro(this.messageService,'consulta fornecedores'),
      complete: () => {}
    })
  }

  editDespesa(param:number){
    this.router.navigate(['/despesa-form'],{ queryParams: { id: param} })
  }

  delDespesa(event: Event, id:number){
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary',outlined: true,},
      acceptButtonProps: {label: 'Excluir',severity: 'warn',},
      accept: () => {
        this.loading=true;
        this.despesaService.delDespesa(id).subscribe({
          next: () =>{
            Util.showMsgSuccess(this.messageService,'Despesa excluída!');
            this.table?._filter();
          },
          error: () => Util.showMsgErro(this.messageService,'exlcuir despesa'),
          complete: () => this.loading=true
        })
      },
      reject: () => {},
    });
  }

  clearFilter(){
    this.idFilter='';
    this.tipoDespesaFilter=new TipoDespesa();
    this.fornecedorFilter=new Fornecedor();
    this.formaPgtoFilter=new FormaPagamento();
    this.dataInicialFilter='';
    this.dataFinalFilter='';
    this.table?._filter();
  }

  loadData(event: TableLazyLoadEvent) {
    let urlfiltros: string = '';
    this.loading = true;

    if(this.idFilter)
      urlfiltros += '&id='+this.idFilter;
    if(this.tipoDespesaFilter && this.tipoDespesaFilter.id)
      urlfiltros += '&idTipoDespesa='+ this.tipoDespesaFilter.id;
    if(this.fornecedorFilter && this.fornecedorFilter.id)
      urlfiltros += '&idFornecedor='+ this.fornecedorFilter.id;
    if(this.formaPgtoFilter && this.formaPgtoFilter.id)
      urlfiltros += '&idFormaPagamento='+this.formaPgtoFilter.id
    if(this.dataInicialFilter)
      urlfiltros += '&dataInicial='+Util.dataStringBrToDateString(this.dataInicialFilter);
    if(this.dataFinalFilter)
      urlfiltros += '&dataFinal='+Util.dataStringBrToDateString(this.dataFinalFilter);

    event.rows = (event.rows ? event.rows : this.pageSize);
    event.sortField = (event.sortField ? event.sortField : 'dataPagamento');

    const url: string = '?page=' + (event.first! / event.rows)
      + '&size=' + event.rows
      + '&sort=' + event.sortField + ',' + (event.sortOrder == 1 ? 'asc' : 'desc')
      + urlfiltros;

    this.despesaService.getDespesasPage(url).subscribe({
      next: (res:any) => {
        this.despesas = [];
        this.totalElements = 0;
        if(res) {
          this.despesas = res.content;
          this.totalElements = res.totalElements;
          this.despesaService.getValorTotal('?'+urlfiltros).subscribe({
            next: (res: any) => this.valorTotal = Number(res)
          })
        }
        Util.showMsgInfo(this.messageService, `${this.totalElements} despesas carregadas`);
      },
      error: () => Util.showMsgErro(this.messageService,'Erro ao carregar as despesas'),
      complete: () => this.loading = false
    });
  }
}
