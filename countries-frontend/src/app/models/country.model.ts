export interface CountrySummary {
  code: string;
  name: string;
  capital: string | null;
  population: number;
  region: string;
  flagUrl: string | null;
}

export interface CountryDetail extends CountrySummary {
  officialName: string;
  subregion: string | null;
  area: number | null;
  flagAlt: string | null;
  languages: string[];
  currencies: string[];
  borders: string[];
  timezones: string[];
}