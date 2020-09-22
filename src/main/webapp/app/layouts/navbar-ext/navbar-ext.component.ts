import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from 'app/layouts/navbar/navbar.component';
import { LoginService } from 'app/core/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { Router } from '@angular/router';
import { VERSION } from 'app/app.constants';

@Component({
  selector: 'pg-navbar-ext',
  templateUrl: './navbar-ext.component.html',
  styleUrls: ['./navbar-ext.component.scss'],
})
export class NavbarExtComponent extends NavbarComponent implements OnInit {
  constructor(
    loginService: LoginService,
    accountService: AccountService,
    loginModalService: LoginModalService,
    profileService: ProfileService,
    router: Router
  ) {
    super(loginService, accountService, loginModalService, profileService, router);
  }

  ngOnInit(): void {}
}
