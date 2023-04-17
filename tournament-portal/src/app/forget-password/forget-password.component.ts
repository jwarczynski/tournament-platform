import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { API_URLS } from '../api-urls';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forget-password.component.html',
})
export class ForgotPasswordComponent implements OnInit {
  forgotPasswordForm!: FormGroup;
  showSuccessMessage = false;
  errorMessage: string | null = null;
  emailSent : boolean = false;


  constructor(private formBuilder: FormBuilder, private http: HttpClient) {}

  ngOnInit(): void {
    this.forgotPasswordForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
    });
  }

  onSubmit(): void {
    if (this.forgotPasswordForm.valid) {
      const email = this.forgotPasswordForm.get('email')?.value;
      const forgotPasswordUrl = `${API_URLS.forgotPassword}`;
      const options = { params: { email: email } };
      this.http.get(forgotPasswordUrl, options).subscribe({
        next: (response) => {
          // TODO: Implement success logic
          console.log('Email sent successfully');
          // this.forgotPasswordForm.reset();
          this.showSuccessMessage = true;
          this.errorMessage = null;
          this.emailSent = true;
        },
        error: (error) => {
          console.log('Email sending failed');
          this.errorMessage = 'Failed to send email. Please try again later.';
          this.showSuccessMessage = false;
        }
      });
    }
  }

}
