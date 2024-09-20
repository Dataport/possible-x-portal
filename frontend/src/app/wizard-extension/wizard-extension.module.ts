/*
 *  Copyright 2024 Dataport. All rights reserved. Developed as part of the MERLOT project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WizardAppModule } from '../sdwizard/wizardapp.module';
import { CommonViewsModule } from '../views/common-views/common-views.module';
import { ButtonGroupModule, ButtonModule, GridModule, ModalModule } from '@coreui/angular';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BaseWizardExtensionComponent } from './base-wizard-extension/base-wizard-extension.component';
import { MaterialModule } from '../sdwizard/material.module'
import { IconModule } from '@coreui/icons-angular';

@NgModule({
    declarations: [BaseWizardExtensionComponent],
    exports: [
        BaseWizardExtensionComponent
    ],
    imports: [
        CommonModule,
        WizardAppModule,
        CommonViewsModule,
        FlexLayoutModule,
        ButtonModule,
        ButtonGroupModule,
        FormsModule,
        GridModule,
        ModalModule,
        MaterialModule,
        IconModule,
    ]
})
export class WizardExtensionModule { }
