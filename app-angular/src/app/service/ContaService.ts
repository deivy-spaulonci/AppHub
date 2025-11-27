import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';
import {Conta} from '@model/conta';

@Injectable({
  providedIn: 'root'
})

export class ContaService{
  ROOT:string="conta";

  constructor(private defaltService:DefaultService) {
  }

  getContasPage(url:string): Observable<any>{
    return this.defaltService.get(this.ROOT+'/page'+url);
  }

  getContas(url:string): Observable<any>{
    return this.defaltService.get(this.ROOT+url);
  }

  getContasById(id:number): Observable<Conta>{
    return this.defaltService.get(this.ROOT+'/'+id);
  }

  getValorTotal(url:string): Observable<number>{
    return this.defaltService.get(this.ROOT+'/valorTotal'+url);
  }

  getGastoAno(ano:number):Observable<[]>{
    return this.defaltService.get(this.ROOT+'/gastoAno/'+ano);
  }

  getGastoPorAno():Observable<[]>{
    return this.defaltService.get(this.ROOT+'/gasto-total-anual');
  }

  update(conta:Conta): Observable<Conta>{
    return this.defaltService.update(conta, this.ROOT)
  }

  create(conta:Conta): Observable<Conta>{
    return this.defaltService.save(conta, this.ROOT)
  }

  delConta(id:number):Observable<any>{
    return this.defaltService.delete(id,this.ROOT)
  }
}
