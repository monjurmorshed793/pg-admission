import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamType } from 'app/shared/model/exam-type.model';

@Component({
  selector: 'pg-exam-type-detail',
  templateUrl: './exam-type-detail.component.html',
})
export class ExamTypeDetailComponent implements OnInit {
  examType: IExamType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examType }) => (this.examType = examType));
  }

  previousState(): void {
    window.history.back();
  }
}
