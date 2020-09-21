import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicant } from 'app/shared/model/applicant.model';
import { ApplicantService } from './applicant.service';

@Component({
  templateUrl: './applicant-delete-dialog.component.html',
})
export class ApplicantDeleteDialogComponent {
  applicant?: IApplicant;

  constructor(protected applicantService: ApplicantService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicantService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applicantListModification');
      this.activeModal.close();
    });
  }
}
