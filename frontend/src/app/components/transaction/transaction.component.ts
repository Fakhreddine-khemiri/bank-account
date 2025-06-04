import {Component} from '@angular/core';
import {FormBuilder, Validators, ReactiveFormsModule, FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {AccountService} from "../../service/account.service";

@Component({
  selector: 'bank-account-deposit-withdraw',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatSnackBarModule,
    FormsModule
  ],
  template: `
    <mat-card>
      <h2>Transaction</h2>

      <mat-button-toggle-group [(ngModel)]="type" aria-label="Type">
        <mat-button-toggle value="deposit">Deposit</mat-button-toggle>
        <mat-button-toggle value="withdrawal">Withdraw</mat-button-toggle>
      </mat-button-toggle-group>

      <form [formGroup]="form" (ngSubmit)="onSubmit()">
        <mat-form-field appearance="fill">
          <mat-label>Amount</mat-label>
          <input matInput type="number" formControlName="amount"/>
        </mat-form-field>

        <button mat-raised-button color="primary" type="submit" [disabled]="form.invalid">
          Submit {{ type }}
        </button>
      </form>
    </mat-card>
  `,
})
export class TransactionComponent {
  type: 'deposit' | 'withdrawal' = 'deposit';

  form = this.fb.group({
    amount: [0, [Validators.required, Validators.min(0.01)]]
  });

  constructor(
    private accountService: AccountService,
    private snackBar: MatSnackBar,
    private fb: FormBuilder,
  ) {
  }

  onSubmit(): void {
    const amount = this.form.value.amount!;
    const handleSuccess = (msg: string) => {
      this.snackBar.open(msg, 'Close', { duration: 2000 });
    };
    const handleError = (err: any) => {
      this.snackBar.open(err?.error || 'Error occurred', 'Close', { duration: 3000 });
    };
    if (this.type === 'deposit') {
      this.accountService.deposit(amount).subscribe({
        next: handleSuccess,
        error: handleError
      });
    } else {
      this.accountService.withdraw(amount).subscribe({
        next: handleSuccess,
        error: handleError
      });
    }
  }
}
