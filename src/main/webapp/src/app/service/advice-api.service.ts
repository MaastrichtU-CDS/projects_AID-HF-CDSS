import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SymptomsFormValue } from '../interface/symptoms';

@Injectable({
  providedIn: 'root'
})
export class AdviceApiService {
  private adviceRoute = "advice";
  private apiServiceRoute = `${environment.apiUrl}/${this.adviceRoute}`;

  constructor(private http: HttpClient) { }

  getAdvice(symptoms: SymptomsFormValue){
    return this.http.post(this.apiServiceRoute, symptoms, {responseType: "text"});
  }
}