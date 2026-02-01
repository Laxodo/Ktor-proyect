package org.example.project.ui.categoria.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.DispatcherIO
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.categoria.listar.GetAllCategoriasUseCase

class CategoriaListViewModel(getAllCategoriasUseCase: GetAllCategoriasUseCase): ViewModel() {
    private val _items = MutableStateFlow<List<CategoriaDto>>(mutableListOf())
    val items: StateFlow<List<CategoriaDto>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<CategoriaDto?>(null)
    val selected = _selected.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(DispatcherIO) {
                _items.value = getAllCategoriasUseCase.invoke()
            }
        }
    }

    fun setSelectedCategoria(item: CategoriaDto?) {
        _selected.value = item
    }
}