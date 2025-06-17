import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BuyDialogComponent } from './buy-sell-dialog.component';

describe('BuySellDialogComponent', () => {
  let component: BuyDialogComponent;
  let fixture: ComponentFixture<BuyDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BuyDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BuyDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
