import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-information-section',
  templateUrl: './information-section.component.html',
  styleUrls: ['./information-section.component.scss']
})
export class InformationSectionComponent implements OnInit {
  @Input() questionName: string = "";
  show: boolean= false;
  constructor() { }

  ngOnInit(): void {
  }

  toggle() {
    this.show = !this.show;
  }
}
