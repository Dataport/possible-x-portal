import {Component} from '@angular/core';
import {environment} from "../../../environments/environment";

@Component({
  templateUrl: './impressum.component.html',
  styleUrls: ['./impressum.component.scss']
})
export class ImpressumComponent {

  protected readonly environment = environment;
}
