import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountDetails} from "../model/account.model";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  backendHost : string='http://localhost:8085';
  constructor(private http : HttpClient) { }
  public getAccountOperations(accountId : String, page : number, pageSize : number) : Observable<AccountDetails> {
    return this.http.get<AccountDetails>(this.backendHost + "/accounts/" + accountId + "/pageOperations?page=" + page + "&pageSize=" + pageSize);
  }
  public debit(accountId : string, amount : number, description : string) {
    let data = {accountId, amount, description};
    return this.http.post(this.backendHost + "/accounts/debit", data);
  }
  public credit(accountId : string, amount : number, description : string) {
    let data = {accountId : accountId, amount : amount, description : description};
    return this.http.post(this.backendHost + "/accounts/credit", data);
  }
  public transfer(accountSource : string, accountDestination : string, amount : number) {
    let data = {accountSource, accountDestination, amount};
    return this.http.post(this.backendHost + "/accounts/transfer", data);
  }
}
