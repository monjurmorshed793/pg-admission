import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IThana } from 'app/shared/model/thana.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ThanaService } from './thana.service';
import { ThanaDeleteDialogComponent } from './thana-delete-dialog.component';

@Component({
  selector: 'pg-thana',
  templateUrl: './thana.component.html',
})
export class ThanaComponent implements OnInit, OnDestroy {
  thanas: IThana[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected thanaService: ThanaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.thanas = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.thanaService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IThana[]>) => this.paginateThanas(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.thanas = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInThanas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IThana): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInThanas(): void {
    this.eventSubscriber = this.eventManager.subscribe('thanaListModification', () => this.reset());
  }

  delete(thana: IThana): void {
    const modalRef = this.modalService.open(ThanaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.thana = thana;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateThanas(data: IThana[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.thanas.push(data[i]);
      }
    }
  }
}
