export interface IProgram {
  id?: number;
  programNameShort?: string;
  programName?: string;
}

export class Program implements IProgram {
  constructor(public id?: number, public programNameShort?: string, public programName?: string) {}
}
