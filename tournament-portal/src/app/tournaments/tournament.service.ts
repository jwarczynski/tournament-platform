import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Tournament } from './tournament.model';
import { API_URLS } from "../api-urls";
import {TournamentSignUpFormModel} from "./tournament-sign-up/tournament-sign-up-form.model";

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  constructor(private http: HttpClient) { }

  tournaments: Tournament[] = [
    {
      _id: '1',
      name: 'Tournament 1',
      discipline: 'Discipline 1',
      description: 'Discipline 1',
      organizer: 'Organizer 1',
      date: new Date(),
      location: 'Politechnika Poznańska, Kampus Piotrowo, Piotrowo 3, 61-001 Poznań',
      registrationLimit: 10,
      registrationDeadline: new Date(),
      sponsors: ['/assets/images/sponsor1.png'],
      mainImage: 'sponsor1.png',
      seedPlayers: 8
    },
    {
      _id: '64451f931e496a3f8ca4a744',
      name: 'Tournament 2',
      discipline: 'Discipline 2',
      description: 'Discipline 2',
      organizer: 'jedrasowicz@gmail.com',
      date: new Date(),
      location: 'Caltech Powell-Booth Laboratory for Computational Science, East California Boulevard, Pasadena, CA, USA',
      registrationLimit: 8,
      registrationDeadline: new Date(),
      sponsors: ['/assets/images/sponsor2.png'],
      mainImage: 'sponsor2_1682174701973.png',
      seedPlayers: 16
    },
    {
      _id: '3',
      name: 'Tournament 3',
      discipline: 'Discipline 3',
      description: 'Discipline 3',
      organizer: 'Organizer 3',
      date: new Date(),
      location: 'Stanford University, Serra Mall, Stanford, CA, USA',
      registrationLimit: 12,
      registrationDeadline: new Date(),
      sponsors: ['/assets/images/sponsor3.png'],
      mainImage: 'sponsor3_1682174701957.png',
      seedPlayers: 32
    }
  ];

  getTournaments(): Observable<Tournament[]> {
    return of(this.tournaments);
    //   return this.http.get<Tournament[]>(API_URLS.tournaments);
  }

  getTournament(id: string): Observable<Tournament> {
    const tournament = this.tournaments.find(t => t._id === id);
    return of(tournament!);
    // return this.http.get<Tournament>(`${API_URLS.tournaments}/${id}`);
  }

  saveTournament(tournament: Tournament) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    return this.http.post(API_URLS.tournaments + "/save", tournament, httpOptions).subscribe({
      next: () => {
        console.log('Password reset successfully.');
      },
      error: error => {
        console.log('Error resetting password:', error);
      }
    });
  }

  getTournamentImage(filename: string): Observable<Blob> {
    const options = {
      responseType: 'blob' as const // Cast responseType to 'blob'
    };
    const url = `${API_URLS.tournaments}/image?filename=${filename}`;
    return this.http.get(url, options);
  }

  createTournament(formData: FormData) :Observable<Tournament>{
    return this.http.post<Tournament>(API_URLS.createTournament, formData);
  }

  signUp(tournamentSignupForm: TournamentSignUpFormModel): Observable<any> {
    console.log("signup data:", tournamentSignupForm);
    const url = `${API_URLS.tournaments}/signup`;
    return this.http.post<any>(url, tournamentSignupForm);
  }



}
