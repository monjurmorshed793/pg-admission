import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgadmissionTestModule } from '../../../test.module';
import { ApplicationDeadlineDetailComponent } from 'app/entities/application-deadline/application-deadline-detail.component';
import { ApplicationDeadline } from 'app/shared/model/application-deadline.model';

describe('Component Tests', () => {
  describe('ApplicationDeadline Management Detail Component', () => {
    let comp: ApplicationDeadlineDetailComponent;
    let fixture: ComponentFixture<ApplicationDeadlineDetailComponent>;
    const route = ({ data: of({ applicationDeadline: new ApplicationDeadline(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgadmissionTestModule],
        declarations: [ApplicationDeadlineDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ApplicationDeadlineDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationDeadlineDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load applicationDeadline on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationDeadline).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
