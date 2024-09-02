import { Component } from '@angular/core';
import { Customer } from '../models/customer.model';
import { CustomerService } from '../services/customer.service';
import { Transaction } from '../models/transaction.model';
import { TransactionService } from '../services/transaction.service';
import { PdfService } from '../services/pdf.service';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
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

  constructor(private customerService: CustomerService, private transactionService: TransactionService,private pdfService: PdfService, private router: Router, private snackBar: MatSnackBar) {}

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

  navigateToTransactions(): void{
    this.router.navigate(['/transactions']);
  }

  generateAccountStatement(signed: boolean): void {
    if (this.transactions.length === 0) {
      this.errorMessage = "No transactions found for the given date range.";
      return;
    }

    this.accountStatement = {
      customerName: this.customer?.name,
      accountNumber: this.customer?.account.accountNumber,
      fromDate: this.fromDate,
      toDate: this.toDate,
      transactions: this.transactions
    };

    if (signed) {
      this.generatePdfWithSign();
    } else {
      this.generatePdf();
    }
  }

  generatePdf(): void {
    if (this.accountStatement) {
      this.pdfService.generatePdf(this.accountStatement).subscribe((response: Blob) => this.downloadFile(response, 'account_statement.pdf'));
      this.snackBar.open('Downloading account_statement.pdf', 'Close', { duration: 3000 });
    }
  }

  generatePdfWithSign(): void {
    if (this.accountStatement) {
      this.pdfService.generatePdfWithSign(this.accountStatement).subscribe((response: Blob) => this.downloadFile(response, 'account_statement_signed.pdf'));
      this.snackBar.open('Downloading account_statement_signed.pdf', 'Close', { duration: 3000 });
    }
  }

  private downloadFile(data: Blob, filename: string): void {
    const blob = new Blob([data], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = filename;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  }
  
}
