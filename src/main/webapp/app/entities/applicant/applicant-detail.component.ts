import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicant } from 'app/shared/model/applicant.model';

@Component({
  selector: 'jhi-applicant-detail',
  templateUrl: './applicant-detail.component.html',
})
export class ApplicantDetailComponent implements OnInit {
  applicant: IApplicant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicant }) => (this.applicant = applicant));
  }

  previousState(): void {
    window.history.back();
  }
}
