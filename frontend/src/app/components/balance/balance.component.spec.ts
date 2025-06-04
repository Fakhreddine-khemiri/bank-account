import {AccountService} from '../../service/account.service';
import {of} from 'rxjs';
import {BalanceComponent} from "./balance.component";

describe('BalanceComponent', () => {
  let component: BalanceComponent;
  let mockAccountService: jest.Mocked<AccountService>;

  beforeEach(() => {
    mockAccountService = {
      getBalance: jest.fn().mockReturnValue(of(250))
    } as unknown as jest.Mocked<AccountService>;

    component = new BalanceComponent(mockAccountService);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize balance and set loading to false', () => {
    component.ngOnInit();

    expect(mockAccountService.getBalance).toHaveBeenCalled();
    expect(component.balance()).toBe(250);
    expect(component.loading()).toBe(false);
  });
});
