import { Component } from '@angular/core';
import { DuelService } from '../common/duel.service';
import { Duel, Player } from '../defintions/interfaces';
import { UserService } from '../common/user.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Tournament } from '../tournaments/tournament.model';
import { TournamentService } from '../tournaments/tournament.service';
import { Messages } from './error-messages';

@Component({
  selector: 'app-user-duels',
  templateUrl: './user-duels.component.html',
  styleUrls: ['./user-duels.component.scss']
})
export class UserDuelsComponent {
  userEmail: string;
  duels: Duel[] = [];
  tournamentNames: {[key: string]: string} = {};
  inconsistentResults: string = null;
  inconsistentDuelId: string;

  constructor(
    private duelService: DuelService,
    private userService: UserService,
    private tournamentService: TournamentService,
  ) {
  }

  ngOnInit() {
    this.userEmail = this.userService.getUserEmail()
    this.getDuels();
  }

  getDuels() {
    this.duelService.getPlayerDuels(this.userEmail).subscribe({
      next: (duels: Duel[]) => {
        this.duels = duels;
        this.extractTournamentNames();
      },
      error: () => {
        console.log('failed to retrieve user duels');
      }
    });
  }

  extractTournamentNames(): void {
    for (let duel of this.duels) {
      const tournamentId = duel.tournamentId;
      if (!(tournamentId in this.tournamentNames)) {
        this.tournamentService.getTournament(tournamentId).subscribe({
          next: (tournament: Tournament) => {
            this.tournamentNames[tournamentId] = tournament.name;
          },
          error: () => {
            console.log('failed to retrieve tournament name');
          }
        });
      }
    }
  }

  canResultBeChanged(duel: Duel) {
    let playerStatus;
    let duelStatus = duel.duelStatus;

    if (duel.player1.email === this.userEmail) {
      playerStatus = duel.player1.playerStatus;
    } else {
      playerStatus = duel.player2.playerStatus;
    }

    return duelStatus !== 'APPROVED' && playerStatus === 'DURING_GAMEPLAY';
  }

  updateDuel(duel: Duel) {
    this.duelService.updateDuel(duel, this.userEmail).subscribe({
      next: (duel) => {
        this.inconsistentResults = null;
        console.log('updated duel', duel);
      },
      error: (response: HttpErrorResponse) => {
        if (response.error.message === 'Results provided by users are inconsistent') {
          this.inconsistentResults = Messages.inconsistentResult;
          this.inconsistentDuelId = duel.id;
          duel.duelStatus = 'INCONSISTENT';
        }
        // } else if (response.error.message === 'There is no next duel. Probably it was final') {
        //
        // }
        // TODO: show inconsistent results message
        console.log(response.error);
      }
    });
  }

  updateWinner(duel: Duel, winner: Player) {
    duel.winner = winner;
  }
}
