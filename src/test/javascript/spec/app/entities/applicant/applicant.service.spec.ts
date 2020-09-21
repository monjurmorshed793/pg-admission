import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ApplicantService } from 'app/entities/applicant/applicant.service';
import { IApplicant, Applicant } from 'app/shared/model/applicant.model';
import { ApplicationStatus } from 'app/shared/model/enumerations/application-status.model';

describe('Service Tests', () => {
  describe('Applicant Service', () => {
    let injector: TestBed;
    let service: ApplicantService;
    let httpMock: HttpTestingController;
    let elemDefault: IApplicant;
    let expectedResult: IApplicant | IApplicant[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ApplicantService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Applicant(0, 0, ApplicationStatus.NOT_APPLIED, currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            appliedOn: currentDate.format(DATE_TIME_FORMAT),
            applicationFeePaidOn: currentDate.format(DATE_TIME_FORMAT),
            selectedRejectedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Applicant', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            appliedOn: currentDate.format(DATE_TIME_FORMAT),
            applicationFeePaidOn: currentDate.format(DATE_TIME_FORMAT),
            selectedRejectedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            appliedOn: currentDate,
            applicationFeePaidOn: currentDate,
            selectedRejectedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new Applicant()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Applicant', () => {
        const returnedFromService = Object.assign(
          {
            applicationSerial: 1,
            status: 'BBBBBB',
            appliedOn: currentDate.format(DATE_TIME_FORMAT),
            applicationFeePaidOn: currentDate.format(DATE_TIME_FORMAT),
            selectedRejectedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            appliedOn: currentDate,
            applicationFeePaidOn: currentDate,
            selectedRejectedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Applicant', () => {
        const returnedFromService = Object.assign(
          {
            applicationSerial: 1,
            status: 'BBBBBB',
            appliedOn: currentDate.format(DATE_TIME_FORMAT),
            applicationFeePaidOn: currentDate.format(DATE_TIME_FORMAT),
            selectedRejectedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            appliedOn: currentDate,
            applicationFeePaidOn: currentDate,
            selectedRejectedOn: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Applicant', () => {
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
