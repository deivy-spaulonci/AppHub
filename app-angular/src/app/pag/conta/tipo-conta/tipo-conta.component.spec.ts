import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TipoContaComponent } from './tipo-conta.component';

describe('TipoContaComponent', () => {
  let component: TipoContaComponent;
  let fixture: ComponentFixture<TipoContaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TipoContaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TipoContaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
