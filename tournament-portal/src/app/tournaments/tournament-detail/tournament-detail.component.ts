import {Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Tournament } from '../tournament.model';
import { TournamentService } from '../tournament.service';
import {UserService} from "../../common/user.service";
import {AdnTournament} from "@adonsio/adn-tournament/lib/declarations/interfaces";

declare var google: any;

@Component({
  selector: 'app-tournament-detail',
  templateUrl: './tournament-detail.component.html',
  styleUrls: ['./tournament-detail.component.scss']
})
export class TournamentDetailComponent implements OnInit {
  tournament!: Tournament;
  isOrganizer: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private tournamentService: TournamentService,
    private userService: UserService,
  ) {}

  ngOnInit(): void {
    console.log("detail init");
    const id = this.route.snapshot.paramMap.get('id')!;
    this.tournamentService.getTournament(id).subscribe(tournament => {
      this.tournament = tournament;
      // check if the current user is the organizer of this tournament
      const currentUserEmail = this.userService.getUserEmail();
      console.log('current user login:', currentUserEmail);
      if (currentUserEmail && this.tournament.organizer === currentUserEmail) {
        this.isOrganizer = true;
      }
    });
  }

  save(): void {
    this.tournamentService.saveTournament(this.tournament);
  }

}
