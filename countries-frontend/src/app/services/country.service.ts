import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CountryDetail, CountrySummary } from '../models/country.model';

@Injectable({ providedIn: 'root' })
export class CountryService {
  private readonly baseUrl = 'http://localhost:8080/api/countries';

  constructor(private http: HttpClient) {}

  getCountries(name?: string, region?: string, sort?: string): Observable<CountrySummary[]> {
    let params = new HttpParams();
    if (name) params = params.set('name', name);
    if (region) params = params.set('region', region);
    if (sort) params = params.set('sort', sort);

    return this.http.get<CountrySummary[]>(this.baseUrl, { params });
  }

  getCountry(code: string): Observable<CountryDetail> {
    return this.http.get<CountryDetail>(`${this.baseUrl}/${code}`);
  }
}