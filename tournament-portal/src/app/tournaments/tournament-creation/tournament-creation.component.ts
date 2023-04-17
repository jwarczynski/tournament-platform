import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {API_URLS} from "../../api-urls";
import {Router} from "@angular/router";
import {UserService} from "../../common/user.service";
import {FileService} from "../../common/file.service";
import {Tournament} from "../tournament.model";
import {TournamentService} from "../tournament.service";
import {main} from "@popperjs/core";

@Component({
  selector: 'app-tournament-creation',
  templateUrl: './tournament-creation.component.html',
  styleUrls: ['./tournament-creation.component.scss']
})
export class TournamentCreationComponent implements OnInit {

  tournamentForm!: FormGroup;

  errorMessage: string | null = null;

  showMap = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private userService: UserService,
    private tournamentService: TournamentService,
    private fileService: FileService,
  ) { }

  ngOnInit(): void {
    this.tournamentForm = this.fb.group({
      title: ['', [Validators.required]],
      discipline: ['', [Validators.required]],
      entryDeadline: ['', [Validators.required]],
      entryLimit: ['', [Validators.required]],
      description: ['', [Validators.required]],
      location: ['', [Validators.required]],
      sponsorLogos: [[]],
      mainImage: [''],
    });

  }

  onSubmit() {
    if (this.tournamentForm.valid) {
      const name = this.tournamentForm.get('title')?.value;
      const discipline = this.tournamentForm.get('discipline')?.value;
      const registrationDeadline = this.tournamentForm.get('entryDeadline')?.value;
      const date= this.tournamentForm.get('tournamentDate')?.value;
      const registrationLimit = this.tournamentForm.get('entryLimit')?.value;
      const description = this.tournamentForm.get('description')?.value;
      const location = this.tournamentForm.get('location')?.value;
      const sponsors = this.tournamentForm.get('sponsorLogos')?.value;
      const mainImage = this.tournamentForm.get('mainImage')?.value;

      const tournament: Tournament = {
        _id: null,
        name: name,
        discipline: discipline,
        organizer: this.userService.getUserEmail()!,
        registrationDeadline: registrationDeadline,
        date:date,
        registrationLimit: registrationLimit,
        description: description,
        location: location,
        seedPlayers: undefined,
        sponsors: sponsors,
        mainImage: mainImage,
      };

      // save the images locally
      if (sponsors) {
        for (let i = 0; i < sponsors.length; i++) {
          const file = sponsors[i];
          const filePath = this.fileService.saveFile(file);
          tournament.sponsors?.push(filePath);
        }
      }

      if (mainImage) {
        tournament.mainImage = this.fileService.saveFile(mainImage);
      }

      this.tournamentService.saveTournament(tournament);
    }
  }

}
