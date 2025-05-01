import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DespesaTableComponent } from './despesa-table.component';

describe('DespesaTableComponent', () => {
  let component: DespesaTableComponent;
  let fixture: ComponentFixture<DespesaTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DespesaTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DespesaTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
