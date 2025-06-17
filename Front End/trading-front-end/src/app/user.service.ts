import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
private userURL:string;
private http = inject(HttpClient)

  constructor() { 
    this.userURL = "http://localhost:8080/user";
  }

 
  public findById(id:number):Observable<User>{
    return this.http.get<User>(this.userURL + "/" + id);
  }

  public resetUser(id: number): void {
  this.http.patch<User>(this.userURL + "/reset_user/" + id, {})
    .subscribe(
      data => {
        console.log("Transaction buy successfully", data);
      },
      error => {
        console.error("Error adding transaction", error);
      }
    );
  }
}