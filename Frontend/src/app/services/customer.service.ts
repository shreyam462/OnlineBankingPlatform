import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private apiUrl = '/api/customer';

  constructor(private http: HttpClient) {}

  getCustomerDetails(): Observable<Customer> {
    return this.http.get<Customer>(`${this.apiUrl}/details`, { withCredentials: true });
  }
}
