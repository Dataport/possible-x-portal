import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router)  {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.includes("/registration/") && !(req.url.match("\/registration\/request$") && req.method === "POST")) {
      return next.handle(req).pipe(catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            alert('Invalid Token generated. Please log in again.');
          } else if (error.status === 403) {
            alert('Unauthorized Token used. Please log in with the correct Role.');
          } else {
            alert('Something went wrong.');
          }
          this.router.navigate(["/"]);
          return throwError(() => new Error(error.message))
        }));
    } else {
      return next.handle(req);
    }
  }
}
