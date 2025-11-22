import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';
import {Despesa} from '@model/despesa';

@Injectable({
  providedIn: 'root'
})

export class DespesaService{
  ROOT:string="despesa";

  constructor(private defaltService:DefaultService) {
  }

  getDespesasPage(url:string): Observable<any>{
    return this.defaltService.get(this.ROOT+'/page'+url);
  }

  getDespesaById(id:number): Observable<Despesa>{
    return this.defaltService.get(this.ROOT+'/'+id);
  }

  getValorTotal(url:string): Observable<number>{
    return this.defaltService.get(this.ROOT+'/valorTotal'+url);
  }

  getDespesaByTipo():Observable<any>{
    return this.defaltService.get(this.ROOT+'/despesaPorTipo');
  }

  getGastoAno(ano:number):Observable<[]>{
    return this.defaltService.get(this.ROOT+'/gastoAno/'+ano);
  }

  delDespesa(id:number):Observable<any>{
    return this.defaltService.delete(id,this.ROOT)
  }

  update(despesa:Despesa): Observable<Despesa>{
    return this.defaltService.update(despesa, this.ROOT)
  }

  create(despesa:Despesa): Observable<Despesa>{
    return this.defaltService.save(despesa, this.ROOT)
  }
}
