import { Component } from '@angular/core';
import {Button} from "primeng/button";
import {AvatarModule} from "primeng/avatar";
import {MenuModule} from "primeng/menu";
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-header-bar',
  standalone: true,
  imports: [
    Button,
    AvatarModule,
    MenuModule
  ],
  templateUrl: './header-bar.component.html',
  styleUrl: './header-bar.component.scss'
})
export class HeaderBarComponent {

  userImage: string = "../../../assets/images/logo-black.png";
  userEmail: string = "ddcsoftwaredev@gmail.com";
  userRole: string = "ROLE_ADMIN";
  items: Array<MenuItem> = [
    {label: "Profile", icon: "pi pi-user"},
    {label: "Settings", icon: "pi pi-cog"},
    {separator: true},
    {label: "Sign out", icon: "pi pi-sign-out"},
  ];
}
