import {Component, OnInit} from '@angular/core';
import {Panel} from 'primeng/panel';
import {ActivatedRoute, Router} from '@angular/router';
import {DefaultService} from '../../../service/default.service';
import {Conta} from '../../../model/conta';
import {Button} from 'primeng/button';
import {CurrencyPipe, DatePipe, NgIf} from '@angular/common';
import {Divider} from 'primeng/divider';
import {TableModule} from 'primeng/table';
import {Message} from 'primeng/message';

@Component({
  selector: 'app-conta-detail',
  imports: [
    Panel,
    Button,
    CurrencyPipe,
    DatePipe,
    Divider,
    NgIf,
    TableModule,
    Message
  ],
  templateUrl: './conta-detail.component.html',
  standalone: true,
  styleUrl: './conta-detail.component.css'
})
export class ContaDetailComponent  implements OnInit{

  conta:Conta=new Conta();

  constructor(private router: Router,
              private route: ActivatedRoute,
              private defaultService: DefaultService,) {
  }

  ngOnInit(): void {
    let id = this.route.snapshot.paramMap.get('id');
    this.defaultService.get('conta/'+id).subscribe({
      next: res =>{
        this.conta = res;
      },
      error: error=>{},
      complete:() =>{}
    });
  }

  goToContalist(){
    this.router.navigate(['/conta-table'])
  }
}
