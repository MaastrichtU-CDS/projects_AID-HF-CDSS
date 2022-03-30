import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-grade-question',
  templateUrl: './grade-question.component.html',
  styleUrls: ['./grade-question.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: GradeQuestionComponent
    }
  ]
})
export class GradeQuestionComponent implements OnInit, ControlValueAccessor {
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
