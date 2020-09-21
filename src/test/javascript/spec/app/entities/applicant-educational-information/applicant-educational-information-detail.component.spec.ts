import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantEducationalInformationDetailComponent } from 'app/entities/applicant-educational-information/applicant-educational-information-detail.component';
import { ApplicantEducationalInformation } from 'app/shared/model/applicant-educational-information.model';

describe('Component Tests', () => {
  describe('ApplicantEducationalInformation Management Detail Component', () => {
    let comp: ApplicantEducationalInformationDetailComponent;
    let fixture: ComponentFixture<ApplicantEducationalInformationDetailComponent>;
    const route = ({ data: of({ applicantEducationalInformation: new ApplicantEducationalInformation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantEducationalInformationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApplicantEducationalInformationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicantEducationalInformationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applicantEducationalInformation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicantEducationalInformation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
