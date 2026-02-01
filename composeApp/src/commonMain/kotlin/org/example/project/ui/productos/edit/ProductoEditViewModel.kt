package org.example.project.ui.productos.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.aplicacion.producto.DeleteProductoUseCase
import org.example.project.aplicacion.producto.crear.AddProductoCommand
import org.example.project.aplicacion.producto.crear.AddProductoUseCase
import org.example.project.aplicacion.producto.listar.GetAllProductosUseCase
import org.example.project.aplicacion.producto.listar.ProductoDto
import org.example.project.ui.productos.edit.from.ProductoFormState

class ProductoEditViewModel(
    private val deleteProductoUseCase: DeleteProductoUseCase,
    private val addProductoUseCase: AddProductoUseCase,
    private val getAllProductosUseCase: GetAllProductosUseCase
): ViewModel() {
    private val _items = MutableStateFlow<MutableList<ProductoDto>>(mutableListOf())
    val items = _items.asStateFlow()

    private val _selected = MutableStateFlow<ProductoDto?>(null)
    val selected = _selected.asStateFlow()

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    fun setSelectedProducto(item: ProductoDto?) {
        _selected.value = item
    }

    fun add(formState: ProductoFormState) {
        val command = AddProductoCommand(
            formState.nombre,
            formState.descripcion,
            formState.precio,
            formState.categoria?.id!!,
            formState.activo
        )
        viewModelScope.launch {
            addProductoUseCase.invoke(command)
            refresh()
        }
    }

    fun delete(item: ProductoDto) {
        viewModelScope.launch {
            deleteProductoUseCase.invoke(item.id)
            refresh()
        }
    }

    private suspend fun refresh(){
        _items.value.clear()
        var items = getAllProductosUseCase.invoke()
        _items.value = items.toMutableList()
    }


    fun save(item: ProductoFormState) {
        if(_selected.value == null)
            this.add(item)
    }
}