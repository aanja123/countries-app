import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CountrySummary } from '../../models/country.model';
import { CountryService } from '../../services/country.service';

@Component({
  selector: 'app-country-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './country-list.html',
  styleUrl: './country-list.scss'
})
export class CountryListComponent implements OnInit {
  countries: CountrySummary[] = [];
  loading = false;
  errorMessage: string | null = null;

  searchTerm = '';
  selectedRegion = '';
  sortOrder = '';

  regions = ['Africa', 'Americas', 'Asia', 'Europe', 'Oceania'];

  constructor(
    private countryService: CountryService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadCountries();
  }

  loadCountries(): void {
    this.loading = true;
    this.errorMessage = null;

    this.countryService
      .getCountries(this.searchTerm, this.selectedRegion, this.sortOrder)
      .subscribe({
        next: (data) => {
          this.countries = data;
          this.loading = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.errorMessage = 'Could not load countries. Please try again later.';
          this.loading = false;
          this.cdr.detectChanges();
        }
      });
  }

  onFilterChange(): void {
    this.loadCountries();
  }
}