import { IDivision } from 'app/shared/model/division.model';

export interface IDistrict {
  id?: number;
  districtName?: string;
  division?: IDivision;
}

export class District implements IDistrict {
  constructor(public id?: number, public districtName?: string, public division?: IDivision) {}
}
