import {Component, Input} from '@angular/core';
import {Tournament} from "../tournament.model";
import {Router} from "@angular/router";


@Component({
  selector: 'app-tournament',
  templateUrl: './tournament.component.html',
  styleUrls: ['./tournament.component.scss']
})
export class TournamentComponent {

  @Input() tournament!: Tournament;

  constructor(private router: Router) { }

  viewDetails() {
    this.router.navigate(['/tournament-detail', this.tournament._id]);
  }
}
