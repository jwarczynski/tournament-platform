import { Component } from '@angular/core';
import { TournamentStoreService } from '../../../common/tournament-store.service';
import { Tournament } from '../../tournament.model';

@Component({
  selector: 'app-edit-tournament',
  templateUrl: './edit-tournament.component.html',
  styleUrls: ['./edit-tournament.component.scss']
})
export class EditTournamentComponent {
  private tournament: Tournament;

  constructor(private tournamentStore: TournamentStoreService) {
  }

  ngOnInit(): void {
    this.tournament = this.tournamentStore.getTournamentToEdit();
  }

  getTournament() {
    return this.tournament;
  }
}
