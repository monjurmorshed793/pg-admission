import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ApplicantPersonalInfoService } from 'app/entities/applicant-personal-info/applicant-personal-info.service';
import { IApplicantPersonalInfo, ApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { Religion } from 'app/shared/model/enumerations/religion.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';

describe('Service Tests', () => {
  describe('ApplicantPersonalInfo Service', () => {
    let injector: TestBed;
    let service: ApplicantPersonalInfoService;
    let httpMock: HttpTestingController;
    let elemDefault: IApplicantPersonalInfo;
    let expectedResult: IApplicantPersonalInfo | IApplicantPersonalInfo[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ApplicantPersonalInfoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ApplicantPersonalInfo(
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        Gender.MALE,
        Religion.ISLAM,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        MaritalStatus.MARRIED
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ApplicantPersonalInfo', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.create(new ApplicantPersonalInfo()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ApplicantPersonalInfo', () => {
        const returnedFromService = Object.assign(
          {
            applicationSerial: 1,
            fullName: 'BBBBBB',
            firstName: 'BBBBBB',
            middleName: 'BBBBBB',
            lastName: 'BBBBBB',
            fatherName: 'BBBBBB',
            motherName: 'BBBBBB',
            gender: 'BBBBBB',
            religion: 'BBBBBB',
            nationality: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            placeOfBirth: 'BBBBBB',
            mobileNumber: 'BBBBBB',
            emailAddress: 'BBBBBB',
            maritalStatus: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ApplicantPersonalInfo', () => {
        const returnedFromService = Object.assign(
          {
            applicationSerial: 1,
            fullName: 'BBBBBB',
            firstName: 'BBBBBB',
            middleName: 'BBBBBB',
            lastName: 'BBBBBB',
            fatherName: 'BBBBBB',
            motherName: 'BBBBBB',
            gender: 'BBBBBB',
            religion: 'BBBBBB',
            nationality: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            placeOfBirth: 'BBBBBB',
            mobileNumber: 'BBBBBB',
            emailAddress: 'BBBBBB',
            maritalStatus: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ApplicantPersonalInfo', () => {
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
