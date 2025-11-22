import {Component, OnInit, ViewChild} from '@angular/core';
import {Card} from 'primeng/card';
import {Toast} from 'primeng/toast';
import {ConfirmationService, MessageService} from 'primeng/api';
import {Router} from '@angular/router';
import {Conta} from '@model/conta';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {Message} from 'primeng/message';
import {TipoConta} from '@model/tipo-conta';
import {FormaPagamento} from '@model/forma-pagamento';
import {Table, TableLazyLoadEvent, TableModule} from 'primeng/table';
import {Util} from '@util/util';
import {Button, ButtonDirective} from 'primeng/button';
import {CurrencyPipe, DatePipe, NgIf, NgStyle} from '@angular/common';
import {Tooltip} from 'primeng/tooltip';
import {InputMask} from 'primeng/inputmask';
import {Select} from 'primeng/select';
import {FormsModule} from '@angular/forms';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {Dialog} from 'primeng/dialog';
import {ComboDefaultComponent} from '@shared/components/combo-default/combo-default.component';
import {InputDateComponent} from '@shared/components/input-date/input-date.component';
import {InputMoneyComponent} from '@shared/components/input-money/input-money.component';
import {LoadingModalComponent} from '@shared/loading-modal/loading-modal.component';
import {Drawer} from 'primeng/drawer';
import {InputText} from 'primeng/inputtext';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {FileSizePipe} from '@pipe/file-size.pipe';
import {Ripple} from 'primeng/ripple';
import {ContaService} from '@service/ContaService';
import {TipoContaService} from '@service/TipoContaService';
import {FormaPagamentoService} from '@service/FormaPagamentoService';
import {DocumentoService} from '@service/DocumentoService';

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
    Dialog,
    ComboDefaultComponent,
    InputDateComponent,
    InputMoneyComponent,
    LoadingModalComponent,
    Drawer,
    InputText,
    ButtonDirective,
    FileSizePipe,
    Ripple,
    NgStyle
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
  pageSize = 10;
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
  setContaPagaVisible:boolean=false;
  contaPagaValor:string='0,00';
  contaPagaFormaPgto:FormaPagamento=new FormaPagamento();
  contaPagaDataPgto:string='';

  //documentos
  docsVisible=false;
  files!: String[];
  selectedFile!: any;
  conteudoBase64: SafeResourceUrl | null = null;
  basePath:string='/media/d31vy/hd01/Pagamentos/Pagamentos_PF/2010/042010/';

  constructor(private confirmationService: ConfirmationService,
              private contaService: ContaService,
              private tipoContaService: TipoContaService,
              private formaPagamentoService: FormaPagamentoService,
              private documentoService: DocumentoService,
              private router: Router,
              private sanitizer: DomSanitizer,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.status = [
      { name: 'Em Aberto', code: 'ABERTO' },
      { name: 'Pago', code: 'PAGO' },
      { name: 'Atrasado', code: 'ATRASADO' },
      { name: 'Vencimento Hoje', code: 'HOJE' },
    ];

    this.tipoContaService.getTiposConta().subscribe({
      next: (res: TipoConta[]) => this.tiposConta = res,
      error: () => Util.showMsgErro(this.messageService, 'Erro ao consulta de tipo contas')
    });
    this.formaPagamentoService.getFormasPagamento().subscribe({
      next: (res: FormaPagamento[]) => this.formasPgto = res,
      error: () => Util.showMsgErro(this.messageService, 'Erro ao consultade forma de pagamento'),
    });
  }

  editConta(param:number){
    this.router.navigate(['/conta-form'],{ queryParams: { id: param} })
  }

  setPago(conta:Conta){
    this.contaSelecinada = conta;
    this.contaPagaValor = Util.formatFloatToReal(conta.valor.toFixed(2).toString());
    this.contaPagaDataPgto = Util.dateToDataBR(conta.vencimento);
    this.setContaPagaVisible=true;
  }

  pagarConta(){
    if(this.contaSelecinada){
      if(!Util.dataIsValida(this.contaPagaDataPgto))
        Util.showMsgErro(this.messageService, 'Data pagamento inválida!');
      else if(['0,00','0','', null, undefined].indexOf(this.contaPagaValor.trim()) == 0)
        Util.showMsgErro(this.messageService, 'Valor inválido!');
      else if(!this.contaPagaFormaPgto || !this.contaPagaFormaPgto.id)
        Util.showMsgErro(this.messageService, 'Forma de Pagamento inválida!');
      else{
        this.loading = true;
        let vlPg: number = Util.formatMoedaToFloat(Util.formatFloatToReal(this.contaPagaValor));
        if(vlPg > this.contaSelecinada.valor)
          this.contaSelecinada.multa = Math.round((vlPg - this.contaSelecinada.valor) * 100)/100;
        if(vlPg < this.contaSelecinada.valor)
          this.contaSelecinada.desconto = Math.round((this.contaSelecinada.valor - vlPg) * 100)/100;

        this.contaSelecinada.dataPagamento = Util.dataBRtoDataIso(this.contaPagaDataPgto);
        this.contaSelecinada.formaPagamento = this.contaPagaFormaPgto;

        this.contaService.create(this.contaSelecinada).subscribe({
          next: () => Util.showMsgSuccess(this.messageService, 'Conta salva!'),
          error: () => Util.showMsgErro(this.messageService, 'Erro ao salvar o conta'),
          complete: () => {
            this.loading = false;
            this.setContaPagaVisible=false;
            this.table?._filter();
          }
        });
      }
    }else
      Util.showMsgErro(this.messageService, 'Sem conta selecionada')
  }

  delConta(event: Event, id:number){
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: "Confirma a exclusão da conta",
      closable: true,
      icon:"pi pi-exclamation-triangle",
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary',outlined: true,},
      acceptButtonProps: {label: 'Excluir',severity: 'warn',},
      accept: () => {
        this.loading = true;
        this.contaService.delConta(id).subscribe({
          next: () =>{
            Util.showMsgSuccess(this.messageService, 'Conta excluída!');
            this.table?._filter();
          },
          error: () => Util.showMsgErro(this.messageService, 'erro ao exlcuir conta'),
          complete: () => this.loading=false
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

  checkData(value:string){
    return (value && ['__/__/____','____/__/__','____-__-__'].indexOf(value)==-1)
  }

  loadData(event: TableLazyLoadEvent) {
    let urlfiltros: string = '';
    // let prefix: string =''
    this.loading = true;

    if(this.idFilter)
      urlfiltros += '&id='+this.idFilter;
    if(this.tipoContaFilter && this.tipoContaFilter.id)
      urlfiltros += '&idTipoConta='+ this.tipoContaFilter.id;
    if(this.checkData(this.vencimentoInicialFilter))
      urlfiltros += '&vencimentoInicial='+Util.dataStringBrToDateString(this.vencimentoInicialFilter);
    if(this.checkData(this.vencimentoFinalFilter))
      urlfiltros += '&vencimentoFinal='+Util.dataStringBrToDateString(this.vencimentoFinalFilter);
    if(this.checkData(this.emissaoInicialFilter))
      urlfiltros += '&emissaoInicial='+Util.dataStringBrToDateString(this.emissaoInicialFilter);
    if(this.checkData(this.emissaoFinalFilter))
      urlfiltros += '&emissaoFinal='+Util.dataStringBrToDateString(this.vencimentoFinalFilter);
    if(this.statusFilter && this.statusFilter.code)
      urlfiltros += '&contaStatus='+this.statusFilter.code;

    event.rows = (event.rows ? event.rows : this.pageSize);
    event.sortField = (event.sortField ? event.sortField : 'vencimento');

    const url: string = '?page=' + (event.first! / event.rows)
      + '&size=' + event.rows
      + '&sort=' + event.sortField + ',' + (event.sortOrder == 1 ? 'asc' : 'desc')
      + urlfiltros;

    this.contaService.getContasPage(url).subscribe({
      next: (res:any) => {
        this.contas = [];
        this.totalElements = 0;
        if(res) {
          this.contas = res.content;
          this.totalElements = res.totalElements;
          this.contaService.getValorTotal('?'+urlfiltros).subscribe({
            next: (res:any) => this.valorTotal = res
          })
        }
        Util.showMsgInfo(this.messageService, `${this.totalElements} contas carregadas`);
      },
      error: () => Util.showMsgErro(this.messageService,'Erro ao carregar as contas'),
      complete: () => this.loading = false
    });
  }

  carregarDocs(){
    this.documentoService.getDocumentoByPath(this.basePath).subscribe({
      next: (res: String[]) => this.files = res,
      error: () => Util.showMsgErro(this.messageService, 'consulta de documentos'),
      complete: () => this.loading=false,
    });
  }

  aoSelecionarDocs(event: any) {
    this.loading = true;
    let url = '?path='+this.basePath+this.selectedFile.nome;
    this.documentoService.getDocumentoBase64(url).subscribe({
      next: (res:any) => {
        const dataUrl = `data:${res.mimeType};base64,${res.base64}`;
        this.conteudoBase64 = this.sanitizer.bypassSecurityTrustResourceUrl(dataUrl);
      },
      error: (err: any) => Util.showMsgErro(this.messageService, err),
      complete: () => this.loading = false
    });
  }

  delDoc(event:any, file:any){
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      message: "Confirma a exclusão de documento",
      closable: true,
      icon:"pi pi-exclamation-triangle",
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary',outlined: true,},
      acceptButtonProps: {label: 'Excluir',severity: 'warn',},
      accept: () => {
        // this.loading = true;
        // let url = 'documentos/delete?path='+this.basePath+file.nome;
        // this.defaultService.delFile(url).subscribe({
        //   next: res => this.messageService.add({severity: 'success', summary: 'Success', detail: res}),
        //   error: err => this.messageService.add({severity: 'error', summary: 'Error', detail: err}),
        //   complete: () => {
        //     this.carregarDocs();
        //   }
        // });
      },
      reject: () => {},
    });
  }

  rowStyle(conta: Conta) {
    return conta.tipoConta.ativo ? {'text-decoration': 'none'}:{'text-decoration': 'line-through', 'color':'red'};
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
  protected readonly event = event;
}
