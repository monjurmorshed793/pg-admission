import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { JobExperienceService } from 'app/entities/job-experience/job-experience.service';
import { IJobExperience, JobExperience } from 'app/shared/model/job-experience.model';

describe('Service Tests', () => {
  describe('JobExperience Service', () => {
    let injector: TestBed;
    let service: JobExperienceService;
    let httpMock: HttpTestingController;
    let elemDefault: IJobExperience;
    let expectedResult: IJobExperience | IJobExperience[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JobExperienceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new JobExperience(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a JobExperience', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
          },
          returnedFromService
        );

        service.create(new JobExperience()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a JobExperience', () => {
        const returnedFromService = Object.assign(
          {
            organizationName: 'BBBBBB',
            designation: 'BBBBBB',
            jobResponsibility: 'BBBBBB',
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            currentlyWorking: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of JobExperience', () => {
        const returnedFromService = Object.assign(
          {
            organizationName: 'BBBBBB',
            designation: 'BBBBBB',
            jobResponsibility: 'BBBBBB',
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            currentlyWorking: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a JobExperience', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
