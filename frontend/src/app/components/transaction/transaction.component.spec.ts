import { TransactionComponent } from './transaction.component';
import { FormBuilder } from '@angular/forms';
import { AccountService } from '../../service/account.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { of, throwError } from 'rxjs';

describe('TransactionComponent', () => {
  let component: TransactionComponent;
  let mockAccountService: jest.Mocked<AccountService>;
  let mockSnackBar: jest.Mocked<MatSnackBar>;

  beforeEach(() => {
    mockAccountService = {
      deposit: jest.fn(),
      withdraw: jest.fn()
    } as unknown as jest.Mocked<AccountService>;

    mockSnackBar = {
      open: jest.fn()
    } as unknown as jest.Mocked<MatSnackBar>;

    component = new TransactionComponent(
      mockAccountService,
      mockSnackBar,
      new FormBuilder()
    );
  });

  it('should call deposit and show success message', () => {
    component.type = 'deposit';
    component.form.setValue({ amount: 100 });
    mockAccountService.deposit.mockReturnValue(of('Deposit successful'));

    component.onSubmit();

    expect(mockAccountService.deposit).toHaveBeenCalledWith(100);
    expect(mockSnackBar.open).toHaveBeenCalledWith('Deposit successful', 'Close', { duration: 2000 });
  });

  it('should call withdraw and show success message', () => {
    component.type = 'withdrawal';
    component.form.setValue({ amount: 50 });
    mockAccountService.withdraw.mockReturnValue(of('Withdrawal successful'));

    component.onSubmit();

    expect(mockAccountService.withdraw).toHaveBeenCalledWith(50);
    expect(mockSnackBar.open).toHaveBeenCalledWith('Withdrawal successful', 'Close', { duration: 2000 });
  });

  it('should show error message if deposit fails', () => {
    component.type = 'deposit';
    component.form.setValue({ amount: 100 });
    mockAccountService.deposit.mockReturnValue(throwError(() => ({ error: 'Deposit failed' })));

    component.onSubmit();

    expect(mockSnackBar.open).toHaveBeenCalledWith('Deposit failed', 'Close', { duration: 3000 });
  });

  it('should show default error message if no error provided', () => {
    component.type = 'withdrawal';
    component.form.setValue({ amount: 100 });
    mockAccountService.withdraw.mockReturnValue(throwError(() => ({})));

    component.onSubmit();

    expect(mockSnackBar.open).toHaveBeenCalledWith('Error occurred', 'Close', { duration: 3000 });
  });
});
