import { IDistrict } from 'app/shared/model/district.model';

export interface IThana {
  id?: number;
  thanaName?: string;
  district?: IDistrict;
}

export class Thana implements IThana {
  constructor(public id?: number, public thanaName?: string, public district?: IDistrict) {}
}
