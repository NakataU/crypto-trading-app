import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { User } from '../user';
import { CommonModule } from '@angular/common';
import { TransactionService } from '../transaction-service';

@Component({
  selector: 'app-buy-sell-dialog',
  standalone: true,
  imports: [MatDialogModule, CommonModule],
  templateUrl: './buy-sell-dialog.component.html',
  styleUrl: './buy-sell-dialog.component.css'
})
export class BuyDialogComponent{

  total: number;
  hasEnoughBalance: boolean;
  private currentUserId = 1;//Because we do not have security

constructor(@Inject(MAT_DIALOG_DATA) public data: { amount: number, price:number, user: User, cryptoName:string, symbol: string }, private transactionService:TransactionService){
  this.total = data.amount * data.price;
    this.hasEnoughBalance = data.user.freeBalance >= this.total;
}

transaction={
  id:-1,
    type:"BUY",
    assetName:" ",
    assetSymbol:" ",
    quantity:-1,
    price:-1,
    createdOn:null

}

buyCrypto(){ 
  this.transaction={
    id:-1,
      type:"BUY",
      assetName:this.data.cryptoName,
      assetSymbol:this.data.symbol,
      quantity:this.data.amount,
      price:this.total,
      createdOn:null,
  
  }
  
  this.transactionService.buyTransaction(this.transaction, this.currentUserId)
  window.location.reload();
}

get isInvalidInput(): boolean {
  return this.data.amount <= 0;
}

}
