import { TestBed } from '@angular/core/testing';

import { AdviceApiService } from './advice-api.service';

describe('AdviceApiService', () => {
  let service: AdviceApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdviceApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
