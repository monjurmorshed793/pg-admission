import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistrict } from 'app/shared/model/district.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DistrictService } from './district.service';
import { DistrictDeleteDialogComponent } from './district-delete-dialog.component';

@Component({
  selector: 'jhi-district',
  templateUrl: './district.component.html',
})
export class DistrictComponent implements OnInit, OnDestroy {
  districts: IDistrict[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected districtService: DistrictService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.districts = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.districtService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IDistrict[]>) => this.paginateDistricts(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.districts = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDistricts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDistrict): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDistricts(): void {
    this.eventSubscriber = this.eventManager.subscribe('districtListModification', () => this.reset());
  }

  delete(district: IDistrict): void {
    const modalRef = this.modalService.open(DistrictDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.district = district;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDistricts(data: IDistrict[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.districts.push(data[i]);
      }
    }
  }
}
