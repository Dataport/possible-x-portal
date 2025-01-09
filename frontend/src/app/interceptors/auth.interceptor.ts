import { HttpClient, HttpErrorResponse, HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import {Router} from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router)  {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.url.includes("/registration/") && !(req.url.match("\/registration\/request$") && req.method === "POST")) {
      var authToken = sessionStorage.getItem("authToken");
      if (!authToken) {
        var username = window.prompt("Enter your username:");
        var password = window.prompt("Enter your password:");
        authToken = btoa(`${username}:${password}`);
        sessionStorage.setItem("authToken", authToken)
      }
      var authReq = req.clone({
        setHeaders: {
          Authorization: `Basic ${authToken}`
        }
      });
      return next.handle(authReq).pipe(catchError((error: HttpErrorResponse) => {
          if (error.status === 401) {
            sessionStorage.removeItem('authToken');
            alert('Invalid Token generated. Please log in again.');
            this.router.navigate(["/"]);
          } else if (error.status === 403) {
            sessionStorage.removeItem('authToken');
            alert('Unauthorized Token used. Please log in with the correct Role.');
            this.router.navigate(["/"]);
          }
          return throwError(() => new Error(error.message))
        }));
    } else {
      return next.handle(req);
    }
  }
}