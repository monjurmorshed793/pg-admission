import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISemester, Semester } from 'app/shared/model/semester.model';
import { SemesterService } from './semester.service';

@Component({
  selector: 'pg-semester-update',
  templateUrl: './semester-update.component.html',
})
export class SemesterUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    semesterName: [null, [Validators.required]],
    isActive: [null, [Validators.required]],
  });

  constructor(protected semesterService: SemesterService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ semester }) => {
      this.updateForm(semester);
    });
  }

  updateForm(semester: ISemester): void {
    this.editForm.patchValue({
      id: semester.id,
      semesterName: semester.semesterName,
      isActive: semester.isActive,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const semester = this.createFromForm();
    if (semester.id !== undefined) {
      this.subscribeToSaveResponse(this.semesterService.update(semester));
    } else {
      this.subscribeToSaveResponse(this.semesterService.create(semester));
    }
  }

  private createFromForm(): ISemester {
    return {
      ...new Semester(),
      id: this.editForm.get(['id'])!.value,
      semesterName: this.editForm.get(['semesterName'])!.value,
      isActive: this.editForm.get(['isActive'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISemester>>): void {
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
