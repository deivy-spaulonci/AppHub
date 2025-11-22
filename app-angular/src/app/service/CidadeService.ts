import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';
import {Cidade} from '@model/cidade';

@Injectable({
  providedIn: 'root'
})

export class CidadeService{
  ROOT:string="cidade";
  constructor(private defaltService:DefaultService) {
  }

  getEstados(): Observable<[]>{
    return this.defaltService.get(this.ROOT+'/estados');
  }

  getCidadeByUf(uf:string): Observable<Cidade[]>{
    return this.defaltService.get(this.ROOT+'/'+uf);
  }
}
