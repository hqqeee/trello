import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoardComponent } from './components/board/board.component';
import { HomeComponent } from './components/home/home.component';
import { boardResolver, boardsResolver } from './components/home/resolvers/board.resolver';

const routes: Routes = [
  {
    path: 'board/:id',
    component: BoardComponent,
    resolve: { board: boardResolver },
  },
  {
    path: '',
    title: 'My Boards',
    component: HomeComponent,
    resolve: { boards: boardsResolver },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule {}
