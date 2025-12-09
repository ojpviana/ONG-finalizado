import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnimalService } from '../../services/animal.service';
import { Animal } from '../../models/animal';

import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-animal-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './animal-list.component.html',
  styleUrls: ['./animal-list.component.css']
})
export class AnimalListComponent implements OnInit {

  todosAnimais: Animal[] = [];
  animaisFiltrados: Animal[] = [];

  searchTerm: string = '';
  filtroEspecie: string = 'todos';
  mostrarFiltros: boolean = false;

  animalSelecionado: Animal | null = null;

  stats = [
    { label: 'Animais para Adoção', value: '0', icon: '🐾', cor: '#e11d48' },
    { label: 'Adoções no Mês', value: '12', icon: '🏠', cor: '#10b981' },
    { label: 'Voluntários', value: '28', icon: '🤝', cor: '#3b82f6' }
  ];

  constructor(private animalService: AnimalService) {}

  ngOnInit(): void {
    this.carregarAnimais();
  }

  carregarAnimais(): void {
    this.animalService.listar().subscribe({
      next: (dados) => {
        this.todosAnimais = dados.map(a => ({
          ...a,
          fotoUrl: a.fotoUrl || 'https://images.unsplash.com/photo-1552053831-71594a27632d?w=400',
          vacinado: a.vacinado ?? false,
          castrado: a.castrado ?? false,
          urgente: a.urgente ?? false,
          localizacao: a.localizacao || 'Local não informado',
          sexo: a.sexo || 'Não especificado'
        }));

        this.stats[0].value = this.todosAnimais.length.toString();
        this.aplicarFiltros();
      },
      error: (e) => console.error('Erro ao buscar', e)
    });
  }

  aplicarFiltros(): void {
    this.animaisFiltrados = this.todosAnimais.filter(animal => {
      const termo = this.searchTerm.toLowerCase();
      const matchTexto = animal.nome.toLowerCase().includes(termo) ||
                         animal.raca.toLowerCase().includes(termo);

      const matchTipo = this.filtroEspecie === 'todos' ||
                        animal.especie.toLowerCase() === this.filtroEspecie;

      return matchTexto && matchTipo;
    });
  }

  mudarFiltro(tipo: string) {
    this.filtroEspecie = tipo;
    this.aplicarFiltros();
  }

  abrirModal(animal: Animal) {
    this.animalSelecionado = animal;
  }

  fecharModal() {
    this.animalSelecionado = null;
  }
}
