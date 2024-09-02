import { Transaction } from './transaction.model';

export class AccountStatement {

  customerName:string
  accountNumber: string;
  fromDate: string;
  toDate: string;
  transactions: Transaction[];

  constructor(customerName:string, accountNumber: string, fromDate: string, toDate: string, transactions: Transaction[]) {
    this.customerName= customerName
    this.accountNumber = accountNumber;
    this.fromDate = fromDate;
    this.toDate = toDate;
    this.transactions = transactions;
  }
}
