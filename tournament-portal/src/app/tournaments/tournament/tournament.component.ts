import { Component, Input } from '@angular/core';
import { Tournament } from '../tournament.model';
import { Router } from '@angular/router';
import { TournamentService } from '../tournament.service';
import { UserService } from '../../common/user.service';

@Component({
  selector: 'app-tournament',
  templateUrl: './tournament.component.html',
  styleUrls: ['./tournament.component.scss']
})
export class TournamentComponent {
  @Input() tournament!: Tournament;
  isImageLoading = false;
  imageToShow: any;
  isOrganizer: boolean = false;

  constructor(
    private router: Router,
    private tournamentService: TournamentService,
    private userService: UserService,
  ) {}

  ngOnInit() {
    this.isOrganizer = this.userService.getUserEmail() === this.tournament.organizer;
    this.getImageFromService()
  }

  getImageFromService() {
    this.isImageLoading = true;
    this.tournamentService.getTournamentImage(this.tournament.mainImage).subscribe({
      next: (data) => {
        this.createImageFromBlob(data);
        this.isImageLoading = false;
      },
      error: (error) => {
        this.isImageLoading = false;
        console.log(error);
      }
    });
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }


  viewDetails() {
    this.router.navigate(['/tournament-detail', this.tournament._id]);
  }
}
