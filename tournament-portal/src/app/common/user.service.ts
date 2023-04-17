import { Injectable } from '@angular/core';

const USER_EMAIL_KEY = 'user_email';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userEmail: string | null = null;

  constructor() {
    const userEmail = localStorage.getItem(USER_EMAIL_KEY);
    if (userEmail) {
      this.userEmail = userEmail;
    }
  }

  setUserEmail(email: string) {
    localStorage.setItem(USER_EMAIL_KEY, email);
    this.userEmail = email;
  }

  getUserEmail(): string | null {
    return this.userEmail;
  }

  isLoggedIn(): boolean {
    return !!this.userEmail;
  }

  logout() {
    localStorage.removeItem(USER_EMAIL_KEY);
    this.userEmail = null;
  }
}
