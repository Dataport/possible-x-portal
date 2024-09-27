import {AfterViewInit, Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ApiService} from "../../../services/mgmt/api/api.service";
import {IRegistrationRequestListTO} from "../../../services/mgmt/api/backend";

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit{
  registrationRequests: IRegistrationRequestListTO[] = [];

  constructor(private apiService: ApiService) {}

  async getRegistrationRequests() {
    this.registrationRequests = await this.apiService.getAllRegistrationRequests();
  }

  ngOnInit(): void {
    this.getRegistrationRequests();
  }

  protected isRegistrationRequestNew(request: IRegistrationRequestListTO): boolean {
    return request.status === 'NEW';
  }

  protected isRegistrationRequestAccepted(request: IRegistrationRequestListTO): boolean {
    return request.status === 'ACCEPTED';
  }

  protected isRegistrationRequestRejected(request: IRegistrationRequestListTO): boolean {
    return request.status === 'REJECTED';
  }

  test(event: Event): void {
    event.stopPropagation();
    console.log("test");
  }
}
