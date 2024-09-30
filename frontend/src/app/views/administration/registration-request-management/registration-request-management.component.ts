import {AfterViewInit, Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestItemTO} from "../../../services/mgmt/api/backend";

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit{
  registrationRequests: IRegistrationRequestItemTO[] = [];

  constructor(private apiService: ApiService) {}

  async getRegistrationRequests() {
    this.registrationRequests = await this.apiService.getAllRegistrationRequests();
  }

  ngOnInit(): void {
    this.getRegistrationRequests();
  }

  protected isRegistrationRequestNew(request: IRegistrationRequestItemTO): boolean {
    return request.status === 'NEW';
  }

  protected isRegistrationRequestAccepted(request: IRegistrationRequestItemTO): boolean {
    return request.status === 'ACCEPTED';
  }

  protected isRegistrationRequestRejected(request: IRegistrationRequestItemTO): boolean {
    return request.status === 'REJECTED';
  }

  async acceptRequest(event: Event, request: IRegistrationRequestItemTO): Promise<void> {
    event.stopPropagation();
    await this.apiService.acceptRegistrationRequest(request.name);
    console.log("accept");
  }

  async deleteRequest(event: Event, request: IRegistrationRequestItemTO): Promise<void> {
    event.stopPropagation();
    await this.apiService.deleteRegistrationRequest(request.name)
    console.log("delete");
  }

  async rejectRequest(event: Event, request: IRegistrationRequestItemTO): Promise<void> {
    event.stopPropagation();
    await this.apiService.rejectRegistrationRequest(request.name);
    console.log("reject");
  }
}
