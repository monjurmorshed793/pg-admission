export interface IDivision {
  id?: number;
  divisionName?: string;
}

export class Division implements IDivision {
  constructor(public id?: number, public divisionName?: string) {}
}
