import { Component } from '@angular/core';
import { AdnRound, AdnTournament } from '@adonsio/adn-tournament/lib/declarations/interfaces';
import { DuelService } from '../../../common/duel.service';
import { ActivatedRoute } from '@angular/router';
import { Duel, Match, Player } from '../../../defintions/interfaces';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-ladder',
  templateUrl: './ladder.component.html',
  styleUrls: ['./ladder.component.scss']
})
export class LadderComponent {
  duels: Duel[] = [];
  matches = [];

  constructor(
    private route: ActivatedRoute,
    private duelService: DuelService,
  ) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.duelService.getDuelsByTournamentId(id).subscribe({
      next: (duels: Duel[]) => {
        console.log('retrieved duels:', duels);
        this.duels = duels
        this.tournamentData = this.createAdnTournaments();
      },
      error: (response: HttpErrorResponse) => {
        console.log('error during retreiving duels:', response.error);
      }
    });
  }

  createAdnTournaments(): AdnTournament {
    let rounds: AdnRound[] = []
    const max = this.duels.reduce((prev, current) => (prev.phase > current.phase) ? prev : current).phase
    for (let i = 1; i <= max; i++) {
      if (i === max) {
        rounds.push(this.createAdnRound(i, 'Final'));
      } else {
        rounds.push(this.createAdnRound(i, 'Winnerbracket'));
      }
    }

    console.log('all rounds', rounds);
    return {
      rounds: rounds
    }
  }

  createAdnRound(phase: number, type: 'Winnerbracket' | 'Loserbracket' | 'Final'): AdnRound {
    return {
      type: type,
      matches: this.createMatches(phase)
    };
  }


  createMatches(phase: number): Match[] {
    let matches: Match[] = [];
    const numDuelsWithPhase1 = this.duels.filter(duel => duel.phase === phase).length;
    for (let i = 0; i < numDuelsWithPhase1; i++) {
      matches.push(this.createPair(phase, i));
    }
    return matches;
  }

  createPair(phase: number, duelNumber: number): Match {
    let player1: Player = this.getPlayer(phase, duelNumber, 'player1');
    let player2: Player = this.getPlayer(phase, duelNumber, 'player2');
    let winner: Player = this.getPlayer(phase, duelNumber, 'winner');
    return {
      players: [player1, player2],
      result: winner?.email === "" || winner === null ? -1 : winner?.email === player1.email  ? 0 : 1
    };
  }

  getPlayer(phase: number, duelNumber: number, player: string): Player {
    return this.duels.find(
      duel => duel.phase === phase && duel.duelNumber === duelNumber
    )?.[player]
  }

  tournamentData: AdnTournament = {
    rounds: [
      {
        type: 'Final',
        matches: []
      }
    ]
  };

  public handleClickEvent(name: string) {
    console.log('Team name clicked: ', name)
  }
}
