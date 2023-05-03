import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URLS } from '../api-urls';
import { Observable } from 'rxjs';
import { Duel } from '../defintions/interfaces';

@Injectable({
  providedIn: 'root'
})
export class DuelService {

  constructor(private http: HttpClient) { }

  getDuels(tournamentId: string): Observable<Duel[]>{
    // const url = `${API_URLS.duels}?tournament=${tournamentId}`;
    const url = `${API_URLS.duels}?tournament=645215fa69248373782e2f3e`;
    return this.http.get<Duel[]>(url);
  }
}
