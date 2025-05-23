import {Component, OnInit} from '@angular/core';
import {CardModule} from 'primeng/card';
import {InputMask, InputMaskModule} from 'primeng/inputmask';
import {InputGroupAddon, InputGroupAddonModule} from 'primeng/inputgroupaddon';
import {InputGroupModule} from 'primeng/inputgroup';
import {Button} from 'primeng/button';
import {SelectButton} from 'primeng/selectbutton';
import {FormsModule} from '@angular/forms';
import {InputText} from 'primeng/inputtext';
import {Select} from 'primeng/select';
import {DefaultService} from '../../../service/default.service';
import {MessageService} from 'primeng/api';
import {Toast} from 'primeng/toast';
import {JsonPipe, NgIf} from '@angular/common';
import {BlockUI} from 'primeng/blockui';
import {ProgressSpinner} from 'primeng/progressspinner';
import {AutoComplete, AutoCompleteCompleteEvent} from 'primeng/autocomplete';
import {Fornecedor} from '../../../model/fornecedor';
import {Util} from '../../../util/util';
import {Panel} from "primeng/panel";
import {Router} from '@angular/router';

@Component({
  selector: 'app-fornecedor-form',
    imports: [CardModule, InputMask, InputMaskModule, InputGroupAddon, InputGroupAddonModule, InputGroupModule, Button, SelectButton, FormsModule, InputText, Select, Toast, JsonPipe, BlockUI, ProgressSpinner, AutoComplete, NgIf, Panel],
  templateUrl: './fornecedor-form.component.html',
  standalone: true,
  styleUrl: './fornecedor-form.component.css',
  providers: [MessageService],
})
export class FornecedorFormComponent implements OnInit {
  stateOptions: any[] = [
    {label: 'PJ', value: 'pj', icon: 'pi pi-building'},
    {label: 'PF', value: 'pf', icon: 'pi pi-user'}
  ];
  tipoPessoa: string = 'pj';
  estados: any[] = [];
  estadoSelect: any = {};
  cidades: any[] = [];
  cidadeSelect: any = {};
  cnpj: string = '';
  cpf: string = '';
  nome: string = '';
  razaoSocial: string = '';
  loading: boolean = false;
  maskCnpj:string='99.999.999/9999-99';

  constructor(private router: Router,
              private defaultService: DefaultService,
              private messageService: MessageService) {
  }

  ngOnInit(): void {
    this.defaultService.get('cidade/estados').subscribe({
      next: res => {
        this.estados = [];
        for (var item of res) {
          this.estados.push({name: item, code: item})
        }
      },
      error: error => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de estados'});
      },
      complete: () => {
      }
    });
  }

  searchCities(cidadeSelect: any) {
    if (this.estadoSelect && this.estadoSelect.name) {
      this.defaultService.get('cidade/' + this.estadoSelect.name).subscribe({
        next: res => {
          this.cidades = [];
          for (var cidade of res) {
            this.cidades.push({nome: cidade.nome, id: cidade.id, ibgeCod: cidade.ibgeCod})
          }
          if (cidadeSelect)
            this.cidadeSelect = cidadeSelect;
        },
        error: error => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de cidades'});
        },
        complete: () => {
        }
      });
    } else {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'erro ao consultar cidades'});
    }
  }

  changePerson() {
    this.cidadeSelect = {};
    this.estadoSelect = {name: 'SP', code: 'SP'};
    this.searchCities(null);
    this.cnpj = "";
    this.cpf = "";
  }

  searchCNPJ() {
    if (['', null, undefined].indexOf(this.cnpj) != 0) {
      this.loading = true;
      this.defaultService.get('fornecedor/consultaCnpj/' + this.cnpj.replace(/[^0-9]+/g, ''))
        .subscribe({
          next: async res => {
            this.estadoSelect = this.estados.filter(est => est.name == res.cidade.uf)[0];
            this.searchCities(res.cidade);
            this.nome = Util.capitalizeSentence(res.nome);
            this.razaoSocial = Util.capitalizeSentence(res.razaoSocial);
          },
          error: error => {
            this.messageService.add({severity: 'error', summary: 'Error', detail: error.error});
          },
          complete: () => {
            this.loading = false;
          }
        });
    }
  }

  save() {
    console.log('inciando validação')
    if (['', null, undefined].indexOf(this.nome.trim()) == 0)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Nome inválido!'});
    if (['', null, undefined].indexOf(this.razaoSocial.trim()) == 0)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Razao Social inválido!'});
    if (!this.cidadeSelect || !this.cidadeSelect.id)
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Cidade inválido!'});
    else {
      this.loading = true;
      var fornecedor: Fornecedor = new Fornecedor();
      fornecedor.nome = this.nome;
      fornecedor.razaoSocial = this.razaoSocial;
      if (this.tipoPessoa == 'pj') {
        fornecedor.cpf = '';
        fornecedor.cnpj = this.cnpj.replace(/[^0-9]+/g, '');
      } else {
        fornecedor.cnpj = '';
        fornecedor.cpf = this.cpf;
      }
      fornecedor.cidade = this.cidadeSelect;

      this.defaultService.save(fornecedor, 'fornecedor').subscribe({
        next: res => {
          this.messageService.add({severity: 'success', summary: 'Success', detail: 'Fornecedor salvo!'});
        },
        error: error => {
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'erro ao salvar o fornecedor'});
        },
        complete: () => {
          this.loading = false;
          this.cnpj = '';
          this.cpf = '';
          this.nome = '';
          this.razaoSocial = '';
        }
      });

    }
  }
}
