import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IApplicantAddress, ApplicantAddress } from 'app/shared/model/applicant-address.model';
import { ApplicantAddressService } from './applicant-address.service';
import { IApplicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from 'app/entities/applicant/applicant.service';

@Component({
  selector: 'jhi-applicant-address-update',
  templateUrl: './applicant-address-update.component.html',
})
export class ApplicantAddressUpdateComponent implements OnInit {
  isSaving = false;
  applicants: IApplicant[] = [];

  editForm = this.fb.group({
    id: [],
    applicationSerial: [],
    addressType: [null, [Validators.required]],
    thanaOther: [],
    line1: [null, [Validators.required, Validators.maxLength(100)]],
    line2: [null, [Validators.maxLength(100)]],
    applicant: [],
  });

  constructor(
    protected applicantAddressService: ApplicantAddressService,
    protected applicantService: ApplicantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicantAddress }) => {
      this.updateForm(applicantAddress);

      this.applicantService.query().subscribe((res: HttpResponse<IApplicant[]>) => (this.applicants = res.body || []));
    });
  }

  updateForm(applicantAddress: IApplicantAddress): void {
    this.editForm.patchValue({
      id: applicantAddress.id,
      applicationSerial: applicantAddress.applicationSerial,
      addressType: applicantAddress.addressType,
      thanaOther: applicantAddress.thanaOther,
      line1: applicantAddress.line1,
      line2: applicantAddress.line2,
      applicant: applicantAddress.applicant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicantAddress = this.createFromForm();
    if (applicantAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.applicantAddressService.update(applicantAddress));
    } else {
      this.subscribeToSaveResponse(this.applicantAddressService.create(applicantAddress));
    }
  }

  private createFromForm(): IApplicantAddress {
    return {
      ...new ApplicantAddress(),
      id: this.editForm.get(['id'])!.value,
      applicationSerial: this.editForm.get(['applicationSerial'])!.value,
      addressType: this.editForm.get(['addressType'])!.value,
      thanaOther: this.editForm.get(['thanaOther'])!.value,
      line1: this.editForm.get(['line1'])!.value,
      line2: this.editForm.get(['line2'])!.value,
      applicant: this.editForm.get(['applicant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicantAddress>>): void {
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
