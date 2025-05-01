import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContaTableComponent } from './conta-table.component';

describe('ContaTableComponent', () => {
  let component: ContaTableComponent;
  let fixture: ComponentFixture<ContaTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContaTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContaTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
