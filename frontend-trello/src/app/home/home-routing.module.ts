import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BoardComponent } from './components/board/board.component';
import { HomeComponent } from './components/home/home.component';
import { boardResolver } from './components/home/resolvers/board.resolver';

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
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule {}
