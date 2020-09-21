import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExamType, ExamType } from 'app/shared/model/exam-type.model';
import { ExamTypeService } from './exam-type.service';

@Component({
  selector: 'jhi-exam-type-update',
  templateUrl: './exam-type-update.component.html',
})
export class ExamTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    examTypeCode: [null, [Validators.required]],
    examTypeName: [null, [Validators.required]],
  });

  constructor(protected examTypeService: ExamTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examType }) => {
      this.updateForm(examType);
    });
  }

  updateForm(examType: IExamType): void {
    this.editForm.patchValue({
      id: examType.id,
      examTypeCode: examType.examTypeCode,
      examTypeName: examType.examTypeName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examType = this.createFromForm();
    if (examType.id !== undefined) {
      this.subscribeToSaveResponse(this.examTypeService.update(examType));
    } else {
      this.subscribeToSaveResponse(this.examTypeService.create(examType));
    }
  }

  private createFromForm(): IExamType {
    return {
      ...new ExamType(),
      id: this.editForm.get(['id'])!.value,
      examTypeCode: this.editForm.get(['examTypeCode'])!.value,
      examTypeName: this.editForm.get(['examTypeName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamType>>): void {
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
