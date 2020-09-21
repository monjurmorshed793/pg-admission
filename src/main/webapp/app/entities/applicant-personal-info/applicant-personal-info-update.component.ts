import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IApplicantPersonalInfo, ApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';
import { ApplicantPersonalInfoService } from './applicant-personal-info.service';
import { IApplicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from 'app/entities/applicant/applicant.service';

@Component({
  selector: 'jhi-applicant-personal-info-update',
  templateUrl: './applicant-personal-info-update.component.html',
})
export class ApplicantPersonalInfoUpdateComponent implements OnInit {
  isSaving = false;
  applicants: IApplicant[] = [];
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    applicationSerial: [],
    fullName: [null, [Validators.required, Validators.maxLength(500)]],
    firstName: [null, [Validators.required]],
    middleName: [],
    lastName: [],
    fatherName: [null, [Validators.required]],
    motherName: [null, [Validators.required]],
    gender: [null, [Validators.required]],
    religion: [null, [Validators.required]],
    nationality: [null, [Validators.required]],
    dateOfBirth: [null, [Validators.required]],
    placeOfBirth: [null, [Validators.maxLength(500)]],
    mobileNumber: [null, [Validators.required]],
    emailAddress: [null, [Validators.required]],
    maritalStatus: [null, [Validators.required]],
    applicant: [],
  });

  constructor(
    protected applicantPersonalInfoService: ApplicantPersonalInfoService,
    protected applicantService: ApplicantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicantPersonalInfo }) => {
      this.updateForm(applicantPersonalInfo);

      this.applicantService
        .query({ filter: 'applicantpersonalinformation-is-null' })
        .pipe(
          map((res: HttpResponse<IApplicant[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IApplicant[]) => {
          if (!applicantPersonalInfo.applicant || !applicantPersonalInfo.applicant.id) {
            this.applicants = resBody;
          } else {
            this.applicantService
              .find(applicantPersonalInfo.applicant.id)
              .pipe(
                map((subRes: HttpResponse<IApplicant>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IApplicant[]) => (this.applicants = concatRes));
          }
        });
    });
  }

  updateForm(applicantPersonalInfo: IApplicantPersonalInfo): void {
    this.editForm.patchValue({
      id: applicantPersonalInfo.id,
      applicationSerial: applicantPersonalInfo.applicationSerial,
      fullName: applicantPersonalInfo.fullName,
      firstName: applicantPersonalInfo.firstName,
      middleName: applicantPersonalInfo.middleName,
      lastName: applicantPersonalInfo.lastName,
      fatherName: applicantPersonalInfo.fatherName,
      motherName: applicantPersonalInfo.motherName,
      gender: applicantPersonalInfo.gender,
      religion: applicantPersonalInfo.religion,
      nationality: applicantPersonalInfo.nationality,
      dateOfBirth: applicantPersonalInfo.dateOfBirth,
      placeOfBirth: applicantPersonalInfo.placeOfBirth,
      mobileNumber: applicantPersonalInfo.mobileNumber,
      emailAddress: applicantPersonalInfo.emailAddress,
      maritalStatus: applicantPersonalInfo.maritalStatus,
      applicant: applicantPersonalInfo.applicant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicantPersonalInfo = this.createFromForm();
    if (applicantPersonalInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.applicantPersonalInfoService.update(applicantPersonalInfo));
    } else {
      this.subscribeToSaveResponse(this.applicantPersonalInfoService.create(applicantPersonalInfo));
    }
  }

  private createFromForm(): IApplicantPersonalInfo {
    return {
      ...new ApplicantPersonalInfo(),
      id: this.editForm.get(['id'])!.value,
      applicationSerial: this.editForm.get(['applicationSerial'])!.value,
      fullName: this.editForm.get(['fullName'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      middleName: this.editForm.get(['middleName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      fatherName: this.editForm.get(['fatherName'])!.value,
      motherName: this.editForm.get(['motherName'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      religion: this.editForm.get(['religion'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      placeOfBirth: this.editForm.get(['placeOfBirth'])!.value,
      mobileNumber: this.editForm.get(['mobileNumber'])!.value,
      emailAddress: this.editForm.get(['emailAddress'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      applicant: this.editForm.get(['applicant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicantPersonalInfo>>): void {
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
