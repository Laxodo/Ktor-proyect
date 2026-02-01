package org.example.project.ui.productos.edit.from

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.categoria.edit.CategoriaEditViewModel
import org.example.project.ui.productos.edit.ProductoEditViewModel

@Composable
fun ProductoForm(
    productoEditViewModel: ProductoEditViewModel,
    productoFromViewModel: ProductoFormViewModel,
    categoriaEditViewModel: CategoriaEditViewModel,
    onConfirm: (formState: ProductoFormState) -> Unit,
    onClose: () -> Unit
) {
    val state by productoFromViewModel.uiState.collectAsState()
    val formValid by productoFromViewModel.isFormValid.collectAsState()
    val selected = productoEditViewModel.selected.collectAsState()

    val status: Boolean = selected.value == null

    val categorias = categoriaEditViewModel.items.collectAsState()

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxWidth().padding(16.dp).defaultMinSize(minHeight = 200.dp),
        tonalElevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(24.dp).verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = if (selected.value == null)
                        "Crear nuevo producto"
                    else
                        "Editar producto",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                enabled = status,
                value = state.nombre,
                onValueChange = { productoFromViewModel.onNameChange(it) },
                label = { Text("Nombre del producto")},
                leadingIcon = { Icon(Icons.Default.PersonOutline, contentDescription = null) },
                isError = state.nameError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.nameError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                enabled = status,
                value = state.descripcion,
                onValueChange = { productoFromViewModel.onDescriptionChange(it) },
                label = { Text("Descripción") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                isError = state.descripcionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.descripcionError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                enabled = status,
                value = state.precio,
                onValueChange = { productoFromViewModel.onPriceChange(it) },
                label = { Text("Precio") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                isError = state.precioError != null,
                modifier = Modifier.fillMaxWidth()
            )
            state.precioError?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        enabled = status,
                        checked = state.activo,
                        onCheckedChange = { productoFromViewModel.onEnabledChange(it) }
                    )
                    Text("activo", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            CategoriaComboBox(
                categorias = categorias.value,
                current = state.categoria,
                onSelect = { productoFromViewModel.onCategoryChange(it) },
                status =status
            )

            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalButton(
                    enabled = status,
                    onClick = { productoFromViewModel.clear() }
                ) {
                    Icon(Icons.Default.Autorenew, contentDescription = null)
                }

                Button(
                    onClick = {
                        productoFromViewModel.submit(
                            onSuccess = {
                                onConfirm(productoFromViewModel.uiState.value)
                            },
                            onFailure = {}
                        )
                    },
                    enabled = formValid && status
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                }

                FilledTonalButton(onClick = { onClose() }) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }

        }
    }

}