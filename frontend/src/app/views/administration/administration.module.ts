import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { RegistrationRequestManagementComponent } from './registration-request-management/registration-request-management.component';
import {AdministrationRoutingModule} from "./administration-routing.module";
import {
  AccordionButtonDirective,
  AccordionComponent,
  AccordionItemComponent, ButtonDirective,
  TemplateIdDirective
} from "@coreui/angular";


@NgModule({
  declarations: [
    RegistrationRequestManagementComponent
  ],
  imports: [
    CommonModule,
    AdministrationRoutingModule,
    AccordionComponent,
    AccordionItemComponent,
    AccordionButtonDirective,
    TemplateIdDirective,
    NgOptimizedImage,
    ButtonDirective
  ]
})
export class AdministrationModule { }
