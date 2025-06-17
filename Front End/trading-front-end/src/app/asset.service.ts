import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Asset } from './asset';

@Injectable({
  providedIn: 'root'
})
export class AssetService {
private assetURL:string;
private http = inject(HttpClient)

  constructor() { 
    this.assetURL = "http://localhost:8080/asset";
  }

  public findAllUserAssets(id: number): Observable<any> {
    return this.http.get(this.assetURL + "/by_user/" + id);
  }

  public findByName(name:string, id:number):Observable<Asset>{
    return this.http.get<Asset>(this.assetURL + "/by_name/" + name + "/" + id)
  }
}