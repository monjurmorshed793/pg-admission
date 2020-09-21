import { Moment } from 'moment';
import { IApplicantEducationalInformation } from 'app/shared/model/applicant-educational-information.model';
import { IJobExperience } from 'app/shared/model/job-experience.model';
import { IApplicantAddress } from 'app/shared/model/applicant-address.model';
import { IApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';
import { ApplicationStatus } from 'app/shared/model/enumerations/application-status.model';

export interface IApplicant {
  id?: number;
  applicationSerial?: number;
  status?: ApplicationStatus;
  appliedOn?: Moment;
  applicationFeePaidOn?: Moment;
  selectedRejectedOn?: Moment;
  applicantEducationalInformations?: IApplicantEducationalInformation[];
  jobExperiences?: IJobExperience[];
  applicantAddresses?: IApplicantAddress[];
  applicantPersonalInformation?: IApplicantPersonalInfo;
}

export class Applicant implements IApplicant {
  constructor(
    public id?: number,
    public applicationSerial?: number,
    public status?: ApplicationStatus,
    public appliedOn?: Moment,
    public applicationFeePaidOn?: Moment,
    public selectedRejectedOn?: Moment,
    public applicantEducationalInformations?: IApplicantEducationalInformation[],
    public jobExperiences?: IJobExperience[],
    public applicantAddresses?: IApplicantAddress[],
    public applicantPersonalInformation?: IApplicantPersonalInfo
  ) {}
}
