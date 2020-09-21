import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ProgramDetailComponent } from 'app/entities/program/program-detail.component';
import { Program } from 'app/shared/model/program.model';

describe('Component Tests', () => {
  describe('Program Management Detail Component', () => {
    let comp: ProgramDetailComponent;
    let fixture: ComponentFixture<ProgramDetailComponent>;
    const route = ({ data: of({ program: new Program(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ProgramDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProgramDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load program on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.program).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
