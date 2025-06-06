import {AccountService, TransactionResponse} from './account.service';
import {HttpClient} from '@angular/common/http';
import {of} from 'rxjs';
import {firstValueFrom} from 'rxjs';

describe('AccountService', () => {
  let httpClientMock: Partial<Record<keyof HttpClient, jest.Mock>>;
  let service: AccountService;

  beforeEach(() => {
    httpClientMock = {
      get: jest.fn(),
      post: jest.fn(),
    };
    service = new AccountService(httpClientMock as unknown as HttpClient);
  });

  it('should return the current balance', async () => {
    httpClientMock.get!.mockReturnValue(of(100));

    const result = await firstValueFrom(service.getBalance());

    expect(result).toBe(100);
    expect(httpClientMock.get).toHaveBeenCalledWith('http://localhost:8080/api/account/balance');
  });

  it('should send a deposit request', async () => {
    const mockResponse = 'Deposit successful. New balance: 200';
    httpClientMock.post!.mockReturnValue(of(mockResponse));

    const result = await firstValueFrom(service.deposit(50));

    expect(result).toBe(mockResponse);
    expect(httpClientMock.post).toHaveBeenCalledWith(
      'http://localhost:8080/api/account/deposit',
      {amount: 50},
      {responseType: 'text'}
    );
  });

  it('should send a withdrawal request', async () => {
    const mockResponse = 'Withdrawal successful. New balance: 150';
    httpClientMock.post!.mockReturnValue(of(mockResponse));

    const result = await firstValueFrom(service.withdraw(30));

    expect(result).toBe(mockResponse);
    expect(httpClientMock.post).toHaveBeenCalledWith(
      'http://localhost:8080/api/account/withdrawal',
      {amount: 30},
      {responseType: 'text'}
    );
  });


  it('should return a list of transactions', async () => {
    const mockTransactions: TransactionResponse[] = [
      {date: '2024-06-01', type: 'DEPOSIT', amount: 100, balanceAfter: 100},
      {date: '2024-06-02', type: 'WITHDRAWAL', amount: 50, balanceAfter: 50}
    ];
    httpClientMock.get!.mockReturnValue(of(mockTransactions));

    const result = await firstValueFrom(service.getStatement());

    expect(result).toEqual(mockTransactions);
    expect(httpClientMock.get).toHaveBeenCalledWith('http://localhost:8080/api/account/statement');
  });
});
