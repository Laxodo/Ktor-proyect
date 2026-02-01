package org.example.project.ui.categoria.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoriaList(
    onSelectItem: (CategoriaDto?) -> Unit
) {
    val vm: CategoriaListViewModel = koinViewModel()
    val items by vm.items.collectAsState()
    val filteredItems  = items.filter { it.activo }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(
                minSize = 512.dp
            ),
        ){
            items(filteredItems.size) { item ->
                CategoriaListCard(filteredItems.get(item), {
                    onSelectItem(it)
                })
            }
        }
    }
}