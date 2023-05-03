import { Component } from '@angular/core';
import {UserService} from "../common/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isCollapsed = true;
  userService: UserService;

  constructor(
    userService: UserService,
    private router: Router,
  ) {
    this.userService = userService;
  }

  toggleNav() {
    this.isCollapsed = !this.isCollapsed;
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/']);
  }
}
