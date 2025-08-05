import {ChangeDetectorRef, Component, effect, inject, OnInit, PLATFORM_ID} from '@angular/core';
import {CardModule} from 'primeng/card';
import {MessageModule} from 'primeng/message';
import {DefaultService} from '../../service/default.service';
import {FormsModule} from '@angular/forms';
import {CurrencyPipe, DatePipe, isPlatformBrowser, NgForOf, NgIf} from '@angular/common';
import {ChartModule, UIChart} from 'primeng/chart';
import {forkJoin} from 'rxjs';
import {DataView} from 'primeng/dataview';

@Component({
  selector: 'app-home',
  imports: [CardModule, MessageModule, FormsModule, CurrencyPipe, UIChart, ChartModule, DataView, NgForOf, DatePipe, NgIf],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  providers: [CurrencyPipe]
})
export class HomeComponent implements OnInit{

  readonly VLR_TOTAL_CONTA_URL = 'conta/valorTotal';
  readonly CT_PAGO_URL = '?contaStatus=PAGO';
  readonly CT_HOJE_URL = '?contaStatus=HOJE';
  readonly CT_ABERTO_URL = '?contaStatus=ABERTO';
  readonly CT_ATRASADO_URL = '?contaStatus=ATRASADO';
  readonly CT_STATUS_URL = 'conta?contaStatus='

  readonly VLR_TOTAL_DESPESA_URL= 'despesa/valorTotal';
  readonly DESPESA_POR_TIPO_URL= 'despesa/despesaPorTipo';

  totalDespesa$!: number;
  totalConta$!: number;
  totalContaAvencer$!:number;
  totalContaAberto$!:number;
  totalContaHoje$!:number;
  totalContaAtrasado$!:number;

  dataContas: any;
  dataDespesas: any;

  optionsContas: any;
  optionsDespesa: any;

  platformId = inject(PLATFORM_ID);

  contasEmAberto:any=[];
  // configService = inject(AppConfigService);
  //
  // designerService = inject(DesignerService);

  themeEffect = effect(() => {
    // if (this.configService.transitionComplete()) {
    //   if (this.designerService.preset()) {
    //     this.initChart();
    //   }
    // }
  });

