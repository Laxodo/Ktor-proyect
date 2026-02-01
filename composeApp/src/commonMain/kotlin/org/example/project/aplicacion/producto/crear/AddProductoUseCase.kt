package org.example.project.aplicacion.producto.crear

import org.example.project.dominio.IProductoRepository


class AddProductoUseCase(private val repository: IProductoRepository) {
    suspend fun invoke(addProductoCommand: AddProductoCommand){
        repository.create(addProductoCommand)
    }
}