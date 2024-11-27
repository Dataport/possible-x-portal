import {Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestEntryTO, IRequestStatus} from "../../../services/mgmt/api/backend";
import {StatusMessageComponent} from "../../common-views/status-message/status-message.component";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit {

  @ViewChild("requestListStatusMessage") public requestListStatusMessage!: StatusMessageComponent;
  registrationRequests: IRegistrationRequestEntryTO[] = [];

  constructor(private apiService: ApiService) {
  }

  async getRegistrationRequests() {
    this.registrationRequests = await this.apiService.getAllRegistrationRequests();
  }

  ngOnInit(): void {
    this.handleGetRegistrationRequests();
  }

  handleGetRegistrationRequests() {
    this.getRegistrationRequests().catch((e: HttpErrorResponse) => {
      this.requestListStatusMessage.showErrorMessage(e.error.detail);
    }).catch(_ => {
      this.requestListStatusMessage.showErrorMessage("Unknown error occurred");
    });
  }
}
