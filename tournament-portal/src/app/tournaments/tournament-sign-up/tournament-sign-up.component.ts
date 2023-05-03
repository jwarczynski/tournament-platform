import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Route, Router} from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { UserService } from '../../common/user.service';
import {TournamentService} from "../tournament.service";
import {TournamentSignUpFormModel} from "./tournament-sign-up-form.model";
import {Location} from "@angular/common";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-tournament-sign-up',
  templateUrl: './tournament-sign-up.component.html',
  styleUrls: ['./tournament-sign-up.component.scss']
})
export class TournamentSignUpComponent implements OnInit {
  tournamentForm: FormGroup;
  tournamentId: string;
  submissionStatus: 'initial' | 'success' | 'error' | 'full application list' | 'already signed up' = 'initial';

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private userService: UserService,
    private tournamentService: TournamentService,
    private route: ActivatedRoute,
    private location: Location,
  ) {
    this.tournamentForm = this.fb.group({
      licenseNumber: ['', Validators.required],
      ranking: ['', [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit(): void {
    if (!this.userService.isLoggedIn()) {
      this.router.navigate(['/login']);
    }
    this.tournamentId = this.route.snapshot.paramMap.get('id');
  }

  onSubmit(): void {
    if (this.tournamentForm.valid) {
      const tournamentSignUpData: TournamentSignUpFormModel = Object.assign({}, this.tournamentForm.value);
      tournamentSignUpData.tournamentId = this.route.snapshot.paramMap.get('id');
      tournamentSignUpData.email = this.userService.getUserEmail();
      this.signUp(tournamentSignUpData);
    } else {
      console.log("invalid signup data");
    }
  }

  signUp(tournamentSignUpData: TournamentSignUpFormModel) {
    this.tournamentService.signUp(tournamentSignUpData).subscribe({
      next: (response: string) => {
        // this.handleSuccessfulSignUp(response);
        this.submissionStatus = "success";
      },
      error: (response: HttpErrorResponse) => {
        this.handleSignUpError(response.error);
      }
    });
  }

  handleSuccessfulSignUp(response: string) {
    // TODO: show successful message and cancel button
    this.submissionStatus = "success";
    console.log("successful signed up");
  }

  handleSignUpError(response) {
    // TODO: show error message and cancel button
    if (response.message === "The tournament is full.") {
      // TODO: show message and disable submit button, show cancel button
      this.submissionStatus = 'full application list';
    } else if (response.message === "User already signed up") {
      // TODO: handle internal error
      this.submissionStatus = 'already signed up';
    } else {
      this.submissionStatus = "error";
    }
  }

}
