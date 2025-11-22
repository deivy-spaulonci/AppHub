import {ChangeDetectorRef, Component, effect, inject, OnInit, PLATFORM_ID} from '@angular/core';
import {CardModule} from 'primeng/card';
import {MessageModule} from 'primeng/message';
import {FormsModule} from '@angular/forms';
import {CurrencyPipe, DatePipe, isPlatformBrowser, NgClass, NgStyle} from '@angular/common';
import {ChartModule, UIChart} from 'primeng/chart';
import {forkJoin} from 'rxjs';
import {TableModule} from 'primeng/table';
import {Select, SelectChangeEvent} from 'primeng/select';
import {DespesaService} from '@service/DespesaService';
import {ContaService} from '@service/ContaService';

@Component({
  selector: 'app-home',
  imports: [CardModule, MessageModule, FormsModule, CurrencyPipe, UIChart, ChartModule, DatePipe, TableModule, NgClass, NgStyle, Select],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,
  providers: [CurrencyPipe]
})
export class HomeComponent implements OnInit{

  readonly CT_PAGO_URL = '?contaStatus=PAGO';
  readonly CT_HOJE_URL = '?contaStatus=HOJE';
  readonly CT_ABERTO_URL = '?contaStatus=ABERTO';
  readonly CT_ATRASADO_URL = '?contaStatus=ATRASADO';

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

  dataGastos: any;
  optionsGastos: any;
  dataGastosDespesa: any;
  dataGastosConta: any;

  selectedYear: number = 2025;

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

  constructor(private despesaService: DespesaService,
              private contaService: ContaService,
              private currencyPipe: CurrencyPipe,
              private cd: ChangeDetectorRef) {
  }

  ngOnInit(): void {

    this.despesaService.getValorTotal('').subscribe({ next: (res: number) => this.totalDespesa$ = res});
    this.contaService.getValorTotal('').subscribe({ next: (res:number) => this.totalConta$ = res});

    forkJoin({
      emaberto:this.contaService.getContas(this.CT_ABERTO_URL),
      atrasado:this.contaService.getContas(this.CT_ATRASADO_URL),
      hoje:this.contaService.getContas(this.CT_HOJE_URL)
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
      pago:this.contaService.getValorTotal(this.CT_PAGO_URL),
      hoje:this.contaService.getValorTotal(this.CT_HOJE_URL),
      aberto:this.contaService.getValorTotal(this.CT_ABERTO_URL),
      atrasado:this.contaService.getValorTotal(this.CT_ATRASADO_URL),
    }).subscribe({
      next: ({pago,hoje,aberto,atrasado}) => {
        this.totalContaAvencer$ = pago;
        this.totalContaHoje$ = hoje;
        this.totalContaAberto$ = aberto;
        this.totalContaAtrasado$ = atrasado;
        this.initChartContas();
      },
      error: (error:any) => console.error(error)
    });

    this.despesaService.getDespesaByTipo().subscribe({
      next: (res: any) => {
        this.dataDespesas = res;
        this.initChartDespesas();
      },
      error: (error: any) => console.error(error)
    });
    this.loadGastos();
  }

  loadGastos(){
    forkJoin({
      gastoD:this.despesaService.getGastoAno(this.selectedYear),
      gastoC:this.contaService.getGastoAno(this.selectedYear),
    }).subscribe({
      next: ({gastoD,gastoC}) => {
        this.dataGastosDespesa = gastoD;
        this.dataGastosConta = gastoC;
        this.initChartGastos();
      },
      error: error => console.error(error)
    });
  }

  rowStyle(item:any) {
    switch (item.status) {
      case 'Atrasado': return { color: 'var(--p-red-500)', backgroundColor: 'var(--p-red-50)' }; break;
      case 'Em Aberto': return { color: 'var(--p-green-500)', backgroundColor: 'var(--p-green-50)' }; break;
      case 'Vencimento Hoje': return { color: 'var(--p-yellow-500)', backgroundColor: 'var(--p-yellow-50)' }; break;
      default: return {};
    }
  }

  rowClassIcon(item:any) {
    switch (item.status) {
      case 'Atrasado': return 'pi pi-exclamation-circle'; break;
      case 'Em Aberto': return 'pi pi-calendar-clock'; break;
      case 'Vencimento Hoje': return 'pi pi-exclamation-circle'; break;
      default: return '';
    }
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

      const nomesTiposDespesa = this.dataDespesas.map((d: { nomeTipoDespesa: { nomeTipoDespesa: any; }; }) => d.nomeTipoDespesa);
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


  initChartGastos() {
    if (isPlatformBrowser(this.platformId)) {

      const documentStyle = getComputedStyle(document.documentElement);
      const textColor = documentStyle.getPropertyValue('--p-text-color');
      const textColorSecondary = documentStyle.getPropertyValue('--p-text-muted-color');
      const surfaceBorder = documentStyle.getPropertyValue('--p-content-border-color');

      this.dataGastos = {
        labels: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        datasets: [
          {
            label: 'Despesas',
            data: this.dataGastosDespesa,
            fill: false,
            borderColor: documentStyle.getPropertyValue('--p-cyan-500'),
            tension: 0.4
          },
          {
            label: 'Contas',
            data: this.dataGastosConta,
            fill: false,
            borderColor: documentStyle.getPropertyValue('--p-green-500'),
            tension: 0.4
          }
        ]
      };

      this.optionsGastos = {
        maintainAspectRatio: false,
        aspectRatio: 0.6,
        plugins: {
          legend: {
            labels: {
              color: textColor
            }
          }
        },
        scales: {
          x: {
            ticks: {
              color: textColorSecondary
            },
            grid: {
              color: surfaceBorder,
              drawBorder: false
            }
          },
          y: {
            ticks: {
              color: textColorSecondary
            },
            grid: {
              color: surfaceBorder,
              drawBorder: false
            }
          }
        }
      };
      this.cd.markForCheck()
    }
  }

  aoAnoSelecionado($event: SelectChangeEvent) {
    this.loadGastos();
  }
}
