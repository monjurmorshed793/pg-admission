import { Moment } from 'moment';
import { IApplicant } from 'app/shared/model/applicant.model';

export interface IJobExperience {
  id?: number;
  organizationName?: string;
  designation?: string;
  jobResponsibility?: any;
  fromDate?: Moment;
  toDate?: Moment;
  currentlyWorking?: boolean;
  applicant?: IApplicant;
}

export class JobExperience implements IJobExperience {
  constructor(
    public id?: number,
    public organizationName?: string,
    public designation?: string,
    public jobResponsibility?: any,
    public fromDate?: Moment,
    public toDate?: Moment,
    public currentlyWorking?: boolean,
    public applicant?: IApplicant
  ) {
    this.currentlyWorking = this.currentlyWorking || false;
  }
}
