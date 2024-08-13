import { Component } from '@angular/core';
import {AvatarModule} from "primeng/avatar";
import {ImageModule} from "primeng/image";
import {MenuItem} from "primeng/api";
import {NgForOf} from "@angular/common";
import {MenuItemComponent} from "./menu-item/menu-item.component";

@Component({
  selector: 'app-menu-bar',
  standalone: true,
  imports: [
    AvatarModule,
    ImageModule,
    NgForOf,
    MenuItemComponent
  ],
  templateUrl: './menu-bar.component.html',
  styleUrl: './menu-bar.component.scss'
})
export class MenuBarComponent {

  menu: Array<MenuItem> = [
    { label: 'Home', icon: 'pi pi-home' },
    { label: 'Clients', icon: 'pi pi-users' },
    { label: 'Settings', icon: 'pi pi-cog' }
  ]
}