  constructor(private defaultService: DefaultService,
              private currencyPipe: CurrencyPipe,
              private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {

    this.defaultService.get(this.VLR_TOTAL_DESPESA_URL).subscribe(res =>{
      this.totalDespesa$ = res;
    });

    this.defaultService.get(this.VLR_TOTAL_CONTA_URL).subscribe(res =>{
      this.totalConta$ = res;
    });

    forkJoin({
      emaberto:this.defaultService.get('conta'+this.CT_ABERTO_URL),
      atrasado:this.defaultService.get('conta'+this.CT_ATRASADO_URL),
      hoje:this.defaultService.get('conta'+this.CT_HOJE_URL)
    }).subscribe({
      next: ({emaberto,atrasado,hoje}) => {
        this.contasEmAberto = (emaberto as []).concat(atrasado as []);
      }
    });

    // this.defaultService.get('conta'+this.CT_ABERTO_URL).subscribe(res =>{
    //   this.contasEmAberto = res;
    //   this.defaultService.get('conta'+this.CT_ATRASADO_URL).subscribe(res =>{
    //     this.contasEmAberto.concat(res);
    //     this.defaultService.get('conta'+this.CT_HOJE_URL).subscribe(res =>{
    //       this.contasEmAberto.concat(res);
    //     })
    //   })
    //
    // });


    forkJoin({
      pago:this.defaultService.get(this.VLR_TOTAL_CONTA_URL+this.CT_PAGO_URL),
      hoje:this.defaultService.get(this.VLR_TOTAL_CONTA_URL+this.CT_HOJE_URL),
      aberto:this.defaultService.get(this.VLR_TOTAL_CONTA_URL+this.CT_ABERTO_URL),
      atrasado:this.defaultService.get(this.VLR_TOTAL_CONTA_URL+this.CT_ATRASADO_URL),
    }).subscribe({
      next: ({pago,hoje,aberto,atrasado}) => {
        this.totalContaAvencer$ = pago;
        this.totalContaHoje$ = hoje;
        this.totalContaAberto$ = aberto;
        this.totalContaAtrasado$ = atrasado;
        this.initChartContas();
      },
      error: erro => {
        console.error('Falhou alguma chamada', erro);
      }
    });

    this.defaultService.get(this.DESPESA_POR_TIPO_URL).subscribe({
      next: (res) => {
        this.dataDespesas = res;
        // console.log(this.dataDespesas);
        this.initChartDespesas();
      },
      error: erro => {
        console.error('Falhou alguma chamada', erro);
      }

    })
  }

  getStatus(status:string){
    this.defaultService.get(this.CT_STATUS_URL+status).subscribe(res =>{
      this.totalContaAvencer$ = res;
    });
  }

  getMoneyFormat(valor:number){
    return valor ? this.currencyPipe.transform(valor, 'BRL', 'symbol', '1.2-2') : '0,00';
  }
  getPercentFormat(valor:number){
    return (valor ? ((valor/this.totalConta$)*100).toFixed(2) : '0');
  }

  initChartDespesas(){
    if (isPlatformBrowser(this.platformId)) {
      const documentStyle = getComputedStyle(document.documentElement);
      const textColor = documentStyle.getPropertyValue('--text-color');

      const nomesTiposDespesa = this.dataDespesas.map((d: { tipoDespesa: { nome: any; }; }) => d.tipoDespesa.nome);
      const valorTiposDespesa = this.dataDespesas.map((d: { subTotal: any; })  => d.subTotal);

      this.dataDespesas = {
        labels: nomesTiposDespesa,
        datasets: [
          {
            label: 'Tipos de Despesa',
            backgroundColor: documentStyle.getPropertyValue('--p-blue-500'),
            borderColor: documentStyle.getPropertyValue('--p-blue-500'),
            data: valorTiposDespesa
          }
        ]
      };

      this.optionsDespesa = {
        indexAxis: 'y',
        maintainAspectRatio: false,
        aspectRatio: 0.8,
        plugins: {
          legend: {
            labels: {
              color: textColor
            }
          }
        }
      };
      this.cd.markForCheck()

    }
  }

  initChartContas() {
    if (isPlatformBrowser(this.platformId)) {
      const documentStyle = getComputedStyle(document.documentElement);
      const textColor = documentStyle.getPropertyValue('--text-color');

      this.dataContas = {
        labels: [
          `Contas Pagas - ${this.getMoneyFormat(this.totalContaAvencer$)} [ ${this.getPercentFormat(this.totalContaAvencer$)}% ]`,
          `Vencem Hoje - ${this.getMoneyFormat(this.totalContaHoje$)} [ ${this.getPercentFormat(this.totalContaHoje$)}% ]`,
          `Estão em Aberto - ${this.getMoneyFormat(this.totalContaAberto$)} [ ${this.getPercentFormat(this.totalContaAberto$)}% ]`,
          `Em atraso - ${this.getMoneyFormat(this.totalContaAtrasado$)} [ ${this.getPercentFormat(this.totalContaAtrasado$)}% ]`
        ],
        datasets: [
          {
            data: [
              this.getPercentFormat(this.totalContaAvencer$),
              this.getPercentFormat(this.totalContaHoje$),
              this.getPercentFormat(this.totalContaAberto$),
              this.getPercentFormat(this.totalContaAtrasado$),
            ],
            backgroundColor: [
              documentStyle.getPropertyValue('--p-blue-500'),
              documentStyle.getPropertyValue('--p-yellow-500'),
              documentStyle.getPropertyValue('--p-green-500'),
              documentStyle.getPropertyValue('--p-red-400'),
            ],
            hoverBackgroundColor: [
              documentStyle.getPropertyValue('--p-blue-400'),
              documentStyle.getPropertyValue('--p-yellow-400'),
              documentStyle.getPropertyValue('--p-green-400'),
              documentStyle.getPropertyValue('--p-red-400'),
            ]
          }
        ]
      };

      this.optionsContas = {
        plugins: {
          legend: {
            labels: {
              usePointStyle: true,
              color: textColor
            }
          }
        }
      };
      this.cd.markForCheck()
    }
  }
}
