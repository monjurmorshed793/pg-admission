import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicantAddress } from 'app/shared/model/applicant-address.model';

@Component({
  selector: 'pg-applicant-address-detail',
  templateUrl: './applicant-address-detail.component.html',
})
export class ApplicantAddressDetailComponent implements OnInit {
  applicantAddress: IApplicantAddress | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicantAddress }) => (this.applicantAddress = applicantAddress));
  }

  previousState(): void {
    window.history.back();
  }
}
