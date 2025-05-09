import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComboDefaultComponent } from './combo-default.component';

describe('ComboDefaultComponent', () => {
  let component: ComboDefaultComponent;
  let fixture: ComponentFixture<ComboDefaultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComboDefaultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComboDefaultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
