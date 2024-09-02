import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction } from '../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private apiUrl = '/api/transaction';

  constructor(private http: HttpClient) {}

  getTransactions(accountNumber: string, fromDate: string, toDate: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/list`, {
      params: {
        accountNumber,
        fromDate,
        toDate
      },
      withCredentials: true
    });
  }

  createTransaction(accountNumber: string, transaction: Transaction): Observable<Transaction> {
    return this.http.post<Transaction>(`${this.apiUrl}?accountNumber=${accountNumber}`, transaction);
  }

  updateTransaction(id: number, transaction: Transaction): Observable<Transaction> {
    return this.http.put<Transaction>(`${this.apiUrl}/${id}`, transaction);
  }

  deleteTransaction(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

