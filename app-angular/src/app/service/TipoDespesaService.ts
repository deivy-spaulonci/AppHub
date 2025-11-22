import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';
import {TipoDespesa} from '@model/tipo-despesa';

@Injectable({
  providedIn: 'root'
})

export class TipoDespesaService{
  ROOT:string="tipo-despesa"

  constructor(private defaltService:DefaultService) {
  }

  getTiposDespesa(): Observable<TipoDespesa[]>{
    return this.defaltService.get(this.ROOT);
  }
}
