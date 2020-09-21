export interface IExamType {
  id?: number;
  examTypeCode?: string;
  examTypeName?: string;
}

export class ExamType implements IExamType {
  constructor(public id?: number, public examTypeCode?: string, public examTypeName?: string) {}
}
