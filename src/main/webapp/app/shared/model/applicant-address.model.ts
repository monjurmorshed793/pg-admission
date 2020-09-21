import { IApplicant } from 'app/shared/model/applicant.model';
import { AddressType } from 'app/shared/model/enumerations/address-type.model';

export interface IApplicantAddress {
  id?: number;
  applicationSerial?: number;
  addressType?: AddressType;
  thanaOther?: string;
  line1?: string;
  line2?: string;
  applicant?: IApplicant;
}

export class ApplicantAddress implements IApplicantAddress {
  constructor(
    public id?: number,
    public applicationSerial?: number,
    public addressType?: AddressType,
    public thanaOther?: string,
    public line1?: string,
    public line2?: string,
    public applicant?: IApplicant
  ) {}
}
