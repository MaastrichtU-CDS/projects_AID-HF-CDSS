import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';


@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss']
})
export class ResultComponent implements OnInit {
  @Input() result: string = ""
  @Output() restartEvent = new EventEmitter();

  constructor() { }

  ngOnInit(): void {}

}

