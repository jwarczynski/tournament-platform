import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistrationComponent } from "./registration/registration.component";
import { LoginComponent } from "./login/login.component";
import {ForgotPasswordComponent} from "./forget-password/forget-password.component";
import {TournamentDetailComponent} from "./tournaments/tournament-detail/tournament-detail.component";
import {TournamentCreationComponent} from "./tournaments/tournament-creation/tournament-creation.component";

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'registration', component: RegistrationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'tournament-detail/:id', component: TournamentDetailComponent },
  { path: 'tournament-creation', component: TournamentCreationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
