import { Component } from '@angular/core';
import {Router, RouterState} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  constructor(private router: Router) {}

  get showTournaments(): boolean {
    const homePageUrl = '/';
    const routerState: RouterState = this.router.routerState;
    const currentUrl = routerState.snapshot.url;
    return currentUrl === homePageUrl;
  }

}
