package org.example.project.ui.categoria.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.aplicacion.categoria.DeleteCategoriaUseCase
import org.example.project.aplicacion.categoria.crear.AddCategoriaUseCase
import org.example.project.aplicacion.categoria.crear.CrearCategoriaCommand
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.categoria.listar.GetAllCategoriasUseCase
import org.example.project.ui.categoria.edit.form.CategoriaFormState

class CategoriaEditViewModel(
    private val borrarCategoriaUseCase: DeleteCategoriaUseCase,
    private val crearCategoriaUseCase: AddCategoriaUseCase,
    private val listarCategoriaUseCase: GetAllCategoriasUseCase
    ): ViewModel() {
    private val _items = MutableStateFlow<MutableList<CategoriaDto>>(mutableListOf())
    val items: StateFlow<List<CategoriaDto>> = _items.asStateFlow()
    private val _selected = MutableStateFlow<CategoriaDto?>(null)
    val selected = _selected.asStateFlow()

    init {
        viewModelScope.launch {
            refresh()
        }
    }

    fun setSelectedCategoria(item: CategoriaDto?) {
        _selected.value = item
    }

    fun add(formState: CategoriaFormState) {
        val command = CrearCategoriaCommand(
            formState.nombre,
            formState.descripcion,
            formState.activo
        )
        viewModelScope.launch {
            crearCategoriaUseCase.invoke(command)
            refresh()
        }
    }

    fun delete(item: CategoriaDto) {
        viewModelScope.launch {
            borrarCategoriaUseCase.invoke(item.id)
            refresh()
        }
    }

    private suspend fun refresh(){
        _items.value.clear()
        var items = listarCategoriaUseCase.invoke()
        _items.value = items.toMutableList()
    }

    fun save(item: CategoriaFormState) {
        if(_selected.value == null)
            this.add(item)
    }
}