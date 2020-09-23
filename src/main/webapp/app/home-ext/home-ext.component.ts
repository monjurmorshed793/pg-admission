import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { HomeComponent } from 'app/home/home.component';

@Component({
  selector: 'pg-home-ext',
  templateUrl: './home-ext.component.html',
  styleUrls: ['./home-ext.scss'],
})
export class HomeExtComponent extends HomeComponent implements OnInit, OnDestroy {
  constructor(protected accountService: AccountService, protected loginModalService: LoginModalService) {
    super(accountService, loginModalService);
  }
}
