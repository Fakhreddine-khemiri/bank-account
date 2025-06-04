import { StatementComponent } from './statement.component';
import { AccountService } from '../../service/account.service';
import { of } from 'rxjs';

describe('StatementComponent', () => {
  let component: StatementComponent;
  let mockAccountService: jest.Mocked<AccountService>;

  beforeEach(() => {
    mockAccountService = {
      getStatement: jest.fn().mockReturnValue(
          of([
            { date: '2025-06-01', type: 'deposit', amount: 200 },
            { date: '2025-06-02', type: 'withdrawal', amount: 50 }
          ])
      )
    } as unknown as jest.Mocked<AccountService>;

    component = new StatementComponent(mockAccountService);
  });

  it('should fetch and store the transactions', async() => {
    await component.ngOnInit();
    expect(component.transactions.length).toBe(2);
    expect(component.transactions[0].type).toBe('deposit');
  });
});
