import { Routes } from '@angular/router';
import { AnimalListComponent } from './components/animal-list/animal-list.component';
import { AnimalFormComponent } from './components/animal-form/animal-form.component';

export const routes: Routes = [
    { path: '', component: AnimalListComponent },
    { path: 'cadastrar-animal', component: AnimalFormComponent }
];
