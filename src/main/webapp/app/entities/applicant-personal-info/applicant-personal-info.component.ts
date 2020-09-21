import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IApplicantPersonalInfo } from 'app/shared/model/applicant-personal-info.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ApplicantPersonalInfoService } from './applicant-personal-info.service';
import { ApplicantPersonalInfoDeleteDialogComponent } from './applicant-personal-info-delete-dialog.component';

@Component({
  selector: 'pg-applicant-personal-info',
  templateUrl: './applicant-personal-info.component.html',
})
export class ApplicantPersonalInfoComponent implements OnInit, OnDestroy {
  applicantPersonalInfos?: IApplicantPersonalInfo[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected applicantPersonalInfoService: ApplicantPersonalInfoService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.applicantPersonalInfoService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IApplicantPersonalInfo[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInApplicantPersonalInfos();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IApplicantPersonalInfo): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInApplicantPersonalInfos(): void {
    this.eventSubscriber = this.eventManager.subscribe('applicantPersonalInfoListModification', () => this.loadPage());
  }

  delete(applicantPersonalInfo: IApplicantPersonalInfo): void {
    const modalRef = this.modalService.open(ApplicantPersonalInfoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.applicantPersonalInfo = applicantPersonalInfo;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IApplicantPersonalInfo[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/applicant-personal-info'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.applicantPersonalInfos = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
