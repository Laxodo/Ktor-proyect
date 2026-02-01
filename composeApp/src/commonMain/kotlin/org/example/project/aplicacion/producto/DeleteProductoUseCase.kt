package org.example.project.aplicacion.producto

import org.example.project.dominio.IProductoRepository

class DeleteProductoUseCase(private val repository: IProductoRepository) {
    suspend fun invoke(id: String){
        repository.delete(id)
    }
}