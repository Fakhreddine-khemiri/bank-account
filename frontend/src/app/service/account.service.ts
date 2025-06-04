import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  constructor(httpClient: HttpClient) {

  }
  getBalance = (): Observable<number> => of(0);


}
