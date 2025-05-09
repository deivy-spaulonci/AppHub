import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ButtonModule} from 'primeng/button';
import {MenuModule} from 'primeng/menu';
import {MenuItem, MessageService} from 'primeng/api';
import {MenubarModule} from 'primeng/menubar';
import {CardModule} from 'primeng/card';
import {Toolbar} from 'primeng/toolbar';
import {Message} from 'primeng/message';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
})
export class AppComponent implements OnInit {

  ngOnInit() {
  }
}
