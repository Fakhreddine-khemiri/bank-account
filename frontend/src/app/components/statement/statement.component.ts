import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {AccountService} from "../../service/account.service";
import {MatTableModule} from "@angular/material/table";
import {MatCardModule} from "@angular/material/card";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";

@Component({
  selector: 'bank-account-statement',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatCardModule, MatProgressSpinnerModule],
  template:`
    <mat-card>
      <h2>Transaction Statement</h2>

      <div *ngIf="loading" class="spinner-container">
        <mat-spinner></mat-spinner>
      </div>

      <table mat-table [dataSource]="transactions" *ngIf="!loading && transactions.length > 0" class="mat-elevation-z8">

        <ng-container matColumnDef="date">
          <th mat-header-cell *matHeaderCellDef>Date</th>
          <td mat-cell *matCellDef="let transaction">{{ transaction.date }}</td>
        </ng-container>

        <ng-container matColumnDef="type">
          <th mat-header-cell *matHeaderCellDef>Type</th>
          <td mat-cell *matCellDef="let transaction">{{ transaction.type }}</td>
        </ng-container>

        <ng-container matColumnDef="amount">
          <th mat-header-cell *matHeaderCellDef>Amount</th>
          <td mat-cell *matCellDef="let transaction">{{ transaction.amount | currency:'EUR' }}</td>
        </ng-container>

        <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
        <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
      </table>

      <p *ngIf="!loading && transactions.length === 0">No transactions found.</p>
    </mat-card>`,
  styleUrls:['statement.component.scss']

})
export class StatementComponent implements OnInit {
  transactions: any[] = [];
  displayedColumns = ['date', 'type', 'amount'];
  loading = true;

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getStatement().subscribe({
      next: (data) => {
        this.transactions = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
