import {ComponentFixture, TestBed} from '@angular/core/testing';
import {CollapseModule, FooterModule, GridModule, NavbarModule, NavModule} from '@coreui/angular';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {DefaultLayoutComponent} from './default-layout.component';
import {of} from 'rxjs';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ApiService} from "../../services/mgmt/api/api.service";
import {IVersionTO} from "../../services/mgmt/api/backend";

describe('DefaultLayoutComponent', () => {
  let component: DefaultLayoutComponent;
  let fixture: ComponentFixture<DefaultLayoutComponent>;
  let mockVersion: IVersionTO = {version: '1.0', date: '2021-01-01'};

  beforeEach(async () => {
    const apiServiceSpy = jasmine.createSpyObj('ApiService', ['getVersion']);
    apiServiceSpy.getVersion.and.returnValue(Promise.resolve(mockVersion));

    await TestBed.configureTestingModule({
      declarations: [DefaultLayoutComponent],
      imports: [NavbarModule, GridModule, CollapseModule, NoopAnimationsModule, NavModule, RouterModule, FooterModule,
        HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({}), // Mock route parameters
            snapshot: {paramMap: {get: () => null}} // Mock snapshot
          }
        },
        {provide: ApiService, useValue: apiServiceSpy}
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(DefaultLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set version from apiService', async () => {
    expect(component.versionDate).toEqual(mockVersion.date);
    expect(component.versionNumber).toEqual(mockVersion.version);
  });
});
