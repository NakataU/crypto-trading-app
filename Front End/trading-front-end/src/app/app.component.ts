import { Component, NgZone, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { CryptoDialogComponent } from './crypto-dialog/crypto-dialog.component';
import { UserService } from './user.service';
import { User } from './user';
import { TransactionsDialogComponent } from './transactions-dialog/transactions-dialog.component';
import { AssetsDialogComponent } from './assets-dialog/assets-dialog.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  tickers: Record<string, string> = {};
  private socket!: WebSocket;
  currentUser!: User;
  private currentUserId = 1;//Because we do not have security

  private readonly pairs = [
    'BTC/USD', 'ETH/USD', 'SOL/USD', 'XRP/USD', 'DOGE/USD',
    'ADA/USD', 'DOT/USD', 'LTC/USD', 'AVAX/USD', 'BCH/USD',
    'TRX/USD', 'PEPE/USD', 'LINK/USD', 'ATOM/USD', 'XLM/USD',
    'ETC/USD', 'NEAR/USD', 'ICP/USD', 'FIL/USD', 'UNI/USD'
  ];

  private readonly symbolNameMap: Record<string, string> = {
    'BTC/USD': 'Bitcoin',
    'ETH/USD': 'Ethereum',
    'SOL/USD': 'Solana',
    'XRP/USD': 'Ripple',
    'DOGE/USD': 'Dogecoin',
    'ADA/USD': 'Cardano',
    'DOT/USD': 'Polkadot',
    'LTC/USD': 'Litecoin',
    'AVAX/USD': 'Avalanche',
    'BCH/USD': 'Bitcoin Cash',
    'TRX/USD': 'TRON',
    'PEPE/USD': 'Pepe',
    'LINK/USD': 'Chainlink',
    'ATOM/USD': 'Cosmos',
    'XLM/USD': 'Stellar',
    'ETC/USD': 'Ethereum Classic',
    'NEAR/USD': 'NEAR Protocol',
    'ICP/USD': 'Internet Computer',
    'FIL/USD': 'Filecoin',
    'UNI/USD': 'Uniswap'
  };

    constructor(private zone: NgZone,private dialog: MatDialog, private userService:UserService) {}


  ngOnInit(): void {
    console.log('[App] ngOnInit()');
    this.initSocket();
    this.loadUser(this.currentUserId);
  }

  ngOnDestroy(): void {
    console.log('[App] ngOnDestroy()');
    this.socket?.close();
  }

 
  private initSocket(): void {
    this.socket = new WebSocket('wss://ws.kraken.com/v2');
    console.log('[Socket] Created');

    
    this.socket.onopen = () => {
      console.log('[Socket] Connection opened');
      this.socket.send(JSON.stringify({
        method: 'subscribe',
        params: {
          channel: 'ticker',
          symbol: this.pairs
        }
      }));
    };
    
    this.socket.onerror = (e) =>
      console.error('[Socket] Error', e);

  
    this.socket.onclose = (ev) =>
      console.warn('[Socket] Connection closed', ev.reason);

  
    this.socket.onmessage = (ev) => this.handleMessage(ev.data);
  }

  private handleMessage(raw: string): void {
    try {
      const msg = JSON.parse(raw);
  
      if (msg.channel === 'ticker' && (msg.type === 'update' || msg.type === 'snapshot')) {
        const data = msg.data ?? [];
  
        for (const entry of data) {
          const symbol = entry.symbol;
          const last = entry.last;
  
          if (symbol && last) {
            const name = this.symbolNameMap[symbol] || 'Unknown';
            const price = last.toString();
  
            console.log(`[Ticker] ${name} (${symbol}): $${price}`);
  
            this.zone.run(() => {
              this.tickers[symbol] = price;
            });
          }
        }
      }
    } catch (err) {
      console.error('[Socket] JSON parse error', err);
    }
  }

  getName(symbol: string): string {
    return this.symbolNameMap[symbol] || 'Unknown';
  }

  openDialog(symbol:string, price:string){
    const name = this.getName(symbol)

  this.dialog.open(CryptoDialogComponent, {
    data: { name, symbol, price, user:this.currentUser }
  });
  }

  openAssetsDialog() {
    console.log("open assets")
    this.dialog.open(AssetsDialogComponent);
  }

  openTransactionDialog(){
    this.dialog.open(TransactionsDialogComponent)
  }

  loadUser(id:number): void {
    this.userService.findById(id).subscribe({
      next: (data) => {
        this.currentUser = data;
        console.log(this.currentUser);
      },
      error: (err) => {
        console.error('Error loading user:', err);
      }
    });
  }

  reset(id:number){
    console.log("reset")
    this.userService.resetUser(id);
    window.location.reload();
  }
}