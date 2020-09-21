import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IJobExperience, JobExperience } from 'app/shared/model/job-experience.model';
import { JobExperienceService } from './job-experience.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IApplicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from 'app/entities/applicant/applicant.service';

@Component({
  selector: 'pg-job-experience-update',
  templateUrl: './job-experience-update.component.html',
})
export class JobExperienceUpdateComponent implements OnInit {
  isSaving = false;
  applicants: IApplicant[] = [];
  fromDateDp: any;
  toDateDp: any;

  editForm = this.fb.group({
    id: [],
    organizationName: [null, [Validators.required]],
    designation: [null, [Validators.required]],
    jobResponsibility: [null, [Validators.required]],
    fromDate: [null, [Validators.required]],
    toDate: [],
    currentlyWorking: [],
    applicant: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected jobExperienceService: JobExperienceService,
    protected applicantService: ApplicantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jobExperience }) => {
      this.updateForm(jobExperience);

      this.applicantService.query().subscribe((res: HttpResponse<IApplicant[]>) => (this.applicants = res.body || []));
    });
  }

  updateForm(jobExperience: IJobExperience): void {
    this.editForm.patchValue({
      id: jobExperience.id,
      organizationName: jobExperience.organizationName,
      designation: jobExperience.designation,
      jobResponsibility: jobExperience.jobResponsibility,
      fromDate: jobExperience.fromDate,
      toDate: jobExperience.toDate,
      currentlyWorking: jobExperience.currentlyWorking,
      applicant: jobExperience.applicant,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('pgadmissionApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jobExperience = this.createFromForm();
    if (jobExperience.id !== undefined) {
      this.subscribeToSaveResponse(this.jobExperienceService.update(jobExperience));
    } else {
      this.subscribeToSaveResponse(this.jobExperienceService.create(jobExperience));
    }
  }

  private createFromForm(): IJobExperience {
    return {
      ...new JobExperience(),
      id: this.editForm.get(['id'])!.value,
      organizationName: this.editForm.get(['organizationName'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      jobResponsibility: this.editForm.get(['jobResponsibility'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      toDate: this.editForm.get(['toDate'])!.value,
      currentlyWorking: this.editForm.get(['currentlyWorking'])!.value,
      applicant: this.editForm.get(['applicant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJobExperience>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IApplicant): any {
    return item.id;
  }
}
