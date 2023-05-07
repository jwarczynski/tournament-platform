import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Tournament } from '../tournament.model';
import { TournamentService } from '../tournament.service';
import { UserService } from '../../common/user.service';
import { TournamentStoreService } from '../../common/tournament-store.service';

@Component({
  selector: 'app-tournament-detail',
  templateUrl: './tournament-detail.component.html',
  styleUrls: ['./tournament-detail.component.scss']
})
export class TournamentDetailComponent implements OnInit {
  tournament!: Tournament;
  isOrganizer: boolean = false;
  today: Date;

  isImageLoading = false;
  sponsorImagesToShow: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private tournamentService: TournamentService,
    private userService: UserService,
    private tournamentStore: TournamentStoreService,
  ) {
  }

  ngOnInit(): void {
    this.today = new Date();
    const id = this.route.snapshot.paramMap.get('id')!;
    const currentUserEmail = this.userService.getUserEmail();

    this.getTournament(id);
    this.isOrganizer = currentUserEmail && this.tournament.organizer === currentUserEmail;
  }

  getTournament(id: string) {
    this.tournamentService.getTournament(id).subscribe(tournament => {
      this.tournament = tournament;
      this.getSponsorImagesFromService();
    });
  }

  getSponsorImagesFromService() {
    for (let sponsor of this.tournament.sponsors) {
      this.tournamentService.getTournamentImage(sponsor).subscribe({
        next: (data) => {
          this.createSponsorImageFromBlob(data);
        },
        error: (error) => {
          console.log(error);
        }
      });
    }
  }

  createSponsorImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener('load', () => {
      this.sponsorImagesToShow.push(reader.result);
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  save(): void {
    this.tournamentService.saveTournament(this.tournament);
  }

  editTournament(): void {
    this.tournamentStore.setTournamentToEdit(this.tournament);
    this.router.navigate(['/edit-tournament', this.tournament._id]);
  }

  canRegister(): boolean {
    const registrationDeadline = new Date(this.tournament.registrationDeadline);
    return registrationDeadline >= this.today;
  }


}
