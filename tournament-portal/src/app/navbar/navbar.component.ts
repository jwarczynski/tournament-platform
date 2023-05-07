import { Component } from '@angular/core';
import { UserService } from '../common/user.service';
import { Router } from '@angular/router';
import { SearchTournamentsService } from '../common/search-tournaments.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  isCollapsed = true;
  userService: UserService;
  searchTerm: string;

  constructor(
    userService: UserService,
    private router: Router,
    private searchService: SearchTournamentsService,
  ) {
    this.userService = userService;
  }

  ngOnInit() {
    document.addEventListener('click', this.onClickOutside.bind(this));
  }

  toggleNav() {
    this.isCollapsed = !this.isCollapsed;
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/']);
  }

  onClickOutside(event) {
    const target = event.target;
    const navbar = document.querySelector('.navbar');

    if (!navbar.contains(target)) {
      this.isCollapsed = true;
    }
  }

  isHomePage() {
    return this.router.url === '/';
  }

  search(): void {
    this.searchService.changeSearchTerm(this.searchTerm);
  }

  onSearchTermChange(event: string) {
    this.search();
  }

}
