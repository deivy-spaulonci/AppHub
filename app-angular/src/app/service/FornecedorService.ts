import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';
import {Fornecedor} from '@model/fornecedor';

@Injectable({
  providedIn: 'root'
})

export class FornecedorService{
  ROOT:string="fornecedor"
  constructor(private defaltService:DefaultService) {
  }

  getFornecedores(): Observable<Fornecedor[]>{
    return this.defaltService.get(this.ROOT);
  }

  getFornecedoresPage(url:string): Observable<any>{
    return this.defaltService.get(this.ROOT+'/page'+url);
  }

  getFornecedorBusca(busca:string): Observable<Fornecedor[]>{
    return this.defaltService.get(this.ROOT+'/find/'+busca);
  }

  update(fornecedor:Fornecedor): Observable<Fornecedor>{
    return this.defaltService.update(fornecedor, this.ROOT)
  }

  create(fornecedor:Fornecedor): Observable<Fornecedor>{
    return this.defaltService.save(fornecedor, this.ROOT)
  }

  buscaCNPJWeb(cnpj:string): Observable<Fornecedor>{
    return this.defaltService.get(this.ROOT+'/consultaCnpj/'+cnpj);
  }
}
