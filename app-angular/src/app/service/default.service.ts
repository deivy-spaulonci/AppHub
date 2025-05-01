import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DefaultService {
  ROOT:string="http://localhost:8081/api/v1/";

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  get(api:string): Observable<any> {
    console.log(`${this.ROOT}${api}`);
    return this.http.get<any>(`${this.ROOT}${api}`, this.httpOptions);
  }

  save(obj: any, api: string): Observable<any> {
    return this.http.post<any>(this.ROOT + api, obj, this.httpOptions);
  }

  update(obj: any, api: string): Observable<any> {
    return this.http.put<any>(this.ROOT + api, obj, this.httpOptions);
  }

  delete(obj: any | number, api: string): Observable<any> {
    const id = typeof obj === 'number' ? obj : obj.id;
    const url = `${this.ROOT + api}/${id}`;
    return this.http.delete<any>(url, this.httpOptions);
  }


}
