import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticationRequest} from "../../models/authentication-request";
import {Observable} from "rxjs";
import {AuthenticationResponse} from "../../models/authentication-response";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }//inject HTTP Client for http request

  login(authRequest: AuthenticationRequest): Observable<AuthenticationResponse>{
    return this.http.post<AuthenticationResponse>("http://localhost:8080/api/v1/auth/login", authRequest);
  }
}
