package org.example.project.ui.categoria.edit.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.aplicacion.categoria.listar.CategoriaDto

class CategoriaFormViewModel(
    private val item: CategoriaDto?,
    onSuccess: (CategoriaFormState) -> Unit
): ViewModel() {
    private val _uiState = MutableStateFlow(CategoriaFormState(
        nombre = item?.nombre ?: "",
        descripcion = item?.descripcion ?: "",
        activo = item?.activo ?: false
    ))
    val uiState: StateFlow<CategoriaFormState> = _uiState.asStateFlow()
    val isFormValid: StateFlow<Boolean> = uiState.map { state ->
        if(item==null)
            state.nameError == null &&
                    state.descriptionError == null &&
                    !state.nombre.isBlank() &&
                    !state.descripcion.isBlank()
        else{
            state.nameError == null &&
                    state.descriptionError == null &&
                    !state.nombre.isBlank() &&
                    !state.descripcion.isBlank()

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
        _uiState.value = _uiState.value.copy(descripcion = v, descriptionError = validateDescription(v))
    }

    fun onEnabledChange(v: Boolean) {
        _uiState.value = _uiState.value.copy(activo = v)
    }

    fun clear() {
        _uiState.value = CategoriaFormState()
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

    fun validateAll(): Boolean {
        val s = _uiState.value
        val nameErr = validateName(s.nombre)
        val descErr = validateDescription(s.descripcion)
        val newState = s.copy(
            nameError = nameErr,
            descriptionError = descErr,
            submitted = true
        )
        _uiState.value = newState
        return listOf(nameErr, descErr).all { it == null }
    }

    fun submit(
        onSuccess: (CategoriaFormState) -> Unit,
        onFailure: ((CategoriaFormState) -> Unit)? = null
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