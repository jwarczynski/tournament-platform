import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from "@angular/common/http";
import { API_URLS } from "../api-urls";
import { Router } from "@angular/router";
import { UserService } from "../common/user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private userService: UserService,
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const email = this.loginForm.get('email')?.value;
      const password = this.loginForm.get('password')?.value;
      this.http.post(API_URLS.login, { email, password })
        .subscribe({
          next: (response) => {
            console.log('Login successful');
            this.userService.setUserEmail(email);
            this.router.navigate(['/']);
          },
          error: (error) => {
            console.log('Login failed');
            this.errorMessage = 'Invalid email or password';
          }
        });
    }
  }
}
