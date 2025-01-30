import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestEntryTO, ISortField, ISortOrder} from "../../../services/mgmt/api/backend";
import {StatusMessageComponent} from "../../common-views/status-message/status-message.component";
import {HttpErrorResponse} from "@angular/common/http";
import {ModalComponent} from "@coreui/angular";
import {RequestResponse} from "../registration-request/registration-request.component";
import {MatSort, Sort} from "@angular/material/sort";
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit, AfterViewInit {

  @ViewChild("requestListStatusMessage") public requestListStatusMessage!: StatusMessageComponent;
  @ViewChild("operationStatusMessage") public operationStatusMessage!: StatusMessageComponent;
  @ViewChild("responseModal") responseModal: ModalComponent;
  @ViewChild(MatSort) sort: MatSort;
  registrationRequests = new MatTableDataSource<IRegistrationRequestEntryTO>();
  pageSize = 10;
  pageIndex = 0;
  totalNumberOfRegistrationRequests = 0;

  constructor(private readonly apiService: ApiService) {
  }

  async getRegistrationRequests(sortState: Sort) {
    let params: any = {page: this.pageIndex, size: this.pageSize};

    if (sortState?.active && sortState?.direction !== '') {
      const isAsc = sortState.direction === 'asc';

      if (sortState.active === 'organizationName') {
        params.sortField = ISortField.ORGANIZATION_NAME;
        params.sortOrder = isAsc ? ISortOrder.ASC : ISortOrder.DESC;
      } else if (sortState.active === 'status') {
        params.sortField = ISortField.STATUS;
        params.sortOrder = isAsc ? ISortOrder.ASC : ISortOrder.DESC;
      }
    }

    const requestsResponseTO = await this.apiService.getRegistrationRequests(params);

    console.log(requestsResponseTO);

    this.registrationRequests.data = requestsResponseTO.registrationRequests;
    this.totalNumberOfRegistrationRequests = requestsResponseTO.totalNumberOfRegistrationRequests;
    this.registrationRequests.sort = this.sort;

    this.sortData(sortState);
  }

  ngOnInit(): void {
    this.handleGetRegistrationRequests();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe((sortState: Sort) => {
      if (!sortState.active || sortState.direction === '') {
        return;
      }
      this.handleGetRegistrationRequests(sortState);
    });
  }

  handleGetRegistrationRequests(sortState: Sort = undefined) {
    this.getRegistrationRequests(sortState).catch((e: HttpErrorResponse) => {
      this.requestListStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.requestListStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  showResponse(response: RequestResponse){
    if(!response.isError) {
      this.operationStatusMessage.showSuccessMessage(response.message);
    } else {
      this.operationStatusMessage.showErrorMessage(response.message);
    }

    this.responseModal.visible = true;
    this.handleGetRegistrationRequests(this.sort);
  }

  sortData(sortState: Sort) {
    if (!sortState?.active || sortState?.direction === '') {
      return;
    }

    const data = this.registrationRequests.data.slice();

    data.sort((a, b) => {
      const isAsc = sortState.direction === 'asc';
      switch (sortState.active) {
        case 'organizationName':
          return this.compare(a.name, b.name, isAsc);
        case 'status':
          return this.compare(a.status, b.status, isAsc);
        default:
          return 0;
      }
    });

    this.registrationRequests.data = data;
  }

  compare(a: string | number, b: string | number, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  onPageChange(event: any): void {
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.handleGetRegistrationRequests(this.sort);
  }
}
