import { Component, signal } from '@angular/core';
import {Router,  RouterOutlet} from '@angular/router';
import {
  LinkButtonComponent,
  MenuButtonComponent,
  MenuComponent,
  MenuItemComponent, MenuSepComponent,
  PanelComponent,
  SubMenuComponent
} from 'ng-easyui';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
    PanelComponent,
    LinkButtonComponent,
    MenuComponent,
    MenuButtonComponent,
    MenuItemComponent,
    SubMenuComponent,
    MenuSepComponent,
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('app-jqeasyui');
  protected readonly window = window;

  constructor(public router: Router) {}

  onItemClick($event: any) {
    this.router.navigate([$event]);
  }
}
