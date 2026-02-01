package org.example.project.ui.productos.edit.from

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.server.routing.RoutingResolveResult
import io.ktor.utils.io.ioDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.coroutineContext
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.DispatcherIO
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.producto.GetCategoriaFromProductoUseCase
import org.example.project.aplicacion.producto.listar.ProductoDto

class ProductoFormViewModel(
    private val item: ProductoDto?,
    private val getCategoriaFromProductoUseCase: GetCategoriaFromProductoUseCase,
    onSuccess: (ProductoFormState) -> Unit
): ViewModel() {

    // TODO: que funcione esta parte

    init {
        if (item != null) viewModelScope.launch {
            withContext(DispatcherIO){
                var itemCategory = getCategoriaFromProductoUseCase.invoke(item.id)
                _uiState.value = _uiState.value.copy(categoria = itemCategory)

            }
        }
    }

    private  val _uiState = MutableStateFlow(ProductoFormState(
        nombre = item?.nombre ?: "",
        descripcion = item?.descripcion ?: "",
        precio = item?.precio?.toString() ?: "",
        categoria = null,
        activo = item?.activo ?: false
    ))

    val uiState: StateFlow<ProductoFormState> = _uiState.asStateFlow()

    val isFormValid: StateFlow<Boolean> = uiState.map { state ->
        if(item==null)
            state.nameError == null &&
                    state.descripcionError == null &&
                    state.precioError == null &&
                    !state.nombre.isBlank() &&
                    !state.descripcion.isBlank() &&
                    !state.precio.isBlank()
        else{
            state.nameError == null &&
                    state.descripcionError == null &&
                    state.precioError == null &&
                    !state.nombre.isBlank() &&
                    !state.descripcion.isBlank() &&
                    !state.precio.isBlank()

        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false
    )

    fun onNameChange(v: String) {
        _uiState.value = _uiState.value.copy(nombre = v, nameError = validateName(v))
    }

    fun onDescriptionChange(v: String) {
        _uiState.value = _uiState.value.copy(descripcion = v, descripcionError = validateDescription(v))
    }

    fun onPriceChange(v: String) {
        _uiState.value = _uiState.value.copy(precio = v, precioError = validatePrice(v))
    }

    fun onEnabledChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(activo = v)
    }

    fun onCategoryChange(v: CategoriaDto) {
        _uiState.value = _uiState.value.copy(categoria =  v)
    }

    fun clear() {
        _uiState.value = ProductoFormState()
    }

    private fun validateName(name: String): String? {
        if (name.isBlank()) return "El nombre es obligatorio"
        if (name.length < 2) return "El nombre es muy corto"
        return null
    }

    private fun validateDescription(description: String): String? {
        if (description.isBlank()) return "La descripción es obligatoria"
        return null
    }

    private fun validatePrice(price: String): String? {
        if (price.isBlank()) return "El precio es obligatorio"
        if (price.toFloatOrNull() == null) return "Formato de precio no valido"
        return null
    }

    fun validateAll(): Boolean {
        val s = _uiState.value
        val nameErr = validateName(s.nombre)
        val descErr = validateDescription(s.descripcion)
        val priceErr = validatePrice(s.precio)
        val newState = s.copy(
            nameError = nameErr,
            descripcionError = descErr,
            precioError =  priceErr,
            submitted = true
        )
        _uiState.value = newState
        return listOf(nameErr, descErr, priceErr).all { it == null }
    }

    fun submit(
        onSuccess: (ProductoFormState) -> Unit,
        onFailure: ((ProductoFormState) -> Unit)? = null
    ) {
        viewModelScope.launch {
            val ok = validateAll()
            if (ok) {
                onSuccess.invoke(_uiState.value)
            } else {
                onFailure?.invoke(_uiState.value)
            }
        }
    }
}