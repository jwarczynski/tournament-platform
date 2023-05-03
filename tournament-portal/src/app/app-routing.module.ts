import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegistrationComponent } from "./registration/registration.component";
import { LoginComponent } from "./login/login.component";
import {ForgotPasswordComponent} from "./forget-password/forget-password.component";
import {TournamentDetailComponent} from "./tournaments/tournament-detail/tournament-detail.component";
import {TournamentCreationComponent} from "./tournaments/tournament-creation/tournament-creation.component";
import {TournamentSignUpComponent} from "./tournaments/tournament-sign-up/tournament-sign-up.component";
import {LadderComponent} from "./tournaments/tournament-detail/ladder/ladder.component";

const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: 'registration', component: RegistrationComponent },
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'tournament-detail/:id', component: TournamentDetailComponent },
  { path: 'tournament-creation', component: TournamentCreationComponent },
  { path: 'tournament-sign-up/:id', component: TournamentSignUpComponent },
  { path: 'ladder/:id', component: LadderComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
