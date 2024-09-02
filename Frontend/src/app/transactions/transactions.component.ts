import { Component } from '@angular/core';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer.service';
import { Transaction } from '../models/transaction.model';
import { TransactionService } from '../services/transaction.service';
import { PdfService } from '../services/pdf.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent {
  customer: Customer | null = null;
  errorMessage: string | null = null;
  fromDate: string = '';
  toDate: string = '';
  transactions: Transaction[] = [];
  accountStatement: any = null;
  editedTransaction: Transaction | null = null;
  tempTransaction: Transaction = new Transaction('', new Date(), 0, 0);
  newTransaction: Transaction = new Transaction('', new Date(), 0, 0);
  showAddForm: boolean = false;

  constructor(private customerService: CustomerService, private transactionService: TransactionService,private pdfService: PdfService) {}

  ngOnInit(): void {
    this.getCustomerDetails();
  }

  getCustomerDetails(): void {
    this.customerService.getCustomerDetails().subscribe({
      next: (data) => {
        this.customer = data;
      },
      error: (error) => {
        this.errorMessage = error.message;
      }
    });
  }

  getTransactions(): void {
    if (this.customer && this.customer.account && this.fromDate && this.toDate) {
      this.transactionService.getTransactions(this.customer.account.accountNumber, this.fromDate, this.toDate).subscribe({
        next: (data) => {
          this.transactions = data;
        },
        error: (error) => {
          this.errorMessage = error.message;
        }
      });
    } else {
      this.errorMessage = 'Please select a date range.';
    }
  }

  addTransaction(): void {
    if (this.customer && this.customer.account) {
      this.transactionService.createTransaction(this.customer.account.accountNumber, this.newTransaction).subscribe({
        next: () => {
          this.getTransactions();
          this.newTransaction = new Transaction('', new Date(), 0, 0);
          this.showAddForm = false; // Hide the form after adding the transaction
        },
        error: (error) => {
          this.errorMessage = error.message;
        }
      });
    }
  }

  updateTransaction(transaction: Transaction): void {
    if (transaction.id) {
      this.transactionService.updateTransaction(transaction.id, transaction).subscribe({
        next: () => {
          this.getTransactions();
        },
        error: (error) => {
          this.errorMessage = error.message;
        }
      });
    }
  }

  deleteTransaction(id: number): void {
    this.transactionService.deleteTransaction(id).subscribe({
      next: () => {
        this.getTransactions();
      },
      error: (error) => {
        this.errorMessage = error.message;
      }
    });
  }

  editTransaction(transaction: Transaction): void {
    this.editedTransaction = { ...transaction };
    this.tempTransaction = { ...transaction };
  }

  cancelEdit(): void {
    this.editedTransaction = null;
  }

  saveEdit(): void {
    if (this.editedTransaction) {
      this.updateTransaction(this.tempTransaction);
      this.editedTransaction = null;
    }
  }

  showAddTransactionForm(): void {
    this.showAddForm = true;
  }

  cancelAddTransaction(): void {
    this.showAddForm = false;
  }
}
