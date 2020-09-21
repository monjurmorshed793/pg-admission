import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicantPersonalInfoComponent } from 'app/entities/applicant-personal-info/applicant-personal-info.component';
import { ApplicantPersonalInfoService } from 'app/entities/applicant-personal-info/applicant-personal-info.service';
import { ApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';

describe('Component Tests', () => {
  describe('ApplicantPersonalInfo Management Component', () => {
    let comp: ApplicantPersonalInfoComponent;
    let fixture: ComponentFixture<ApplicantPersonalInfoComponent>;
    let service: ApplicantPersonalInfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicantPersonalInfoComponent],
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
        .overrideTemplate(ApplicantPersonalInfoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicantPersonalInfoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicantPersonalInfoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApplicantPersonalInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.applicantPersonalInfos && comp.applicantPersonalInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ApplicantPersonalInfo(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.applicantPersonalInfos && comp.applicantPersonalInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
