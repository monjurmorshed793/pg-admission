export interface ISemester {
  id?: number;
  semesterName?: string;
  isActive?: boolean;
}

export class Semester implements ISemester {
  constructor(public id?: number, public semesterName?: string, public isActive?: boolean) {
    this.isActive = this.isActive || false;
  }
}
