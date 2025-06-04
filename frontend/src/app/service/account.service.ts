import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private http: HttpClient) {

  }

  getBalance(): Observable<number> {
    return this.http.get<number>('/api/account/balance');
  }

  deposit(amount: number): Observable<string> {
    return this.http.post('/api/account/deposit', {amount}, {responseType: 'text'});
  }

}
