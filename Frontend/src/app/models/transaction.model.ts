export class Transaction {
    id?: number;
    referenceNo: string;
    date: Date;
    amount: number;
    balance: number;
    journalNo?: string;
    chequeNo?: string;
    statementNarrative?: string;
    transferNarrative?: string;
  
    constructor(
      referenceNo: string,
      date: Date,
      amount: number,
      balance: number,
      journalNo?: string,
      chequeNo?: string,
      statementNarrative?: string,
      transferNarrative?: string
    ) {
      this.referenceNo = referenceNo;
      this.date = date;
      this.amount = amount;
      this.balance = balance;
      this.journalNo = journalNo;
      this.chequeNo = chequeNo;
      this.statementNarrative = statementNarrative;
      this.transferNarrative = transferNarrative;
    }
  }
  