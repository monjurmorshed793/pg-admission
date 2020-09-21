import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExamType } from 'app/shared/model/exam-type.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ExamTypeService } from './exam-type.service';
import { ExamTypeDeleteDialogComponent } from './exam-type-delete-dialog.component';

@Component({
  selector: 'pg-exam-type',
  templateUrl: './exam-type.component.html',
})
export class ExamTypeComponent implements OnInit, OnDestroy {
  examTypes: IExamType[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected examTypeService: ExamTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.examTypes = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.examTypeService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IExamType[]>) => this.paginateExamTypes(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.examTypes = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExamTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExamType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInExamTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('examTypeListModification', () => this.reset());
  }

  delete(examType: IExamType): void {
    const modalRef = this.modalService.open(ExamTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.examType = examType;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateExamTypes(data: IExamType[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.examTypes.push(data[i]);
      }
    }
  }
}
