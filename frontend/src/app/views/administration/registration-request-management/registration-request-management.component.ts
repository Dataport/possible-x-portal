import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-registration-request-management',
  templateUrl: './registration-request-management.component.html',
  styleUrls: ['./registration-request-management.component.scss']
})
export class RegistrationRequestManagementComponent implements OnInit{
  registrationRequests: any[] = [];

  async getRegistrationRequests() {
    this.registrationRequests = [{"name":"orga1", "description":"desc1", "address":{"street":"street1", "city":"city1", "zip":"zip1"}},
      {"name":"orga2", "description":"desc2", "address":{"street":"street2", "city":"city2", "zip":"zip2"}},
      {"name":"orga3", "description":"desc3", "address":{"street":"street3", "city":"city3", "zip":"zip3"}},
      {"name":"orga4", "description":"desc4", "address":{"street":"street4", "city":"city4", "zip":"zip4"}},
      {"name":"orga5", "description":"desc5", "address":{"street":"street5", "city":"city5", "zip":"zip5"}},
      {"name":"orga6", "description":"desc6", "address":{"street":"street6", "city":"city6", "zip":"zip6"}},
      {"name":"orga7", "description":"desc7", "address":{"street":"street7", "city":"city7", "zip":"zip7"}},
      {"name":"orga8", "description":"desc8", "address":{"street":"street8", "city":"city8", "zip":"zip8"}},
      {"name":"orga9", "description":"desc9", "address":{"street":"street9", "city":"city9", "zip":"zip9"}},
      {"name":"orga10", "description":"desc10", "address":{"street":"street10", "city":"city10", "zip":"zip10"}}];
  }

  ngOnInit(): void {
    this.getRegistrationRequests();
  }
}
