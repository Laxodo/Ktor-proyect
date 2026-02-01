package org.example.project.ui


import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.coroutines.flow.MutableStateFlow
import org.example.project.ui.categoria.edit.CategoriaEdit
import org.example.project.ui.categoria.edit.CategoriaEditViewModel
import org.example.project.ui.categoria.edit.form.CategoriaForm
import org.example.project.ui.categoria.edit.form.CategoriaFormState
import org.example.project.ui.categoria.edit.form.CategoriaFormViewModel
import org.example.project.ui.categoria.list.CategoriaList
import org.example.project.ui.categoria.list.CategoriaListViewModel
import org.example.project.ui.productos.edit.ProductoEdit
import org.example.project.ui.productos.edit.ProductoEditViewModel
import org.example.project.ui.productos.edit.from.ProductoForm
import org.example.project.ui.productos.edit.from.ProductoFormState
import org.example.project.ui.productos.edit.from.ProductoFormViewModel
import org.example.project.ui.productos.list.ProductoList
import org.example.project.ui.productos.list.ProductosListViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun Main (){
    val mainViewModel: MainViewModel = koinViewModel()
    val categoriaListViewModel: CategoriaListViewModel = koinViewModel()
    val categoriaEditViewModel: CategoriaEditViewModel = koinViewModel()
    val productoEditViewModel: ProductoEditViewModel = koinViewModel()
    val navController = rememberNavController()

    val options by mainViewModel.options.collectAsState()
    val wai = MutableStateFlow<WindowAdaptiveInfo?>(null)

    mainViewModel.setOptions(
        listOf(
            ItemOption(
                Icons.Default.Category, {
                    navController.navigate(Routes.CategoriaEditRoute) {
                        launchSingleTop = true
                    }
                },
                "Editar categorias"
            ),
            ItemOption(
                Icons.Default.Cake, {
                    navController.navigate(Routes.ProductoEditRoute) {
                        launchSingleTop = true
                    }
                },
                "Editar Producto"
            ),

            ItemOption(
                Icons.Default.List, {
                    navController.navigate(Routes.CategoriaViewListRoute) {
                        launchSingleTop = true
                    }
                },
                "Lista"
            ),
            ItemOption(
                Icons.Default.ArrowBack, {
                    navController.popBackStack()
                },
                "Back"
            )
        )
    )

    val adaptiveInfo = currentWindowAdaptiveInfo()

    val navegador: @Composable () -> Unit = {
        NavHost(
            navController = navController,
            startDestination = Routes.CategoriaViewListRoute
        ){
            composable(Routes.CategoriaViewListRoute) {
                CategoriaList(
                    {
                        categoriaListViewModel.setSelectedCategoria(it)
                        navController.navigate(Routes.ProductosViewListRoute) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Routes.CategoriaEditRoute) {
                CategoriaEdit(
                    mainViewModel,
                    categoriaEditViewModel,
                    {
                        categoriaEditViewModel.setSelectedCategoria(it)
                        navController.navigate(Routes.CategoriaFormRoute) {
                            launchSingleTop = true
                        }
                    },
                    {
                        categoriaEditViewModel.setSelectedCategoria(it)
                        navController.navigate(Routes.CategoriaFormDataRoute) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Routes.CategoriaFormRoute) {
                val categoriaFormViewModel: CategoriaFormViewModel = koinViewModel(){
                    parametersOf(categoriaEditViewModel.selected.value, { it: CategoriaFormState -> {} })
                }
                CategoriaForm(
                    categoriaEditViewModel,
                    categoriaFormViewModel,
                    {
                        categoriaEditViewModel.save(it)
                        navController.popBackStack()
                    },
                    {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.CategoriaFormDataRoute) {
                val categoriaFormViewModel: CategoriaFormViewModel = koinViewModel(){
                    parametersOf(categoriaEditViewModel.selected.value, { it: CategoriaFormState -> {} })
                }
                CategoriaForm(
                    categoriaEditViewModel,
                    categoriaFormViewModel,
                    {
                        navController.popBackStack()
                    },
                    {
                        navController.popBackStack()
                    }
                )
            }




            composable(Routes.ProductosViewListRoute) {
                ProductoList(categoriaListViewModel.selected.value?.id!!)
            }
            composable(Routes.ProductoEditRoute) {
                ProductoEdit(
                    mainViewModel,
                    productoEditViewModel,
                    {
                        productoEditViewModel.setSelectedProducto(it)
                        navController.navigate(Routes.ProductoFormRoute) {
                            launchSingleTop = true
                        }
                    },
                    {
                        productoEditViewModel.setSelectedProducto(it)
                        navController.navigate(Routes.ProductoFormDataRoute) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Routes.ProductoFormRoute) {
                val productoFormViewModel: ProductoFormViewModel = koinViewModel(){
                    parametersOf(productoEditViewModel.selected.value, { it: ProductoFormState -> {} })
                }
                ProductoForm(
                    productoEditViewModel,
                    productoFormViewModel,
                    categoriaEditViewModel,
                    {
                        productoEditViewModel.save(it)
                        navController.popBackStack()
                    },
                    {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.ProductoFormDataRoute) {
                val productoFormViewModel: ProductoFormViewModel = koinViewModel(){
                    parametersOf(productoEditViewModel.selected.value, { it: ProductoFormState -> {} })
                }
                ProductoForm(
                    productoEditViewModel,
                    productoFormViewModel,
                    categoriaEditViewModel,
                    {
                        navController.popBackStack()
                    },
                    {
                        navController.popBackStack()
                    }
                )
            }
        }
    }

    if (wai.collectAsState().value?.windowSizeClass?.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    mainViewModel.options.collectAsState().value.forEach { item ->
                        // if(!item.admin || (item.admin && appViewModel.hasPermission()))
                        NavigationBarItem(
                            selected = true,
                            onClick = { item.action() },
                            icon = { Icon(item.icon, contentDescription = item.name) },
                            )
                    }
                }
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                navegador()
            }
        }
    } else {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    Modifier.then(
                        if (wai.collectAsState().value?.windowSizeClass?.windowWidthSizeClass == WindowWidthSizeClass.COMPACT)
                            Modifier.width(128.dp)
                        else Modifier.width(128.dp)
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight()  // ocupa todo el alto del drawer
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center,  // centra verticalmente
                        horizontalAlignment = Alignment.CenterHorizontally  // opcional: centra horizontalmente
                    ) {
                        Spacer(Modifier.height(16.dp))
                        options.forEach { item ->
                            //si se tienen permiso
                            // if(!item.admin || (item.admin && appViewModel.hasPermission()))
                            NavigationDrawerItem(
                                icon = {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center,

                                        ) {
                                        Icon(
                                            item.icon,
                                            tint = MaterialTheme.colorScheme.primary,
                                            contentDescription = item.name
                                        )
                                    }
                                },
                                label = { wai.collectAsState().value?.windowSizeClass.toString() }, // sin texto
                                selected = false,
                                onClick = { item.action() },
                                modifier = Modifier
                                    .padding(vertical = 4.dp) // espaciado entre items

                            )
                        }
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        // Add a fixed height constraint to prevent "Size out of range" error
                        .height(600.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    navegador()
                }
            }
        )
    }



}