import {Component, OnInit, signal} from '@angular/core';
import {AccountService} from "../../service/account.service";
import {CurrencyPipe, NgIf} from "@angular/common";

@Component({
  selector: 'bank-account-home',
  standalone: true,
  imports: [
    CurrencyPipe,
    NgIf
  ],
  template: `
    <div *ngIf="loading(); else balanceTpl">
      <p>Loading...</p>
    </div>

    <ng-template #balanceTpl>
      <h2>Current Balance</h2>
      <p>{{ balance() | currency: 'EUR' }}</p>
    </ng-template>`
})
export class HomeComponent implements OnInit {
  constructor(private accountService: AccountService) {
  }

  balance = signal<number | null>(null);
  loading = signal(true);

  ngOnInit(): void {
    this.accountService.getBalance().subscribe({
      next: (value) => {
        this.balance.set(value);
        this.loading.set(false);
      }
    });
  }
}
