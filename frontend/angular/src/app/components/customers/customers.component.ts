import { Component } from '@angular/core';
import {MenuBarComponent} from "../menu-bar/menu-bar.component";
import {HeaderBarComponent} from "../header-bar/header-bar.component";
import {Button} from "primeng/button";
import {SidebarModule} from "primeng/sidebar";
import {CustomerCreationFormComponent} from "./customer-creation-form/customer-creation-form.component";

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [
    MenuBarComponent,
    HeaderBarComponent,
    Button,
    SidebarModule,
    CustomerCreationFormComponent,
  ],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.scss'
})
export class CustomersComponent {
  createCustomerSidebarVisible: boolean = false;

}
