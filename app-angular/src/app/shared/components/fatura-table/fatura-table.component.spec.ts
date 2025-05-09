import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FaturaTableComponent } from './fatura-table.component';

describe('FaturaTableComponent', () => {
  let component: FaturaTableComponent;
  let fixture: ComponentFixture<FaturaTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FaturaTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FaturaTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
