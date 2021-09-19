package main.scala

import main.scala.Dados
import main.scala.Funcoes
import scala.io.StdIn

object Main extends App {
    def inicio(): Unit = {
        val clientes_dados = Dados.carregarClientesCsv("src/main/resources/clientes.csv")
        val autores_dados = Dados.carregarAutoresCsv("src/main/resources/autores.csv")
        val dominios_dados = Dados.carregarAreasCientificasCsv("src/main/resources/areasCientificas.csv")
        val livros_dados = Dados.carregarLivrosCsv("src/main/resources/livros.csv")
        val compras_dados = Dados.carregarComprasCsv("src/main/resources/compras.csv")
        val livro_autor_dados = Dados.carregarLivroAutorCsv("src/main/resources/livro_autor.csv")
        val compra_livro_dados = Dados.carregarCompraLivroCsv("src/main/resources/compra_livro.csv")

        println("Bem vindo(a) a Livraria Online \n")
        println("Funcionalidades disponíveis: \n")
        println("1 - Listar vendas por publicação/livro")
        println("2 - Listar compras por cliente") // TODO
        println("3 - Listar publicações por domínio/área científica") // TODO
        println("4 - Listar publicações por autor") // TODO
        println("5 - Listar vendas por mês \n") // TODO
        print("Por favor digite o valor da funcionalidade que você quer utilizar: ")

        var valor: Int = 0

        try {
            valor = StdIn.readLine().toInt
        } catch {
            case _: Exception => {
                print("Valor Inválido")
                StdIn.readLine()
                return inicio()
            }
        }

        var retorno = 0

        if(valor == 1) {
            retorno = escolhaVendasPorPublicacao(livros_dados,
                compras_dados,
                compra_livro_dados)
        }

        if(retorno == 0) {
            return inicio()
        }

    }

    def escolhaVendasPorPublicacao(livros: List[Map[String, _]],
                                   vendas: List[Map[String, _]],
                                   compra_livros: List[Map[String, _]],
                                  ): Int = {
        println("Livraria Online - Seleção de vendas por publicação \n")
        println("Livros disponíveis no sistema: \n")
        println("ID - Nome - Editora - Ano de Publicação - Preço - Área Científica(id)")
        livros.map(livro => Funcoes.imprimirLivro(livro))
        println("Insira \"0\" para voltar ou")
        println("Insira o ID do livro que você quer ver as respectivas vendas: ")

        var livro_id: Int = StdIn.readLine().toInt

        if(livro_id == 0){
            return 0;
        } else {
            var livro_busca: Option[Map[String, _]] = livros.find(livro => livro("id") == livro_id)
            var retorno: Int = listarVendasPorPublicacao(livro_busca.get, vendas, compra_livros)

            if(retorno != 0){
                retorno = escolhaVendasPorPublicacao(livros,
                    vendas,
                    compra_livros)
            }

            0
        }
    }

    def listarVendasPorPublicacao(livro: Map[String, _],
                                  vendas: List[Map[String, _]],
                                  compra_livro: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de vendas por publicação \n")
        println("Listando vendas que contém o livro: ")
        Funcoes.imprimirLivro(livro)
        println("\nVendas -")
        println("ID - Data - Cliente(id)")
        var ids_vendas: List[Any] = Funcoes.vendasPorPublicacao(compra_livro, livro.get("id").get)

        Funcoes.listarVendasPorId(ids_vendas, vendas).map(
            venda => Funcoes.imprimirVenda(venda)
        )

        println("\nInsira 0 para retornar ao inicio ou")
        print("1 para buscar vendas de outra publicação: ")

        StdIn.readLine().toInt
    }

    inicio()
}
