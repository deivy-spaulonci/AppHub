import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContaDetailComponent } from './conta-detail.component';

describe('ContaDetailComponent', () => {
  let component: ContaDetailComponent;
  let fixture: ComponentFixture<ContaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContaDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
