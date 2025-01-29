import {ActivatedRouteSnapshot, ResolveFn, Router, RouterStateSnapshot} from '@angular/router';
import {ApiService} from "../api/api.service";
import {inject} from "@angular/core";

export const isAuthenticated: ResolveFn<any> =
  async (route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> => {
    const router = inject(Router);
    const apiService = inject(ApiService);

    try {
      const response = await apiService.getAllRegistrationRequests();
      return true;
    } catch (e) {
      console.log(e);
      return false;
    }
  }
