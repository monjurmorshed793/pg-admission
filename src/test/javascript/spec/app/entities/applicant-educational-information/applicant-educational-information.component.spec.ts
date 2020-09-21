import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantEducationalInformationComponent } from 'app/entities/applicant-educational-information/applicant-educational-information.component';
import { ApplicantEducationalInformationService } from 'app/entities/applicant-educational-information/applicant-educational-information.service';
import { ApplicantEducationalInformation } from 'app/shared/model/applicant-educational-information.model';

describe('Component Tests', () => {
  describe('ApplicantEducationalInformation Management Component', () => {
    let comp: ApplicantEducationalInformationComponent;
    let fixture: ComponentFixture<ApplicantEducationalInformationComponent>;
    let service: ApplicantEducationalInformationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantEducationalInformationComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ApplicantEducationalInformationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicantEducationalInformationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicantEducationalInformationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApplicantEducationalInformation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.applicantEducationalInformations && comp.applicantEducationalInformations[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApplicantEducationalInformation(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.applicantEducationalInformations && comp.applicantEducationalInformations[0]).toEqual(
        jasmine.objectContaining({ id: 123 })
      );
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
