import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ExamTypeUpdateComponent } from 'app/entities/exam-type/exam-type-update.component';
import { ExamTypeService } from 'app/entities/exam-type/exam-type.service';
import { ExamType } from 'app/shared/model/exam-type.model';

describe('Component Tests', () => {
  describe('ExamType Management Update Component', () => {
    let comp: ExamTypeUpdateComponent;
    let fixture: ComponentFixture<ExamTypeUpdateComponent>;
    let service: ExamTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ExamTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExamTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExamTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExamTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExamType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExamType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
