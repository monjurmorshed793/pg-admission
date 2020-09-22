import { Component, OnDestroy, OnInit } from '@angular/core';
import { HomeComponent } from 'app/home/home.component';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';

@Component({
  selector: 'pg-home-ext',
  templateUrl: './home-ext.component.html',
  styleUrls: ['./home-ext.component.scss'],
})
export class HomeExtComponent extends HomeComponent implements OnInit, OnDestroy {
  constructor(protected accountService: AccountService, protected loginModalService: LoginModalService) {
    super(accountService, loginModalService);
  }
}
