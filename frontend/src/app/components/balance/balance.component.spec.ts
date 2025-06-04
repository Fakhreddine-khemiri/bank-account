import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BalanceComponent } from './balance.component';
import {AccountService} from "../../service/account.service";
import {of} from "rxjs";
import {By} from "@angular/platform-browser";

describe('HomeComponent', () => {
  let component: BalanceComponent;
  let fixture: ComponentFixture<BalanceComponent>;
  let accountServiceMock: jest.Mocked<AccountService>;

  beforeEach(() => {
    accountServiceMock = {
      getBalance: jest.fn()
    } as any;
    TestBed.configureTestingModule({
      imports: [BalanceComponent],
      providers: [{ provide: AccountService, useValue: accountServiceMock }]
    });
    fixture = TestBed.createComponent(BalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should display the balance when service returns value', async () => {
    accountServiceMock.getBalance.mockReturnValue(of(250));
    fixture.detectChanges();
    await fixture.whenStable();

    const text = fixture.debugElement.query(By.css('p')).nativeElement.textContent;
    expect(text).toContain('€250.00');
  });
});
