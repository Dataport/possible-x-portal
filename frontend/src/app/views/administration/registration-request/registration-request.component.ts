import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {IRegistrationRequestEntryTO, IRequestStatus} from "../../../services/mgmt/api/backend";
import {HttpErrorResponse} from "@angular/common/http";
import {StatusMessageComponent} from "../../common-views/status-message/status-message.component";
import {ApiService} from "../../../services/mgmt/api/api.service";

@Component({
  selector: 'app-registration-request',
  templateUrl: './registration-request.component.html',
  styleUrls: ['./registration-request.component.scss']
})
export class RegistrationRequestComponent implements OnInit, OnChanges {
  @Input() request: IRegistrationRequestEntryTO;
  @ViewChild("operationStatusMessage") public operationStatusMessage!: StatusMessageComponent;
  @Output() reloadList: EventEmitter<any> = new EventEmitter();

  isClickableAccept: boolean = true;
  isClickableReject: boolean = true;
  isClickableDelete: boolean = true;

  constructor(private apiService: ApiService) {
  }

  ngOnInit() {
    this.computeButtonStates();
  }

  ngOnChanges(changes: SimpleChanges) {
    this.computeButtonStates();
  }

  protected isRegistrationRequestNew(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.NEW;
  }

  protected isRegistrationRequestAccepted(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.ACCEPTED;
  }

  protected isRegistrationRequestRejected(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.REJECTED;
  }

  protected isRegistrationRequestCompleted(request: IRegistrationRequestEntryTO): boolean {
    return request.status === IRequestStatus.COMPLETED;
  }

  computeButtonStates() {
    switch (this.request.status) {
      case IRequestStatus.NEW:
        this.isClickableAccept = true;
        this.isClickableReject = true;
        this.isClickableDelete = true;
        break;
      case IRequestStatus.ACCEPTED:
        this.isClickableAccept = false;
        this.isClickableReject = true;
        this.isClickableDelete = true;
        break;
      case IRequestStatus.REJECTED:
        this.isClickableAccept = false;
        this.isClickableReject = false;
        this.isClickableDelete = true;
        break;
      default:
        this.isClickableAccept = false;
        this.isClickableReject = false;
        this.isClickableDelete = false;
    }
  }


  async acceptRequest(event: Event, request: IRegistrationRequestEntryTO): Promise<void> {
    event.stopPropagation();
    this.operationStatusMessage.hideAllMessages();

    this.apiService.acceptRegistrationRequest(request.name).then(() => {
      console.log("Accept request for: " + request.name);
      this.operationStatusMessage.showSuccessMessage("Request accepted successfully. Participant was checked for compliance and stored in the catalog.");
      this.reloadList.emit();
    }).catch((e: HttpErrorResponse) => {
      this.operationStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.operationStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  async deleteRequest(event: Event, request: IRegistrationRequestEntryTO): Promise<void> {
    event.stopPropagation();
    this.operationStatusMessage.hideAllMessages();

    this.apiService.deleteRegistrationRequest(request.name).then(() => {
      console.log("Delete request for: " + request.name);
      this.operationStatusMessage.showSuccessMessage("Request deleted successfully");
      this.reloadList.emit();
    }).catch((e: HttpErrorResponse) => {
      this.operationStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.operationStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }

  async rejectRequest(event: Event, request: IRegistrationRequestEntryTO): Promise<void> {
    event.stopPropagation();
    this.operationStatusMessage.hideAllMessages();

    this.apiService.rejectRegistrationRequest(request.name).then(() => {
      console.log("Reject request for: " + request.name);
      this.operationStatusMessage.showSuccessMessage("Request rejected successfully");
      this.reloadList.emit();
    }).catch((e: HttpErrorResponse) => {
      this.operationStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.operationStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }
}
