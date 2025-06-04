import { AccountService } from './account.service';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { firstValueFrom } from 'rxjs';

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
    expect(httpClientMock.get).toHaveBeenCalledWith('/api/account/balance');
  });
});
