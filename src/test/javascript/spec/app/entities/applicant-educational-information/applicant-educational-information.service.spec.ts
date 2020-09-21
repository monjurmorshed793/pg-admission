import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApplicantEducationalInformationService } from 'app/entities/applicant-educational-information/applicant-educational-information.service';
import {
  IApplicantEducationalInformation,
  ApplicantEducationalInformation,
} from 'app/shared/model/applicant-educational-information.model';

describe('Service Tests', () => {
  describe('ApplicantEducationalInformation Service', () => {
    let injector: TestBed;
    let service: ApplicantEducationalInformationService;
    let httpMock: HttpTestingController;
    let elemDefault: IApplicantEducationalInformation;
    let expectedResult: IApplicantEducationalInformation | IApplicantEducationalInformation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ApplicantEducationalInformationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ApplicantEducationalInformation(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ApplicantEducationalInformation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ApplicantEducationalInformation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ApplicantEducationalInformation', () => {
        const returnedFromService = Object.assign(
          {
            instituteName: 'BBBBBB',
            board: 'BBBBBB',
            totalMarksGrade: 'BBBBBB',
            divisionClassGrade: 'BBBBBB',
            passingYear: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ApplicantEducationalInformation', () => {
        const returnedFromService = Object.assign(
          {
            instituteName: 'BBBBBB',
            board: 'BBBBBB',
            totalMarksGrade: 'BBBBBB',
            divisionClassGrade: 'BBBBBB',
            passingYear: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ApplicantEducationalInformation', () => {
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
