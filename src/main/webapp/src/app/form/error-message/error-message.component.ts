import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { timer } from 'rxjs';

@Component({
  selector: 'app-error-message',
  templateUrl: './error-message.component.html',
  styleUrls: ['./error-message.component.scss']
})
export class ErrorMessageComponent implements OnInit {
  @Output() dismissError = new EventEmitter();
  dismissDelay = 5000; // 5 sec

  constructor() { }

  ngOnInit(): void {
    timer(this.dismissDelay).subscribe(() => this.dismissError.emit());
  }
}
