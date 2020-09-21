import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationDeadline } from 'app/shared/model/application-deadline.model';
import { ApplicationDeadlineService } from './application-deadline.service';

@Component({
  templateUrl: './application-deadline-delete-dialog.component.html',
})
export class ApplicationDeadlineDeleteDialogComponent {
  applicationDeadline?: IApplicationDeadline;

  constructor(
    protected applicationDeadlineService: ApplicationDeadlineService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicationDeadlineService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applicationDeadlineListModification');
      this.activeModal.close();
    });
  }
}
