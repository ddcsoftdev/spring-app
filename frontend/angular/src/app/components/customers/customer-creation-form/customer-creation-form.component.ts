import { Component } from '@angular/core';
import {InputTextModule} from "primeng/inputtext";
import {Button, ButtonDirective} from "primeng/button";

@Component({
  selector: 'app-customer-creation-form',
  standalone: true,
  imports: [
    InputTextModule,
    Button,
    ButtonDirective
  ],
  templateUrl: './customer-creation-form.component.html',
  styleUrl: './customer-creation-form.component.scss'
})
export class CustomerCreationFormComponent {

}
