import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../common/user.service';
import { TournamentService } from '../tournament.service';
import { Tournament } from '../tournament.model';


@Component({
  selector: 'app-tournament-creation',
  templateUrl: './tournament-creation.component.html',
  styleUrls: ['./tournament-creation.component.scss']
})
export class TournamentCreationComponent implements OnInit {
  @Input() savedTournament: Tournament;

  tournamentForm!: FormGroup;

  errorMessage: string | null = null;
  showMap = false;
  mainImage: any;
  sponsors: FileList;
  createdTournament: Tournament = null;
  mainImageTypeError = false;
  sponsorsTypeError = false;
  tournamentCreated = false;
  editing: boolean = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private userService: UserService,
    private tournamentService: TournamentService,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    this.initializeForm();
    this.populateInputsIfEditing();
  }

  populateInputsIfEditing() {
    if (this.savedTournament) {
      this.editing = true;
      this.populateInputs();
    } else if (this.route.snapshot.url.some(segment => segment.path === 'edit-tournament')) {
      this.editing = true;
      this.getTournamentAndPopulateInputs();
    }
  }

  getTournamentAndPopulateInputs() {
    let tournamentId = this.route.snapshot.paramMap.get('id');
    this.tournamentService.getTournament(tournamentId).subscribe({
      next: (tournament: Tournament) => {
        this.savedTournament = tournament;
        this.populateInputs();
      },
      error(response: HttpErrorResponse) {
        console.log('Failed to retrieve tournament data to modify');
      },
    });
  }

  initializeForm() {
    this.tournamentForm = this.fb.group({
      title: ['', [Validators.required]],
      discipline: ['', [Validators.required]],
      entryDeadline: ['', [Validators.required]],
      entryLimit: ['', [Validators.required, Validators.min(0)]],
      seedPlayers: ['', [Validators.required, Validators.min(0), this.playersValidator]],
      description: ['', [Validators.required]],
      location: ['', [Validators.required]],
      sponsorLogos: [''],
      tournamentDate: ['', [Validators.required, this.dateValidator]],
      mainImage: [''],
    }, {
      validators: [this.dateValidator, this.mainImageValidator, this.playersValidator/*, this.sponsorImagesValidator*/],
    });
  }

  populateInputs() {
    const tournament = this.savedTournament;
    this.tournamentForm.patchValue({
      title: tournament.name,
      discipline: tournament.discipline,
      entryDeadline: tournament.registrationDeadline,
      entryLimit: tournament.registrationLimit,
      seedPlayers: tournament.seedPlayers,
      description: tournament.description,
      location: tournament.location,
      sponsorLogos: tournament.sponsors,
      tournamentDate: tournament.date,
      mainImage: tournament.mainImage
    });
    this.tournamentForm.patchValue({
      discipline: this.savedTournament.discipline
    })
  }

  isTournamentEditing(): boolean {
    console.log('tournamentEditing:', this.savedTournament !== null)
    return this.savedTournament !== null;
  }

  dateValidator(control: FormGroup): { [key: string]: boolean } | null {
    const entryDeadline = control.get('entryDeadline')?.value;
    const tournamentDate = control.get('tournamentDate')?.value;

    if (entryDeadline && tournamentDate && entryDeadline > tournamentDate) {
      console.log('invalid date');
      return {'invalidDate': true};
    }

    return null;
  }

  playersValidator(control: FormGroup): { [key: string]: boolean } | null {
    const entryLimit = control.get('entryLimit')?.value;
    const seedPlayers = control.get('seedPlayers')?.value;

    if (seedPlayers && entryLimit && seedPlayers > entryLimit) {
      console.log('invalid seed players');
      return {'invalidSeedPlayers': true};
    }

    return null;
  }

  mainImageValidator(control: FormGroup): { [key: string]: boolean } | null {
    const mainImage = control.get('mainImage')?.value;
    if (!mainImage || mainImage.length === 0) {
      return {'noMainImage': true};
    }
    if (Array.isArray(mainImage) && mainImage.length > 1) {
      return {'tooManyMainImages': true};
    }
    return null;
  }

  sponsorImagesValidator(control: FormGroup): { [key: string]: boolean } | null {
    const sponsorLogos = control.get('sponsorLogos')?.value;
    if (!sponsorLogos) {
      return null;
    }
    if (sponsorLogos.length > 5) {
      return {'tooManySponsorLogos': true};
    }
    return null;
  }

  uploadSponsorsFile(event: Event) {
    const files = (event.target as HTMLInputElement)?.files;
    if (files) {
      for (let i = 0; i < files.length; i++) {
        const file = files[i];
        if (!file.type.match(/image\/(png|jpeg)/)) {
          this.sponsorsTypeError = true;
          return;
        }
      }
    }
    this.sponsors = files;
    this.sponsorsTypeError = false;
  }


  uploadMainImage(event: Event) {
    const file = (event.target as HTMLInputElement)?.files?.[0];
    if (!file.type.match(/image\/(png|jpeg)/)) {
      this.mainImageTypeError = true;
      return;
    }
    this.mainImage = file;
    this.mainImageTypeError = false;
  }


  onSubmit() {
    if (this.tournamentForm.valid) {
      const formData: FormData = this.createFormData()
      this.tournamentService.createTournament(formData).subscribe({
        next: (tournament: Tournament) => {
          console.log('Tournament created successfully!', tournament);
          this.createdTournament = tournament;
          this.tournamentCreated = true;
          // this.location.back();
          // this.router.navigate(['/']);
        },
        error: () => {
          this.errorMessage = 'Failed to create tournament.';
        }
      });
    }
  }

  createFormData(): FormData {
    const name = this.tournamentForm.get('title')?.value;
    const discipline = this.tournamentForm.get('discipline')?.value;
    const registrationDeadline = this.tournamentForm.get('entryDeadline')?.value;
    const date = this.tournamentForm.get('tournamentDate')?.value;
    const registrationLimit = this.tournamentForm.get('entryLimit')?.value;
    const seedPlayers = this.tournamentForm.get('seedPlayers')?.value;
    const description = this.tournamentForm.get('description')?.value;
    const location = this.tournamentForm.get('location')?.value;

    const formData = new FormData();
    formData.append('title', name);
    formData.append('discipline', discipline);
    formData.append('organizer', this.userService.getUserEmail()!);
    formData.append('registrationDeadline', registrationDeadline);
    formData.append('date', date);
    formData.append('registrationLimit', registrationLimit);
    formData.append('seedPlayers', seedPlayers);
    formData.append('description', description);
    formData.append('location', location);

    if (this.mainImage) {
      formData.append('mainImage', this.mainImage);
    }

    if (this.sponsors) {
      for (let i = 0; i < this.sponsors.length; i++) {
        const file = this.sponsors[i];
        formData.append('sponsorLogos', file);
      }
    }
    return formData;
  }
}
