import { Component } from '@angular/core';
import {UserService} from "../common/user.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isCollapsed = true;
  userService: UserService;

  constructor(userService: UserService) {
    this.userService = userService;
  }

  toggleNav() {
    this.isCollapsed = !this.isCollapsed;
  }
}
