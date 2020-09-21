import { IExamType } from 'app/shared/model/exam-type.model';
import { IApplicant } from 'app/shared/model/applicant.model';

export interface IApplicantEducationalInformation {
  id?: number;
  instituteName?: string;
  board?: string;
  totalMarksGrade?: string;
  divisionClassGrade?: string;
  passingYear?: number;
  examType?: IExamType;
  applicant?: IApplicant;
}

export class ApplicantEducationalInformation implements IApplicantEducationalInformation {
  constructor(
    public id?: number,
    public instituteName?: string,
    public board?: string,
    public totalMarksGrade?: string,
    public divisionClassGrade?: string,
    public passingYear?: number,
    public examType?: IExamType,
    public applicant?: IApplicant
  ) {}
}
