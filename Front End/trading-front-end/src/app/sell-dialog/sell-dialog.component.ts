import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { User } from '../user';
import { TransactionService } from '../transaction-service';
import { Asset } from '../asset';
import { AssetService } from '../asset.service';
import { Router } from 'express';

@Component({
  selector: 'app-sell-dialog',
  standalone: true,
  imports: [MatDialogModule, CommonModule],
  templateUrl: './sell-dialog.component.html',
  styleUrl: './sell-dialog.component.css'
})
export class SellDialogComponent implements OnInit{
total: number;
  hasEnoughBalance: boolean;
  assetQuantity: number = 0;
  private currentUserId = 1;//Because we do not have security

constructor(@Inject(MAT_DIALOG_DATA) public data: { amount: number, price:number, user: User, cryptoName:string, symbol: string}, private transactionService:TransactionService, private assetService:AssetService){
  this.total = data.amount * data.price;
    this.hasEnoughBalance = data.user.freeBalance >= this.total;
}

ngOnInit(): void {
  this.loadAsset(this.data.cryptoName)
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

sellCrypto(){ 
  this.transaction={
    id:-1,
      type:"SELL",
      assetName:this.data.cryptoName,
      assetSymbol:this.data.symbol,
      quantity:this.data.amount,
      price:this.total,
      createdOn:null,
  
  }
  
 this.transactionService.sellTransaction(this.transaction, this.currentUserId)
 window.location.reload();
}

loadAsset(name:string) {
  this.assetService.findByName(name, this.currentUserId).subscribe({
    next: (data) => {
      this.assetQuantity = data?.quantity ?? 0;
    },
    error: (err) => {
      console.error('Error loading asset:', err);
      this.assetQuantity = 0;
    }
  });
}

get isInvalidInput(): boolean {
  return this.data.amount <= 0;
}

get ifThisAmountAvailable(): boolean{
  console.log(this.data.amount , this.assetQuantity)
  return this.data.amount <= this.assetQuantity;
}

}
