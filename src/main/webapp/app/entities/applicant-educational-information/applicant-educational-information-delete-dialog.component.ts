import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicantEducationalInformation } from 'app/shared/model/applicant-educational-information.model';
import { ApplicantEducationalInformationService } from './applicant-educational-information.service';

@Component({
  templateUrl: './applicant-educational-information-delete-dialog.component.html',
})
export class ApplicantEducationalInformationDeleteDialogComponent {
  applicantEducationalInformation?: IApplicantEducationalInformation;

  constructor(
    protected applicantEducationalInformationService: ApplicantEducationalInformationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.applicantEducationalInformationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('applicantEducationalInformationListModification');
      this.activeModal.close();
    });
  }
}
