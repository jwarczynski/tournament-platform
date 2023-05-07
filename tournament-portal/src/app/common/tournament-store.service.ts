import { Injectable } from '@angular/core';
import { Tournament } from '../tournaments/tournament.model';

@Injectable({
  providedIn: 'root'
})
export class TournamentStoreService {
  private tournamentToEdit: Tournament;

  constructor() { }

  setTournamentToEdit(tournament: Tournament): void {
    this.tournamentToEdit = tournament;
  }

  getTournamentToEdit(): Tournament {
    return this.tournamentToEdit;
  }
}
