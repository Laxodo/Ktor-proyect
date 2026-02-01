package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.aplicacion.categoria.DeleteCategoriaUseCase
import org.example.project.aplicacion.categoria.GetCategoriaByIdUseCase
import org.example.project.aplicacion.categoria.GetProductosFromCategoriaUseCase
import org.example.project.aplicacion.categoria.crear.AddCategoriaUseCase
import org.example.project.aplicacion.categoria.listar.CategoriaDto
import org.example.project.aplicacion.categoria.listar.GetAllCategoriasUseCase
import org.example.project.aplicacion.producto.DeleteProductoUseCase
import org.example.project.aplicacion.producto.GetCategoriaFromProductoUseCase
import org.example.project.aplicacion.producto.GetProductoByIdUseCase
import org.example.project.aplicacion.producto.crear.AddProductoUseCase
import org.example.project.aplicacion.producto.listar.GetAllProductosUseCase
import org.example.project.aplicacion.producto.listar.ProductoDto
import org.koin.core.module.dsl.viewModel
import org.example.project.dominio.ICategoriaRepository
import org.example.project.dominio.IProductoRepository
import org.example.project.infraestructura.repositories.CategoryRepository
import org.example.project.infraestructura.repositories.ProductoRepository
import org.example.project.ui.MainViewModel
import org.example.project.ui.categoria.edit.CategoriaEditViewModel
import org.example.project.ui.categoria.edit.form.CategoriaFormState
import org.example.project.ui.categoria.edit.form.CategoriaFormViewModel
import org.example.project.ui.categoria.list.CategoriaListViewModel
import org.example.project.ui.productos.edit.ProductoEditViewModel
import org.example.project.ui.productos.edit.from.ProductoFormState
import org.example.project.ui.productos.edit.from.ProductoFormViewModel
import org.example.project.ui.productos.list.ProductosListViewModel
import org.koin.dsl.module

val moduloAplicacion = module {
    // casos de uso
    factory { GetAllCategoriasUseCase(get()) }
    factory { AddCategoriaUseCase(get()) }
    factory { DeleteCategoriaUseCase(get()) }
    factory { GetCategoriaByIdUseCase(get()) }
    factory { GetProductosFromCategoriaUseCase(get()) }

    factory { GetProductoByIdUseCase(get()) }
    factory { GetCategoriaFromProductoUseCase(get()) }
    factory { DeleteProductoUseCase(get()) }
    factory { GetAllProductosUseCase(get()) }
    factory { AddProductoUseCase(get()) }
}

val moduloDominio = module {
    single<ICategoriaRepository> { CategoryRepository("http://localhost:8080/categorias",get ()) }
    single<IProductoRepository> { ProductoRepository("http://localhost:8080/productos",get ()) }
}

val moduloUI = module {
    viewModel { MainViewModel() }

    viewModel { CategoriaListViewModel(get()) }
    viewModel { CategoriaEditViewModel(get(), get(), get()) }
    viewModel { (item: CategoriaDto?, onSuccess: (CategoriaFormState) -> Unit) ->
        CategoriaFormViewModel(
            item = item,
            onSuccess = onSuccess
        )
    }
    viewModel { (id: String) ->
        ProductosListViewModel(get(), id = id)
    }
    viewModel { ProductoEditViewModel(get(), get(), get()) }
    viewModel { (item: ProductoDto?, onSuccess: (ProductoFormState) -> Unit) ->
        ProductoFormViewModel(
            item = item,
            get(),
            onSuccess = onSuccess
        )
    }
}

val moduloInfraestructuran = module {
    single {
        HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
}