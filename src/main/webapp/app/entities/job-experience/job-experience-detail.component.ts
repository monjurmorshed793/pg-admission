import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IJobExperience } from 'app/shared/model/job-experience.model';

@Component({
  selector: 'pg-job-experience-detail',
  templateUrl: './job-experience-detail.component.html',
})
export class JobExperienceDetailComponent implements OnInit {
  jobExperience: IJobExperience | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobExperience }) => (this.jobExperience = jobExperience));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
