import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import {
  FormsModule,
  ReactiveFormsModule,
} from '@angular/forms';
import { NavbarComponent } from './navbar/navbar.component';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { ForgotPasswordComponent } from './forget-password/forget-password.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { TournamentsComponent } from './tournaments/tournaments.component';
import { TournamentComponent } from './tournaments/tournament/tournament.component';
import { TournamentDetailComponent } from './tournaments/tournament-detail/tournament-detail.component';
import { GoogleMapComponent } from './google-map/google-map.component';
import { TournamentCreationComponent } from './tournaments/tournament-creation/tournament-creation.component';
import { LocationSearchComponent } from './location-search/location-search.component';
import { TournamentSignUpComponent } from './tournaments/tournament-sign-up/tournament-sign-up.component';
import { LadderComponent } from './tournaments/tournament-detail/ladder/ladder.component';
import { AdnSingleEliminationTreeModule } from '@adonsio/adn-tournament';
import { BracketComponent } from './tournaments/tournament-detail/ladder/bracket/bracket.component';
import { UserDuelsComponent } from './user-duels/user-duels.component';
import { EditTournamentComponent } from './tournaments/tournament-creation/edit-tournament/edit-tournament.component';
import { SearchTournamentsService } from './common/search-tournaments.service';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    NavbarComponent,
    LoginComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    TournamentsComponent,
    TournamentComponent,
    TournamentDetailComponent,
    GoogleMapComponent,
    TournamentCreationComponent,
    LocationSearchComponent,
    TournamentSignUpComponent,
    LadderComponent,
    BracketComponent,
    UserDuelsComponent,
    EditTournamentComponent,
  ],
  imports: [
    BrowserModule,
    NgbModule,
    FormsModule,
    RouterModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    AdnSingleEliminationTreeModule,
  ],
  providers: [SearchTournamentsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
