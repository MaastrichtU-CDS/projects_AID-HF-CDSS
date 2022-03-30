import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FlexModule } from '@angular/flex-layout/flex';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';

import { FormComponent } from './form/form.component';
import { BooleanQuestionComponent } from './form/form-element/boolean-question/boolean-question.component';
import { GradeQuestionComponent } from './form/form-element/grade-question/grade-question.component';
import { InformationSectionComponent } from './form/information-section/information-section.component';
import { ResultComponent } from './result/result.component';
import { ErrorMessageComponent } from './form/error-message/error-message.component';


@NgModule({
  declarations: [
    AppComponent,
    FormComponent,
    ResultComponent,
    BooleanQuestionComponent,
    GradeQuestionComponent,
    InformationSectionComponent,
    ErrorMessageComponent
  ],
  imports: [
    BrowserModule,
    FlexModule,
    HttpClientModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      defaultLanguage: 'nl-NL',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
    }
    })
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}