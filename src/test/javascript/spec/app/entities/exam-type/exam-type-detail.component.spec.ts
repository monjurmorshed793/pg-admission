import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ExamTypeDetailComponent } from 'app/entities/exam-type/exam-type-detail.component';
import { ExamType } from 'app/shared/model/exam-type.model';

describe('Component Tests', () => {
  describe('ExamType Management Detail Component', () => {
    let comp: ExamTypeDetailComponent;
    let fixture: ComponentFixture<ExamTypeDetailComponent>;
    const route = ({ data: of({ examType: new ExamType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ExamTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExamTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExamTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load examType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.examType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
