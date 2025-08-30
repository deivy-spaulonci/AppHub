import {Component, OnInit} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';
import {DefaultService} from '../../service/default.service';
import {Button} from 'primeng/button';
import {Toast} from 'primeng/toast';
import {Toolbar} from 'primeng/toolbar';
import {InputText} from 'primeng/inputtext';
import {TableModule} from 'primeng/table';
import {NgForOf, NgIf} from '@angular/common';
import {DomSanitizer} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {LoadingModalComponent} from '../../shared/loading-modal/loading-modal.component';
import {ConfirmDialog} from 'primeng/confirmdialog';
import {Dialog} from 'primeng/dialog';
import {ToggleButton} from 'primeng/togglebutton';
import {Divider} from 'primeng/divider';

@Component({
  selector: 'app-documento',
  imports: [
    Button,
    Toast,
    Toolbar,
    InputText,
    TableModule,
    FormsModule,
    LoadingModalComponent,
    NgForOf,
    ConfirmDialog,
    Dialog,
    NgIf,
    ToggleButton,
    Divider,

  ],
  templateUrl: './documento.component.html',
  standalone: true,
  styleUrl: './documento.component.css',
  providers: [ConfirmationService,MessageService],
})
export class DocumentoComponent implements OnInit{
  files!: any[];
  loading: boolean = false;
  selectedFile!: any;
  basePath:string='/media/d31vy/hd01/Pagamentos/Pagamentos_PF/2019/01-2019/';
  popupRenShow= false;
  newNameFile:string='';
  filesSelected:string[]=[];

  constructor(private confirmationService: ConfirmationService,
              private defaultService: DefaultService,
              private sanitizer: DomSanitizer,
              private messageService: MessageService,) {}

  ngOnInit() {

  }

  carregar(){
    this.loading=true;
    this.defaultService.get('documentos/diretorio?path='+this.basePath).subscribe({
      next: res => this.files = res,
      error: error => this.messageService.add({severity: 'error', summary: 'Error', detail: 'consulta de documentos'}),
      complete: () => this.loading=false
    });
  }

  initRename(file:any) {
    this.selectedFile = file;
    this.newNameFile = file.nome;
    this.popupRenShow = true;
  }

  rename(evt:Event){
    if (['', null, undefined].indexOf(this.newNameFile.trim())){
      const pr = {
        oldPath: this.basePath+this.selectedFile.nome,
        newPath: this.basePath+this.newNameFile
      }

      this.defaultService.renFile(pr,'documentos/rename').subscribe({
         next: (res) => this.messageService.add({ severity: 'success', summary: 'Success', detail: res }),
         error: (err) => this.messageService.add({severity: 'error', summary: 'Error', detail: err}),
         complete: () => {
           this.loading = false;
           this.popupRenShow = false;
           this.carregar();
         }
      });
    }
  }

  // aoSelecionar(event: any) {
  //   this.loading = true;
  //   let url = 'documentos/base64?path='+this.basePath+this.selectedFile.nome;
  //   this.defaultService.get(url).subscribe({
  //     next: res => {
  //       const dataUrl = `data:${res.mimeType};base64,${res.base64}`;
  //       this.conteudoBase64 = this.sanitizer.bypassSecurityTrustResourceUrl(dataUrl);
  //     },
  //     error: err => this.messageService.add({severity: 'error', summary: 'Error', detail: err}),
  //     complete: () => {
  //       this.loading = false;
  //     }
  //   });
  // }

  getPdf(base64:string, ext:string){
    //const url = this.sanitizer.bypassSecurityTrustResourceUrl(this.defaultService.ROOT+'documentos/file?path='+this.basePath+nomeArquivo);
    //return url;
    const base64String = (ext=='pdf' ? `data:application/pdf;base64,${base64}` : `data:image/png;base64,${base64}`);
    return this.sanitizer.bypassSecurityTrustResourceUrl(base64String);
  }

  delDoc(evt:Event, file:any){
    this.confirmationService.confirm({
      target: evt.target as EventTarget,
      message: 'Deseja realmente excluir o arquivo: <br/><br/>' + file.nome + ' ?',
      header: 'Confirmação',
      closable: true,
      closeOnEscape: true,
      icon: 'pi pi-exclamation-triangle',
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary'},
      acceptButtonProps: {label: 'Excluir',},
      accept: () => {
        let arrDel:string[]=[];
        arrDel.push(this.basePath+file.nome);
        this.deleteFileByName(arrDel);
      },
      reject: () =>this.messageService.add({severity: 'error',summary: 'Rejected',detail: 'You have rejected',life: 3000,}),
    });
  }

  addSelect(f: any) {
    if(this.filesSelected.indexOf(f.nome) == -1)
      this.filesSelected.push(f.nome);
    else
      this.filesSelected = this.filesSelected.filter(nome => nome !== f.nome)
  }

  deletSelected(evt:Event){
    this.confirmationService.confirm({
      target: evt.target as EventTarget,
      message: `Deseja realmente excluir o(s) ${this.filesSelected.length} arquivo: ?`,
      header: 'Confirmação',
      closable: true,
      closeOnEscape: true,
      icon: 'pi pi-exclamation-triangle',
      rejectButtonProps: {label: 'Cancelar',severity: 'secondary'},
      acceptButtonProps: {label: 'Excluir',},
      accept: () => {
        let arrDel:string[]=[];
        this.filesSelected.forEach(n =>{
          arrDel.push(this.basePath+n);
        })
        this.deleteFileByName(arrDel);
      },
      reject: () =>this.messageService.add({severity: 'error',summary: 'Rejected',detail: 'You have rejected',life: 3000,}),
    });
  }

  deleteFileByName(arrDel:string[]){
    this.loading = true;
    this.defaultService.delFiles(arrDel, 'documentos/delete').subscribe({
      next: res => this.messageService.add({severity: 'success', summary: 'Success', detail: res}),
      error: err => this.messageService.add({severity: 'error', summary: 'Error', detail: JSON.stringify(err)}),
      complete: () => {
        this.filesSelected = [];
        this.carregar();
      }
    });
  }
}
