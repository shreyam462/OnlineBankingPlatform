import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { LoginRequest } from '../models/login-request.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = '/api';

  constructor(private http: HttpClient) {}

  login(loginRequest: LoginRequest): Observable<void> {
    console.log('Attempting to log in user:', loginRequest.username);
    return this.http.post(`${this.apiUrl}/login`, loginRequest, { observe: 'response', responseType: 'text', withCredentials: true })
      .pipe(
        map((response: HttpResponse<string>) => {
          console.log('HTTP response:', response);
          if (response.status === 200) {
            console.log('Login successful for user:', loginRequest.username);
            return;
          } else {
            console.error('Login failed with status:', response.status);
            throw new Error('Login failed');
          }
        }),
        catchError((error: HttpErrorResponse) => {
          console.error('HTTP error response:', error);
          if (error.status === 401) {
            return throwError(() => new Error('Invalid username or password'));
          }
          return throwError(() => new Error(`An error occurred: ${error.message}`));
        })
      );
  }
}



