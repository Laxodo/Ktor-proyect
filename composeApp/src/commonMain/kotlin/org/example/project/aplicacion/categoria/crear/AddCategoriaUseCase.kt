package org.example.project.aplicacion.categoria.crear

import org.example.project.dominio.ICategoriaRepository

class AddCategoriaUseCase(private val repository: ICategoriaRepository) {
    suspend fun invoke(createCategoryCommand: CrearCategoriaCommand){
        repository.create(createCategoryCommand)
    }
}