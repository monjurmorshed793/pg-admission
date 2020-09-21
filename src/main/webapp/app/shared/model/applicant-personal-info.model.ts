import { Moment } from 'moment';
import { IApplicant } from 'app/shared/model/applicant.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { Religion } from 'app/shared/model/enumerations/religion.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';

export interface IApplicantPersonalInfo {
  id?: number;
  applicationSerial?: number;
  fullName?: string;
  firstName?: string;
  middleName?: string;
  lastName?: string;
  fatherName?: string;
  motherName?: string;
  gender?: Gender;
  religion?: Religion;
  nationality?: string;
  dateOfBirth?: Moment;
  placeOfBirth?: string;
  mobileNumber?: string;
  emailAddress?: string;
  maritalStatus?: MaritalStatus;
  applicant?: IApplicant;
}

export class ApplicantPersonalInfo implements IApplicantPersonalInfo {
  constructor(
    public id?: number,
    public applicationSerial?: number,
    public fullName?: string,
    public firstName?: string,
    public middleName?: string,
    public lastName?: string,
    public fatherName?: string,
    public motherName?: string,
    public gender?: Gender,
    public religion?: Religion,
    public nationality?: string,
    public dateOfBirth?: Moment,
    public placeOfBirth?: string,
    public mobileNumber?: string,
    public emailAddress?: string,
    public maritalStatus?: MaritalStatus,
    public applicant?: IApplicant
  ) {}
}
