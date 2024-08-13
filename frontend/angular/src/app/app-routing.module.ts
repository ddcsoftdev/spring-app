import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomersComponent} from "./components/customers/customers.component";
import {LoginComponent} from "./components/login/login.component";

const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "customers",
    component: CustomersComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
