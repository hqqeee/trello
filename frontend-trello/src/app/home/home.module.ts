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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { AddListDialogComponent } from './components/board/add-list-dialog/add-list-dialog/add-list-dialog.component';

@NgModule({
  declarations: [
    BoardComponent,
    ListComponent,
    CardComponent,
    HomeComponent,
    AddNewBoardDialogComponent,
    AddListDialogComponent,
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    MatDialogModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCardModule,
    MatListModule,
    FormsModule,
  ],
})
export class HomeModule {}
