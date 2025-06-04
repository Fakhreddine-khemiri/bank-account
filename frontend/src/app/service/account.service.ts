import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export interface TransactionResponse {
  date: string;
  type: 'DEPOSIT' | 'WITHDRAWAL';
  amount: number;
  balanceAfter: number;
}

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private baseUrl = 'http://localhost:8080/api/account';

  constructor(private http: HttpClient) {

  }

  getBalance(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/balance`);
  }

  deposit(amount: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/deposit`, {amount}, {responseType: 'text'});
  }

  withdraw(amount: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/withdrawal`, {amount}, {responseType: 'text'});
  }

  getStatement(): Observable<TransactionResponse[]> {
    return this.http.get<TransactionResponse[]>(`${this.baseUrl}/statement`);
  }
}
