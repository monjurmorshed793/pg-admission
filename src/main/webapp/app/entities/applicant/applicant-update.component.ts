import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IApplicant, Applicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from './applicant.service';

@Component({
  selector: 'jhi-applicant-update',
  templateUrl: './applicant-update.component.html',
})
export class ApplicantUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    applicationSerial: [null, [Validators.required]],
    status: [],
    appliedOn: [],
    applicationFeePaidOn: [],
    selectedRejectedOn: [],
  });

  constructor(protected applicantService: ApplicantService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicant }) => {
      if (!applicant.id) {
        const today = moment().startOf('day');
        applicant.appliedOn = today;
        applicant.applicationFeePaidOn = today;
        applicant.selectedRejectedOn = today;
      }

      this.updateForm(applicant);
    });
  }

  updateForm(applicant: IApplicant): void {
    this.editForm.patchValue({
      id: applicant.id,
      applicationSerial: applicant.applicationSerial,
      status: applicant.status,
      appliedOn: applicant.appliedOn ? applicant.appliedOn.format(DATE_TIME_FORMAT) : null,
      applicationFeePaidOn: applicant.applicationFeePaidOn ? applicant.applicationFeePaidOn.format(DATE_TIME_FORMAT) : null,
      selectedRejectedOn: applicant.selectedRejectedOn ? applicant.selectedRejectedOn.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicant = this.createFromForm();
    if (applicant.id !== undefined) {
      this.subscribeToSaveResponse(this.applicantService.update(applicant));
    } else {
      this.subscribeToSaveResponse(this.applicantService.create(applicant));
    }
  }

  private createFromForm(): IApplicant {
    return {
      ...new Applicant(),
      id: this.editForm.get(['id'])!.value,
      applicationSerial: this.editForm.get(['applicationSerial'])!.value,
      status: this.editForm.get(['status'])!.value,
      appliedOn: this.editForm.get(['appliedOn'])!.value ? moment(this.editForm.get(['appliedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      applicationFeePaidOn: this.editForm.get(['applicationFeePaidOn'])!.value
        ? moment(this.editForm.get(['applicationFeePaidOn'])!.value, DATE_TIME_FORMAT)
        : undefined,
      selectedRejectedOn: this.editForm.get(['selectedRejectedOn'])!.value
        ? moment(this.editForm.get(['selectedRejectedOn'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicant>>): void {
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
}
