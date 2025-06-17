import { Component, OnInit } from '@angular/core';
import { Asset } from '../asset';
import { AssetService } from '../asset.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-assets-dialog',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './assets-dialog.component.html',
  styleUrl: './assets-dialog.component.css'
})
export class AssetsDialogComponent implements OnInit{
  assets: Asset[] = [];
  private currentUserId = 1;//Because we do not have security

  constructor(private assetService: AssetService) {}

  ngOnInit(): void {
    this.loadUserAssets(this.currentUserId)

  }


loadUserAssets(id: number): void {
  this.assetService.findAllUserAssets(id).subscribe({
    next: (data) => {
      this.assets = data.content;
    },
    error: (err) => {
      console.error('Error loading assets:', err);
    }
  });
}
  
}
