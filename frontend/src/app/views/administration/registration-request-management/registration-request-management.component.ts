import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestEntryTO} from "../../../services/mgmt/api/backend";
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

  async getRegistrationRequests() {
    const requestsResponseTO = await this.apiService.getRegistrationRequests({page: this.pageIndex, size: this.pageSize});
    this.registrationRequests.data = requestsResponseTO.registrationRequests;
    this.totalNumberOfRegistrationRequests = requestsResponseTO.totalNumberOfRegistrationRequests;
    this.registrationRequests.sort = this.sort;

    // Apply the current sorting state
    if (this.sort.active && this.sort.direction) {
      await this.customSort({active: this.sort.active, direction: this.sort.direction});
    }
  }

  ngOnInit(): void {
    this.handleGetRegistrationRequests();
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe((sortState: Sort) => {
      this.customSort(sortState);
    });
  }

  handleGetRegistrationRequests() {
    this.getRegistrationRequests().catch((e: HttpErrorResponse) => {
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
    this.handleGetRegistrationRequests();
  }

  async customSort(sortState: Sort) {
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

    this.registrationRequests.data = data.slice();
  }

  compare(a: string | number, b: string | number, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }

  onPageChange(event: any): void {
    this.pageSize = event.pageSize;
    this.pageIndex = event.pageIndex;
    this.handleGetRegistrationRequests();
  }
}
