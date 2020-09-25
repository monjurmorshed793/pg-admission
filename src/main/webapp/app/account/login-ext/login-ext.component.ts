import { AfterViewInit, Component, OnInit } from '@angular/core';
import { LoginModalComponent } from '../../shared/login/login.component';
import { LoginService } from '../../core/login/login.service';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'pg-login-ext',
  templateUrl: './login-ext.component.html',
  styleUrls: ['./login-ext.component.scss'],
})
export class LoginExtComponent extends LoginModalComponent implements AfterViewInit {
  constructor(loginService: LoginService, router: Router, public activeModal: NgbActiveModal, fb: FormBuilder) {
    super(loginService, router, activeModal, fb);
  }
}
