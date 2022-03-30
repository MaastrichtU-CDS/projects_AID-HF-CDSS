import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, Validators} from '@angular/forms';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { SymptomsFormValue } from '../interface/symptoms';
import { AdviceApiService } from '../service/advice-api.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  @Output() setAdviceEvent = new EventEmitter<string>();
  constructor(private fb: FormBuilder, private adviceApiService: AdviceApiService) { }

  showError = false;

  symptomForm = this.fb.group({
    orthopnea: [0],
    cough: [0],
    edema: [0],
    dizziness: [0],
    syncope: ['', [Validators.required]],
  })

  ngOnInit(): void {}

  onReset() {
    this.showError = false;
    this.symptomForm.reset({
      orthopnea: 0,
      cough: 0,
      edema: 0,
      dizziness: 0,
      syncope: '',
    })
  }

  dismissError() {
    this.showError = false;
  }

  onSubmit() {
    const symptoms = this.symptomForm.value as SymptomsFormValue;

    const handleError = (err: any) => {
      this.showError = true;
      return throwError(() => new Error(err.message));
    };

    const handleAdvice = (advice: string) => {
      if(this.showError){
        this.showError = false;
      }
        this.setAdviceEvent.emit(advice);
    }

    this.adviceApiService.getAdvice(symptoms).pipe(catchError(handleError)).subscribe(handleAdvice);
  }
}