package main.scala

import main.scala.Dados

object Main extends App {
    var clientes = Dados.carregarClientesCsv("src/main/resources/clientes.csv")
    var autores = Dados.carregarAutoresCsv("src/main/resources/autores.csv")
    var dominios = Dados.carregarAreasCientificasCsv("src/main/resources/areasCientificas.csv")
    var livros = Dados.carregarLivrosCsv("src/main/resources/livros.csv")
    var compras = Dados.carregarComprasCsv("src/main/resources/compras.csv")
    var livro_autor = Dados.carregarLivroAutorCsv("src/main/resources/livro_autor.csv")
    var compra_livro = Dados.carregarCompraLivroCsv("src/main/resources/compra_livro.csv")
}
