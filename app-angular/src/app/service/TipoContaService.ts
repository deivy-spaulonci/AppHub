import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DefaultService } from './default.service';
import { TipoConta } from '@model/tipo-conta';

@Injectable({
  providedIn: 'root'
})
export class TipoContaService {

  ROOT: string = "tipo-conta";

  constructor(private defaultService: DefaultService) {}

  getTiposConta(): Observable<TipoConta[]> {
    return this.defaultService.get(this.ROOT+'?sort=nome,asc');
  }

  update(obj:any): Observable<TipoConta> {
    return this.defaultService.update(obj, this.ROOT)
  }
}
