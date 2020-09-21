import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantPersonalInfoDetailComponent } from 'app/entities/applicant-personal-info/applicant-personal-info-detail.component';
import { ApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';

describe('Component Tests', () => {
  describe('ApplicantPersonalInfo Management Detail Component', () => {
    let comp: ApplicantPersonalInfoDetailComponent;
    let fixture: ComponentFixture<ApplicantPersonalInfoDetailComponent>;
    const route = ({ data: of({ applicantPersonalInfo: new ApplicantPersonalInfo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantPersonalInfoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApplicantPersonalInfoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicantPersonalInfoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applicantPersonalInfo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicantPersonalInfo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
