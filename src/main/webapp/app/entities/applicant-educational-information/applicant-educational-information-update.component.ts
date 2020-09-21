import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import {
  IApplicantEducationalInformation,
  ApplicantEducationalInformation,
} from 'app/shared/model/applicant-educational-information.model';
import { ApplicantEducationalInformationService } from './applicant-educational-information.service';
import { IExamType } from 'app/shared/model/exam-type.model';
import { ExamTypeService } from 'app/entities/exam-type/exam-type.service';
import { IApplicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from 'app/entities/applicant/applicant.service';

type SelectableEntity = IExamType | IApplicant;

@Component({
  selector: 'jhi-applicant-educational-information-update',
  templateUrl: './applicant-educational-information-update.component.html',
})
export class ApplicantEducationalInformationUpdateComponent implements OnInit {
  isSaving = false;
  examtypes: IExamType[] = [];
  applicants: IApplicant[] = [];

  editForm = this.fb.group({
    id: [],
    instituteName: [null, [Validators.required]],
    board: [],
    totalMarksGrade: [],
    divisionClassGrade: [],
    passingYear: [null, [Validators.required]],
    examType: [null, Validators.required],
    applicant: [],
  });

  constructor(
    protected applicantEducationalInformationService: ApplicantEducationalInformationService,
    protected examTypeService: ExamTypeService,
    protected applicantService: ApplicantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicantEducationalInformation }) => {
      this.updateForm(applicantEducationalInformation);

      this.examTypeService.query().subscribe((res: HttpResponse<IExamType[]>) => (this.examtypes = res.body || []));

      this.applicantService.query().subscribe((res: HttpResponse<IApplicant[]>) => (this.applicants = res.body || []));
    });
  }

  updateForm(applicantEducationalInformation: IApplicantEducationalInformation): void {
    this.editForm.patchValue({
      id: applicantEducationalInformation.id,
      instituteName: applicantEducationalInformation.instituteName,
      board: applicantEducationalInformation.board,
      totalMarksGrade: applicantEducationalInformation.totalMarksGrade,
      divisionClassGrade: applicantEducationalInformation.divisionClassGrade,
      passingYear: applicantEducationalInformation.passingYear,
      examType: applicantEducationalInformation.examType,
      applicant: applicantEducationalInformation.applicant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicantEducationalInformation = this.createFromForm();
    if (applicantEducationalInformation.id !== undefined) {
      this.subscribeToSaveResponse(this.applicantEducationalInformationService.update(applicantEducationalInformation));
    } else {
      this.subscribeToSaveResponse(this.applicantEducationalInformationService.create(applicantEducationalInformation));
    }
  }

  private createFromForm(): IApplicantEducationalInformation {
    return {
      ...new ApplicantEducationalInformation(),
      id: this.editForm.get(['id'])!.value,
      instituteName: this.editForm.get(['instituteName'])!.value,
      board: this.editForm.get(['board'])!.value,
      totalMarksGrade: this.editForm.get(['totalMarksGrade'])!.value,
      divisionClassGrade: this.editForm.get(['divisionClassGrade'])!.value,
      passingYear: this.editForm.get(['passingYear'])!.value,
      examType: this.editForm.get(['examType'])!.value,
      applicant: this.editForm.get(['applicant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicantEducationalInformation>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
