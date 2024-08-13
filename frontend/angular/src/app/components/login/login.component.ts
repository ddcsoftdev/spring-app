import { Component } from '@angular/core';
import {ImageModule} from "primeng/image";
import {AvatarModule} from "primeng/avatar";
import {InputTextModule} from "primeng/inputtext";
import {ButtonDirective} from "primeng/button";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {AuthenticationRequest} from "../../models/authentication-request";
import {FormsModule} from "@angular/forms";
import {Messages, MessagesModule} from "primeng/messages";
import {Message} from "primeng/api";
import {NgIf} from "@angular/common";
import {Router} from "@angular/router";


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ImageModule,
    AvatarModule,
    InputTextModule,
    ButtonDirective,
    FormsModule,
    MessagesModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
  }
  authenticationRequest: AuthenticationRequest = {};
  messages: Message[] = [];

  login() {
    this.messages = [];
    this.authenticationService.login(this.authenticationRequest)
      .subscribe({
        next: (authenticationResponse) => {
          localStorage.setItem("user", JSON.stringify(authenticationResponse)); //store response
          this.router.navigate(["customers"]);//navigate to customers page
        },
        error: (err) => {
          if (err.error.statusCode === 401){
            this.messages = [{severity: 'error', detail: "Username or Password incorrect."}];
          }
        }
      })
  }

}
