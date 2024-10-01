import {Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestWithStatusTO} from "../../../services/mgmt/api/backend";
import {StatusMessageComponent} from "../../common-views/status-message/status-message.component";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit{

  @ViewChild("operationStatusMessage") public operationStatusMessage!: StatusMessageComponent;
  @ViewChild("requestListStatusMessage") public requestListStatusMessage!: StatusMessageComponent;
  registrationRequests: IRegistrationRequestWithStatusTO[] = [];

  constructor(private apiService: ApiService) {}

  async getRegistrationRequests() {
    this.registrationRequests = await this.apiService.getAllRegistrationRequests();
  }

  ngOnInit(): void {
    this.handleGetRegistrationRequests();
  }

  private handleGetRegistrationRequests() {
    this.getRegistrationRequests().catch((e: HttpErrorResponse) => {
      this.requestListStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.requestListStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  protected isRegistrationRequestNew(request: IRegistrationRequestWithStatusTO): boolean {
    return request.status === 'NEW';
  }

  protected isRegistrationRequestAccepted(request: IRegistrationRequestWithStatusTO): boolean {
    return request.status === 'ACCEPTED';
  }

  protected isRegistrationRequestRejected(request: IRegistrationRequestWithStatusTO): boolean {
    return request.status === 'REJECTED';
  }

  async acceptRequest(event: Event, request: IRegistrationRequestWithStatusTO): Promise<void> {
    event.stopPropagation();
    this.operationStatusMessage.hideAllMessages();

    this.apiService.acceptRegistrationRequest(request.name).then(() => {
      console.log("Accept request for: " + request.name);
      this.operationStatusMessage.showSuccessMessage("Request accepted successfully");
      this.handleGetRegistrationRequests();
    }).catch((e: HttpErrorResponse) => {
      this.operationStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.operationStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  async deleteRequest(event: Event, request: IRegistrationRequestWithStatusTO): Promise<void> {
    event.stopPropagation();
    this.operationStatusMessage.hideAllMessages();

    this.apiService.deleteRegistrationRequest(request.name).then(() => {
      console.log("Delete request for: " + request.name);
      this.operationStatusMessage.showSuccessMessage("Request deleted successfully");
      this.handleGetRegistrationRequests();
    }).catch((e: HttpErrorResponse) => {
      this.operationStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.operationStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  async rejectRequest(event: Event, request: IRegistrationRequestWithStatusTO): Promise<void> {
    event.stopPropagation();
    this.operationStatusMessage.hideAllMessages();

    this.apiService.rejectRegistrationRequest(request.name).then(() => {
      console.log("Reject request for: " + request.name);
      this.operationStatusMessage.showSuccessMessage("Request rejected successfully");
      this.handleGetRegistrationRequests();
    }).catch((e: HttpErrorResponse) => {
      this.operationStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.operationStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }
}
