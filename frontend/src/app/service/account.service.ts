import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(private http: HttpClient) {

  }

  getBalance(): Observable<number> {
    return this.http.get<number>('/api/account/balance');
  }

  deposit(number: number): Observable<string> {
    return of("");
  }
}
