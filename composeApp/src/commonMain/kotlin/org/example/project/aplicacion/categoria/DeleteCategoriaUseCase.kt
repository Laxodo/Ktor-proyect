package org.example.project.aplicacion.categoria

import org.example.project.dominio.ICategoriaRepository

class DeleteCategoriaUseCase(private val repository: ICategoriaRepository) {
    suspend fun invoke(id: String){
        repository.delete(id)
    }
}