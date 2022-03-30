import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'AID-HF';
  advice: string | null = null
  constructor(private translate: TranslateService) {}

  useLanguage(language: string): void {
    this.translate.use(language);
  }
  
  setAdvice(advice: string) {
    this.advice = advice;
  }

  clearAdvice() {
    this.advice = null;
  }
}
