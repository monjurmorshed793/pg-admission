import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDivision } from 'app/shared/model/division.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DivisionService } from './division.service';
import { DivisionDeleteDialogComponent } from './division-delete-dialog.component';

@Component({
  selector: 'pg-division',
  templateUrl: './division.component.html',
})
export class DivisionComponent implements OnInit, OnDestroy {
  divisions: IDivision[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected divisionService: DivisionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.divisions = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.divisionService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IDivision[]>) => this.paginateDivisions(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.divisions = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDivisions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDivision): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDivisions(): void {
    this.eventSubscriber = this.eventManager.subscribe('divisionListModification', () => this.reset());
  }

  delete(division: IDivision): void {
    const modalRef = this.modalService.open(DivisionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.division = division;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDivisions(data: IDivision[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.divisions.push(data[i]);
      }
    }
  }
}
