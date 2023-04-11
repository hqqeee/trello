import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';

import { HomeRoutingModule } from './home-routing.module';
import { BoardComponent } from './components/board/board.component';
import { ListComponent } from './components/list/list.component';
import { CardComponent } from './components/card/card.component';
import { HomeComponent } from './components/home/home.component';
import { AddNewBoardDialogComponent } from './components/home/add-new-board-dialog/add-new-board-dialog.component';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    BoardComponent,
    ListComponent,
    CardComponent,
    HomeComponent,
    AddNewBoardDialogComponent,
  ],
  imports: [CommonModule, HomeRoutingModule, MatDialogModule, MatButtonModule],
})
export class HomeModule {}
