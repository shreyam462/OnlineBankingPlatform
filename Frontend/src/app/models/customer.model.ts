import { Account } from "./account.model";

export class Customer {
    username: string;
    email: string;
    name: string;
    account: Account;
  
    constructor(username: string, email: string, name: string, account:Account) {
      this.username = username;
      this.email = email;
      this.name = name;
      this.account=account
    }
  }
  