import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URLS } from '../api-urls';
import { Observable } from 'rxjs';
import { Duel } from '../defintions/interfaces';

@Injectable({
  providedIn: 'root'
})
export class DuelService {

  constructor(private http: HttpClient) {
  }

  getDuelsByTournamentId(tournamentId: string): Observable<Duel[]> {
    const url = `${API_URLS.duels}/tournament?tournament=${tournamentId}`;
    return this.http.get<Duel[]>(url);
  }

  getPlayerDuels(playerEmail: string): Observable<Duel[]> {
    const url = `${API_URLS.duels}?user=${playerEmail}`;
    return this.http.get<Duel[]>(url);
  }

  updateDuel(duel: Duel, applicantEmail: string): Observable<Duel> {
    switch (duel.duelStatus) {
      case 'NOT_PLAYED':
        duel.duelStatus = 'PENDING_QUORUM';
        break;
      case 'INCONSISTENT':
        duel.duelStatus = 'APPROVED';
        break;
      case 'PENDING_QUORUM':
        duel.duelStatus = 'APPROVED';
        break;
      default:
        console.error('invalid duel status');
    }
    const url = `${API_URLS.duels}/save-result`;
    return this.http.post<Duel>(url, {duel, applicantEmail});
  }

}
