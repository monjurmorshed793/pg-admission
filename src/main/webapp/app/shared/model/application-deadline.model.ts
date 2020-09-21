import { Moment } from 'moment';
import { ISemester } from 'app/shared/model/semester.model';
import { IProgram } from 'app/shared/model/program.model';

export interface IApplicationDeadline {
  id?: number;
  fromDate?: Moment;
  toDate?: Moment;
  semester?: ISemester;
  program?: IProgram;
}

export class ApplicationDeadline implements IApplicationDeadline {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public toDate?: Moment,
    public semester?: ISemester,
    public program?: IProgram
  ) {}
}
