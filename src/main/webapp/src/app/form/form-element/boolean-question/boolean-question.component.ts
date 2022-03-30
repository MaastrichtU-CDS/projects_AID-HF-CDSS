import { Component, Input, OnInit } from '@angular/core';
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-boolean-question',
  templateUrl: './boolean-question.component.html',
  styleUrls: ['./boolean-question.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: BooleanQuestionComponent
    }
  ]
})
export class BooleanQuestionComponent implements OnInit, ControlValueAccessor {
  control = new FormControl();
  onTouched?: Function;

  @Input() name = "";

  constructor() { }

  ngOnInit(): void {}

  writeValue(value: string): void {
    this.control.setValue(value); 
  }

  registerOnChange(onChangeFn: any): void {
    this.control.valueChanges.subscribe(onChangeFn);
  }

  registerOnTouched(onTouched: any): void {
    this.onTouched = onTouched;
  }


}
