import { Component, Input } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
  ValidatorFn, ValidationErrors,
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { API_URLS } from '../api-urls';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent {
  @Input() email!: string;
  resetPasswordForm!: FormGroup;
  errorMessage: string = '';
  passwordResetSuccess: boolean = false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.resetPasswordForm = new FormGroup({
      code: new FormControl('', [Validators.required, Validators.pattern('^[0-9]{6}$')]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)]),
      confirmPassword: new FormControl('', [Validators.required])
    }, { validators: this.passwordsMatchValidator });
  }

  get codeControl() {
    return this.resetPasswordForm.get('code') as FormControl;
  }

  get passwordControl() {
    return this.resetPasswordForm.get('password') as FormControl;
  }

  get confirmPasswordControl() {
    return this.resetPasswordForm.get('confirmPassword') as FormControl;
  }

  passwordsMatchValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
      const password = control.get('password');
      const confirmPassword = control.get('confirmPassword');
      if (password?.value !== confirmPassword?.value) {
        return { passwordsDoNotMatch: true };
      }
      return null;
    };
  }

  onSubmit() {
    console.log('Reset password form submitted');
    console.log('Code:', this.codeControl.value);
    console.log('Password:', this.passwordControl.value);
    console.log('Confirm Password:', this.confirmPasswordControl.value);

    const resetPasswordData = {
      email: this.email,
      verificationCode: this.codeControl.value,
      password: this.passwordControl.value,
    };

    this.http.post(API_URLS.resetPassword, resetPasswordData)
      .subscribe({
        next: () => {
          console.log('Password reset successfully.');
          this.passwordResetSuccess = true;
          this.errorMessage = '';

          // Redirect to login or show success message
        },
        error: error => {
          console.log('Error resetting password:', error);
          this.errorMessage = 'Error resetting password. Please try again.';
          this.passwordResetSuccess = false;
        }
      });
  }
}
