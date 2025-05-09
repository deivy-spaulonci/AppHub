import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputParcelasComponent } from './input-parcelas.component';

describe('InputParcelasComponent', () => {
  let component: InputParcelasComponent;
  let fixture: ComponentFixture<InputParcelasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputParcelasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputParcelasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
