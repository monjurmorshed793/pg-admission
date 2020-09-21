import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PgadmissionTestModule } from '../../../test.module';
import { JobExperienceDetailComponent } from 'app/entities/job-experience/job-experience-detail.component';
import { JobExperience } from 'app/shared/model/job-experience.model';

describe('Component Tests', () => {
  describe('JobExperience Management Detail Component', () => {
    let comp: JobExperienceDetailComponent;
    let fixture: ComponentFixture<JobExperienceDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ jobExperience: new JobExperience(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [JobExperienceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JobExperienceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JobExperienceDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load jobExperience on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jobExperience).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
