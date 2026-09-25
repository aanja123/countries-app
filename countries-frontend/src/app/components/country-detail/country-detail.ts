import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CountryDetail } from '../../models/country.model';
import { CountryService } from '../../services/country.service';

@Component({
  selector: 'app-country-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './country-detail.html',
  styleUrl: './country-detail.scss'
})
export class CountryDetailComponent implements OnInit {
  country: CountryDetail | null = null;
  loading = false;
  errorMessage: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private countryService: CountryService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const code = this.route.snapshot.paramMap.get('code');
    if (code) {
      this.loadCountry(code);
    }
  }

  loadCountry(code: string): void {
    this.loading = true;
    this.errorMessage = null;

    this.countryService.getCountry(code).subscribe({
      next: (data) => {
        this.country = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorMessage = 'Could not load this country. It may not exist.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
}