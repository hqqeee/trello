import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HomeRoutingModule } from './home-routing.module';
import { BoardComponent } from './components/board/board.component';

@NgModule({
  declarations: [BoardComponent],
  imports: [CommonModule, HomeRoutingModule],
})
export class HomeModule {}
