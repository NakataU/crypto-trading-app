import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Asset } from './asset';
import { Transaction } from './transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
private transactionURL:string;
private userTransactionURL:string;
private http = inject(HttpClient)

  constructor() { 
    this.transactionURL = "http://localhost:8080/transaction";
    this.userTransactionURL = "http://localhost:8080/user_transactions";

  }

  public findAllByUser(id:number):Observable<any>{
    return this.http.get(this.transactionURL + "/user_transactions/" + id);
  }

  public buyTransaction(transaction:Transaction, userId:number):void{
    this.http.post<Transaction>(this.userTransactionURL + "/buy/" + userId, transaction)
    .subscribe(
        data => {
          console.log("Transaction buy successfully", data);
        },
        error => {
          console.error("Error adding transaction", error);
        }
      );
  }

  public sellTransaction(transaction:Transaction, userId:number):void{
    this.http.post<Transaction>(this.userTransactionURL + "/sell/" + userId, transaction)
    .subscribe(
        data => {
          console.log("Transaction sell successfully", data);
        },
        error => {
          console.error("Error adding transaction", error);
        }
      );
  }

}