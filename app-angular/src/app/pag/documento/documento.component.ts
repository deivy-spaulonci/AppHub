import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {DefaultService} from '../../service/default.service';
import {Button} from 'primeng/button';
import {Toast} from 'primeng/toast';
import {Toolbar} from 'primeng/toolbar';
import {InputText} from 'primeng/inputtext';
import {TableModule} from 'primeng/table';
import {NgIf} from '@angular/common';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-documento',
  imports: [
    Button,
    Toast,
    Toolbar,
    InputText,
    TableModule,
    NgIf,
    FormsModule
  ],
  templateUrl: './documento.component.html',
  standalone: true,
  styleUrl: './documento.component.css',
  providers: [MessageService],
})
export class DocumentoComponent implements OnInit{
  files!: String[];
  loading: boolean = false;
  selectedFile!: string;
  conteudoBase64: SafeResourceUrl | null = null;
  basePath:string='';

  constructor(private defaultService: DefaultService,
              private sanitizer: DomSanitizer,
              private messageService: MessageService,) {}

  ngOnInit() {
    this.carregar();
  }

  carregar(){
    this.defaultService.get('documentos/diretorio?path='+this.basePath).subscribe({
      next: res => {
        this.files = res;
      },
      error: error => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de documentos'});
      },
      complete: () => {
        this.loading=false;
      }
    });

  }

  aoSelecionar(event: any) {

    let url = 'documentos/base64?path='+this.basePath+this.selectedFile;
    this.defaultService.get(url).subscribe({
      next: res => {
        const dataUrl = `data:${res.mimeType};base64,${res.base64}`;
        this.conteudoBase64 = this.sanitizer.bypassSecurityTrustResourceUrl(dataUrl);
      },
      error: error => {
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de documentos'});
      },
      complete: () => {

      }
    });
  }



}
