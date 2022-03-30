import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BooleanQuestionComponent } from './boolean-question.component';

describe('BooleanQuestionComponent', () => {
  let component: BooleanQuestionComponent;
  let fixture: ComponentFixture<BooleanQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BooleanQuestionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BooleanQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
