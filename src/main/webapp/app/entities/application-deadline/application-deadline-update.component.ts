import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IApplicationDeadline, ApplicationDeadline } from 'app/shared/model/application-deadline.model';
import { ApplicationDeadlineService } from './application-deadline.service';
import { ISemester } from 'app/shared/model/semester.model';
import { SemesterService } from 'app/entities/semester/semester.service';
import { IProgram } from 'app/shared/model/program.model';
import { ProgramService } from 'app/entities/program/program.service';

type SelectableEntity = ISemester | IProgram;

@Component({
  selector: 'jhi-application-deadline-update',
  templateUrl: './application-deadline-update.component.html',
})
export class ApplicationDeadlineUpdateComponent implements OnInit {
  isSaving = false;
  semesters: ISemester[] = [];
  programs: IProgram[] = [];

  editForm = this.fb.group({
    id: [],
    fromDate: [null, [Validators.required]],
    toDate: [null, [Validators.required]],
    semester: [null, Validators.required],
    program: [null, Validators.required],
  });

  constructor(
    protected applicationDeadlineService: ApplicationDeadlineService,
    protected semesterService: SemesterService,
    protected programService: ProgramService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ applicationDeadline }) => {
      if (!applicationDeadline.id) {
        const today = moment().startOf('day');
        applicationDeadline.fromDate = today;
        applicationDeadline.toDate = today;
      }

      this.updateForm(applicationDeadline);

      this.semesterService.query().subscribe((res: HttpResponse<ISemester[]>) => (this.semesters = res.body || []));

      this.programService.query().subscribe((res: HttpResponse<IProgram[]>) => (this.programs = res.body || []));
    });
  }

  updateForm(applicationDeadline: IApplicationDeadline): void {
    this.editForm.patchValue({
      id: applicationDeadline.id,
      fromDate: applicationDeadline.fromDate ? applicationDeadline.fromDate.format(DATE_TIME_FORMAT) : null,
      toDate: applicationDeadline.toDate ? applicationDeadline.toDate.format(DATE_TIME_FORMAT) : null,
      semester: applicationDeadline.semester,
      program: applicationDeadline.program,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const applicationDeadline = this.createFromForm();
    if (applicationDeadline.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationDeadlineService.update(applicationDeadline));
    } else {
      this.subscribeToSaveResponse(this.applicationDeadlineService.create(applicationDeadline));
    }
  }

  private createFromForm(): IApplicationDeadline {
    return {
      ...new ApplicationDeadline(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value ? moment(this.editForm.get(['fromDate'])!.value, DATE_TIME_FORMAT) : undefined,
      toDate: this.editForm.get(['toDate'])!.value ? moment(this.editForm.get(['toDate'])!.value, DATE_TIME_FORMAT) : undefined,
      semester: this.editForm.get(['semester'])!.value,
      program: this.editForm.get(['program'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationDeadline>>): void {
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
