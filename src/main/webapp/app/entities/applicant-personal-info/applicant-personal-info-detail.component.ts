import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';

@Component({
  selector: 'pg-applicant-personal-info-detail',
  templateUrl: './applicant-personal-info-detail.component.html',
})
export class ApplicantPersonalInfoDetailComponent implements OnInit {
  applicantPersonalInfo: IApplicantPersonalInfo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicantPersonalInfo }) => (this.applicantPersonalInfo = applicantPersonalInfo));
  }

  previousState(): void {
    window.history.back();
  }
}
