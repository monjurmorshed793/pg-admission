import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ApplicantAddressService } from 'app/entities/applicant-address/applicant-address.service';
import { IApplicantAddress, ApplicantAddress } from 'app/shared/model/applicant-address.model';
import { AddressType } from 'app/shared/model/enumerations/address-type.model';

describe('Service Tests', () => {
  describe('ApplicantAddress Service', () => {
    let injector: TestBed;
    let service: ApplicantAddressService;
    let httpMock: HttpTestingController;
    let elemDefault: IApplicantAddress;
    let expectedResult: IApplicantAddress | IApplicantAddress[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ApplicantAddressService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ApplicantAddress(0, 0, AddressType.PRESENT, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ApplicantAddress', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ApplicantAddress()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ApplicantAddress', () => {
        const returnedFromService = Object.assign(
          {
            applicationSerial: 1,
            addressType: 'BBBBBB',
            thanaOther: 'BBBBBB',
            line1: 'BBBBBB',
            line2: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ApplicantAddress', () => {
        const returnedFromService = Object.assign(
          {
            applicationSerial: 1,
            addressType: 'BBBBBB',
            thanaOther: 'BBBBBB',
            line1: 'BBBBBB',
            line2: 'BBBBBB',
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

      it('should delete a ApplicantAddress', () => {
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
