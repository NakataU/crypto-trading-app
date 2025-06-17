import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Transaction } from '../transaction';
import { TransactionService } from '../transaction-service';

@Component({
  selector: 'app-transactions-dialog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transactions-dialog.component.html',
  styleUrl: './transactions-dialog.component.css'
})
export class TransactionsDialogComponent implements OnInit{
transactions: Transaction[] = [];
  private currentUserId = 1;//Because we do not have security

  constructor(private transactionService: TransactionService) {}

  ngOnInit(): void {
    this.loadUserTransactions(this.currentUserId)
  }


loadUserTransactions(id: number): void {
  this.transactionService.findAllByUser(id).subscribe({
    next: (data) => {
      this.transactions = data.content;
    },
    error: (err) => {
      console.error('Error loading transactions:', err);
    }
  });
}
}
