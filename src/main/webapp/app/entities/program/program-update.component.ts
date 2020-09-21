import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProgram, Program } from 'app/shared/model/program.model';
import { ProgramService } from './program.service';

@Component({
  selector: 'jhi-program-update',
  templateUrl: './program-update.component.html',
})
export class ProgramUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    programNameShort: [null, [Validators.required]],
    programName: [null, [Validators.required, Validators.maxLength(500)]],
  });

  constructor(protected programService: ProgramService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ program }) => {
      this.updateForm(program);
    });
  }

  updateForm(program: IProgram): void {
    this.editForm.patchValue({
      id: program.id,
      programNameShort: program.programNameShort,
      programName: program.programName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const program = this.createFromForm();
    if (program.id !== undefined) {
      this.subscribeToSaveResponse(this.programService.update(program));
    } else {
      this.subscribeToSaveResponse(this.programService.create(program));
    }
  }

  private createFromForm(): IProgram {
    return {
      ...new Program(),
      id: this.editForm.get(['id'])!.value,
      programNameShort: this.editForm.get(['programNameShort'])!.value,
      programName: this.editForm.get(['programName'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgram>>): void {
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
