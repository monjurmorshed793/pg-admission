import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamType } from 'app/shared/model/exam-type.model';
import { ExamTypeService } from './exam-type.service';

@Component({
  templateUrl: './exam-type-delete-dialog.component.html',
})
export class ExamTypeDeleteDialogComponent {
  examType?: IExamType;

  constructor(protected examTypeService: ExamTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('examTypeListModification');
      this.activeModal.close();
    });
  }
}
