import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountStatement } from '../models/account-statement.model';

@Injectable({
  providedIn: 'root'
})
export class PdfService {
  private apiUrl = '/api/pdf';

  constructor(private http: HttpClient) {}

  generatePdf(accountStatement: AccountStatement): Observable<Blob> {
    return this.http.post(`${this.apiUrl}/generate`, accountStatement, { responseType: 'blob' });
  }

  generatePdfWithSign(accountStatement: AccountStatement): Observable<Blob> {
    return this.http.post(`${this.apiUrl}/generate-signed`, accountStatement, { responseType: 'blob' });
  }
}
