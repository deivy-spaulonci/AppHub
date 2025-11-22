import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class DocumentoService {
  ROOT: string = "documentos";

  constructor(private defaultService:DefaultService) {
  }

  getDocumentoByPath(path:string):Observable<any>{
    return this.defaultService.get(this.ROOT+'/diretorio?path='+path);
  }

  getDocumentoBase64(path:string):Observable<any>{
    return this.defaultService.get(this.ROOT+'/base64'+path);
  }

  renFile(obj: any): Observable<any> {
    return this.defaultService.updateTextToJson(this.ROOT+'/rename', obj);
  }

  delFiles(obj:any): Observable<any> {
    return this.defaultService.postTextToJson(obj, this.ROOT+'/delete');
  }
}
