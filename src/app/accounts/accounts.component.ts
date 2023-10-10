import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountService} from "../services/account.service";
import {Observable} from "rxjs";
import {AccountDetails} from "../model/account.model";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit{
  accountFormGroup! : FormGroup;
  currentPage : number=0;
  pageSize : number=5;
  accountObservable! : Observable<AccountDetails>;
  operationFormGroup! : FormGroup;
  constructor(private fb:FormBuilder, private accountService:AccountService) {
  }
  ngOnInit(): void {
    this.accountFormGroup = this.fb.group({
      accountId : this.fb.control('')
    });
    this.operationFormGroup = this.fb.group({
      operationType : this.fb.control(null),
      operationAmount : this.fb.control(0),
      operationDescription : this.fb.control(null),
      accountDestination : this.fb.control(null)
    })
  }

  handleSearchAccount() {
    let accountId = this.accountFormGroup.value.accountId;
    this.accountObservable = this.accountService.getAccountOperations(accountId, this.currentPage, this.pageSize);
  }

  gotoPage(page: number) {
    this.currentPage  = page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    let accountId = this.accountFormGroup.value.accountId;
    let operationType = this.operationFormGroup.value.operationType;
    let operationAmount : number = this.operationFormGroup.value.operationAmount;
    let operationDescription : string = this.operationFormGroup.value.operationDescription;
    let accountDestination : string = this.operationFormGroup.value.accountDestination;
    if (operationType == 'DEBIT') {
      this.accountService.debit(accountId, operationAmount, operationDescription).subscribe({
        next : (data) => {
          alert("success debit");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error : err => {
          console.log(err);
        }
      })
    } else if (operationType == "CREDIT") {
      this.accountService.credit(accountId, operationAmount, operationDescription).subscribe({
        next : (data) => {
          alert("success credit");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        },
        error : err => {
          console.log(err);
        }
      })
    } else if (operationType == "TRANSFER") {
      this.accountService.transfer(accountId, accountDestination, operationAmount).subscribe({
        next : (data) => {
          alert("success transfer");
          this.operationFormGroup.reset();
          this.handleSearchAccount();
        }
      })
    }
  }
}
