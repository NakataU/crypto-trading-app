import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { AssetService } from '../asset.service';
import { FormsModule } from '@angular/forms';
import { BuyDialogComponent } from '../buy-dialog/buy-sell-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from '../user';
import { SellDialogComponent } from '../sell-dialog/sell-dialog.component';


@Component({
  selector: 'app-crypto-dialog',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './crypto-dialog.component.html',
  styleUrl: './crypto-dialog.component.css'
})
export class CryptoDialogComponent implements OnInit{
  assetQuantity: number = 0;
  private currentUserId = 1;//Because we do not have security

  constructor(@Inject(MAT_DIALOG_DATA) public data: { name: string, symbol: string, price: string, user: User }, private assetService:AssetService, private dialog:MatDialog,private snackBar: MatSnackBar) {}
  
  ngOnInit(): void {
    this.loadAsset(this.data.name);
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

  openPopUp(buyData:HTMLInputElement, price:string){
    const amount = buyData.value;
    this.dialog.open(BuyDialogComponent, {
        width: '300px',
        data: { amount: amount, price: parseFloat(price),  user: this.data.user, cryptoName:this.data.name, symbol:this.data.symbol}
  })
}

  openSellDialog(sellData:HTMLInputElement, price:string){
    const amount = sellData.value;
    this.dialog.open(SellDialogComponent, {
      width: '300px',
      data: { amount: amount, price: parseFloat(price),  user: this.data.user, cryptoName:this.data.name, symbol:this.data.symbol}
  })
}
  
  
  
}
