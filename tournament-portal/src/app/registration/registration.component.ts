import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {API_URLS} from "../api-urls";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  registrationForm!: FormGroup;
  registrationSuccessful = false;
  registrationFailed = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient
  ) {
  }

  ngOnInit() {
    this.registrationForm = this.fb.group({
      firstName: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      repeatPassword: ['', [Validators.required]]
    }, {validator: this.passwordMatchValidator('password', 'repeatPassword')});
  }

  passwordMatchValidator(controlName: string, matchingControlName: string): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const controlValue = control.get(controlName)?.value;
      const matchingControlValue = control.get(matchingControlName)?.value;

      if (controlValue !== matchingControlValue) {
        return { 'passwordMismatch': true };
      }
      return null;
    };
  }

  onRegister() {
    if (this.registrationForm.valid) {
      const firstName = this.registrationForm.get('firstName')?.value;
      const surname = this.registrationForm.get('surname')?.value;
      const email = this.registrationForm.get('email')?.value;
      const password = this.registrationForm.get('password')?.value;

      const body = {firstName, surname, email, password};

      this.http.post(API_URLS.registration, body).subscribe({
        next: response => {
          console.log(response);
          this.registrationSuccessful = true;
          this.registrationForm.reset();
        },
        error: error => {
          this.registrationFailed = true;
          console.error(error);
        }
      });
    }
  }

  onInputBlur() {
    if (this.registrationForm.touched) {
      this.registrationSuccessful = false;
      this.registrationFailed = false;
    }
  }
}
