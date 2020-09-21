import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicantEducationalInformation } from 'app/shared/model/applicant-educational-information.model';

@Component({
  selector: 'pg-applicant-educational-information-detail',
  templateUrl: './applicant-educational-information-detail.component.html',
})
export class ApplicantEducationalInformationDetailComponent implements OnInit {
  applicantEducationalInformation: IApplicantEducationalInformation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ applicantEducationalInformation }) => (this.applicantEducationalInformation = applicantEducationalInformation)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
