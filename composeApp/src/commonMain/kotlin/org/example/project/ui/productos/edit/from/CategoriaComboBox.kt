package org.example.project.ui.productos.edit.from

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.example.project.aplicacion.categoria.listar.CategoriaDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriaComboBox(
    categorias: List<CategoriaDto>,
    current: CategoriaDto?,
    onSelect: (CategoriaDto) -> Unit,
    status: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            enabled = status,
            value = current?.nombre ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Categoría") },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            categorias.forEach { categoria ->
                DropdownMenuItem(
                    enabled = status,
                    text = { Text(categoria.nombre) },
                    onClick = {
                        onSelect(categoria)
                        expanded = false
                    }
                )
            }
        }
    }
}