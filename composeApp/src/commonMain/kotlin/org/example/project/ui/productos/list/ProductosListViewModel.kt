package org.example.project.ui.productos.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.DispatcherIO
import org.example.project.aplicacion.categoria.GetProductosFromCategoriaUseCase
import org.example.project.aplicacion.producto.listar.ProductoDto

class ProductosListViewModel(getProductosFromCategoriaUseCase: GetProductosFromCategoriaUseCase, id: String): ViewModel() {
    private val _items = MutableStateFlow<List<ProductoDto>>(mutableListOf())
    val items: StateFlow<List<ProductoDto>> = _items.asStateFlow()

    private val _selected = MutableStateFlow<ProductoDto?>(null)
    val selected: StateFlow<ProductoDto?> = _selected.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(DispatcherIO) {
                _items.value =getProductosFromCategoriaUseCase.invoke(id)
            }
        }
    }

    fun setSelectedProduct(item: ProductoDto?) {
        _selected.value = item
    }

}