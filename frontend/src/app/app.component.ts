import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, MatToolbarModule, MatButtonModule],
  template: `
    <mat-toolbar color="primary">
      <button mat-button routerLink="/balance">Balance</button>
      <button mat-button routerLink="/deposit-withdraw">Deposit/Withdraw</button>
      <button mat-button routerLink="/statement">Statement</button>
    </mat-toolbar>
    <router-outlet></router-outlet>
  `,
})
export class AppComponent {}
