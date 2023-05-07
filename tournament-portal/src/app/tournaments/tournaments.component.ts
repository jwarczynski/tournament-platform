import { Component, OnDestroy, OnInit } from '@angular/core';
import { Tournament } from './tournament.model';
import { TournamentService } from './tournament.service';
import { SearchTournamentsService } from '../common/search-tournaments.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.scss']
})
export class TournamentsComponent implements OnInit, OnDestroy {
  tournaments: Tournament[] = [];
  filteredTournaments: Tournament[] = [];
  searchTerm: string = '';

  currentPage = 1;
  itemsPerPage = 10;

  subscription: Subscription;

  constructor(
    private tournamentService: TournamentService,
    private searchService: SearchTournamentsService,
  ) {
  }

  ngOnInit(): void {
    this.getTournaments();
    this.subscription = this.searchService.currentSearchTerm.subscribe(searchTerm => {
      this.searchTerm = searchTerm;
      this.searchTournaments();
    });
  }

  getTournaments(): void {
    this.tournamentService.getTournaments()
      .subscribe(
        tournaments => {
          this.tournaments = tournaments || [];
          this.filteredTournaments = tournaments || [];
        });
  }

  searchTournaments(): void {
    if (this.searchTerm.trim().length === 0) {
      // if search term is empty, show all tournaments
      this.filteredTournaments = this.tournaments;
    } else {
      this.filteredTournaments = this.tournaments.filter((tournament) => {
        return tournament.name.toLowerCase().includes(this.searchTerm.toLowerCase());
      });
    }
  }


  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  setPage(page: number) {
    this.currentPage = page;
  }

  get tournamentsToShow(): Tournament[] {
    if (this.filteredTournaments) {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredTournaments.slice(start, end);
    }
    return [];
  }

  get totalPages() {
    return Math.ceil(this.tournaments.length / this.itemsPerPage);
  }

  get pages() {
    const pages = [];
    for (let i = 1; i <= this.totalPages; i++) {
      pages.push(i);
    }
    return pages;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
