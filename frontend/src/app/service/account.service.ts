import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private baseUrl = '/api/account';
  constructor(private http: HttpClient) {

  }

  getBalance(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/balance`);
  }

  deposit(amount: number): Observable<string> {
    return this.http.post(`${this.baseUrl}/deposit`, {amount}, {responseType: 'text'});
  }

}
