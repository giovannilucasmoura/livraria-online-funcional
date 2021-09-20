package main.scala

import java.time.LocalDate
import scala.io.StdIn

object Main extends App {
    def inicio(): Unit = {
        val clientes_dados = Dados.carregarClientesCsv("src/main/resources/clientes.csv")
        val autores_dados = Dados.carregarAutoresCsv("src/main/resources/autores.csv")
        val areas_cientificas_dados = Dados.carregarAreasCientificasCsv("src/main/resources/areasCientificas.csv")
        val livros_dados = Dados.carregarLivrosCsv("src/main/resources/livros.csv")
        val compras_dados = Dados.carregarComprasCsv("src/main/resources/compras.csv")
        val livro_autor_dados = Dados.carregarLivroAutorCsv("src/main/resources/livro_autor.csv")
        val compra_livro_dados = Dados.carregarCompraLivroCsv("src/main/resources/compra_livro.csv")

        println("Bem vindo(a) a Livraria Online \n")
        println("Funcionalidades disponíveis: \n")
        println("1 - Listar vendas por publicação/livro")
        println("2 - Listar compras por cliente")
        println("3 - Listar publicações por domínio/área científica")
        println("4 - Listar publicações por autor") // TODO
        println("5 - Listar vendas por mês \n") // TODO
        print("Por favor digite o valor da funcionalidade que você quer utilizar: ")

        var valor: Int = 0

        try {
            valor = StdIn.readLine().toInt
        } catch {
            case _: Exception =>
                print("Valor Inválido")
                StdIn.readLine()
                inicio()
        }

        var retorno = 0

        if(valor == 1) {
            retorno = escolhaVendasPorPublicacao(livros_dados,
                compras_dados,
                compra_livro_dados)
        } else if(valor == 2) {
            retorno = escolhaComprasPorCliente(clientes_dados, compras_dados)
        } else if(valor == 3) {
            retorno = escolhaLivrosPorAreaCientifica(livros_dados, areas_cientificas_dados)
        } else if(valor == 4) {
            retorno = escolhaLivrosPorAutor(livros_dados, autores_dados, livro_autor_dados)
        } else if(valor == 5){
            retorno = escolhaComprasPorMes(compras_dados)
        }


        inicio()
    }

    def escolhaVendasPorPublicacao(livros: List[Map[String, _]],
                                   vendas: List[Map[String, _]],
                                   compra_livros: List[Map[String, _]],
                                  ): Int = {
        println("Livraria Online - Seleção de vendas por publicação \n")
        println("Livros disponíveis no sistema: \n")
        println("ID - Nome - Editora - Ano de Publicação - Preço - Área Científica(id)")
        livros.map(livro => Funcoes.imprimirLivro(livro))
        println("\nInsira 0 para voltar ou")
        print("Insira o ID do livro que você quer ver as respectivas vendas: ")

        val livro_id: Int = StdIn.readLine().toInt

        if(livro_id == 0){
            0
        } else {
            val livro_busca: Option[Map[String, _]] = livros.find(livro => livro("id") == livro_id)
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
        val ids_vendas: List[Any] = Funcoes.vendasPorPublicacao(compra_livro, livro("id"))

        Funcoes.listarVendasPorId(ids_vendas, vendas).map(
            venda => Funcoes.imprimirVenda(venda)
        )

        println("\nInsira 0 para retornar ao inicio ou")
        print("1 para buscar vendas de outra publicação: ")

        StdIn.readLine().toInt
    }

    def escolhaComprasPorCliente(clientes: List[Map[String, _]],
                                 compras: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de compras por cliente \n")
        println("Clientes registrados no sistema: \n")
        println("ID - Nome - Apelido - Nacionalidade - Email - Senha - Cartão de Crédito - Profissão - Endereço - Telefone")
        clientes.map(cliente => Funcoes.imprimirCliente(cliente))
        println("\nInsira 0 para voltar ou")
        print("Insira o ID do cliente que você quer ver as respectivas compras: ")

        val cliente_id: Int = StdIn.readLine().toInt

        if(cliente_id == 0){
            0
        } else {
            val cliente_busca: Option[Map[String, _]] = clientes.find(cliente => cliente("id") == cliente_id)
            var retorno: Int = listarComprasPorCliente(cliente_busca.get, compras)

            if(retorno != 0){
                retorno = escolhaComprasPorCliente(clientes, compras)
            }

            0
        }
    }

    def listarComprasPorCliente(cliente: Map[String, _],
                                compras: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de vendas por publicação \n")
        println("Listando compras feitas pelo cliente: ")
        Funcoes.imprimirCliente(cliente)
        println("\nVendas -")
        println("ID - Data - Cliente(id)")
        Funcoes.comprasPorCliente(compras, cliente("id")).map(
            venda => Funcoes.imprimirVenda(venda)
        )

        println("\nInsira 0 para retornar ao inicio ou")
        print("1 para buscar compras de outro cliente: ")

        StdIn.readLine().toInt
    }

    def escolhaLivrosPorAreaCientifica(livros: List[Map[String, _]],
                                       areas_cientificas: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de livros por área científica \n")
        println("Livros registrados no sistema: \n")
        println("ID - Descrição")
        areas_cientificas.map(area_cientifica => Funcoes.imprimirAreaCientifica(area_cientifica))
        println("\nInsira 0 para voltar ou")
        print("Insira o ID da área científica que você quer ver os respectivos livros: ")

        val area_cientifica_id: Int = StdIn.readLine().toInt

        if(area_cientifica_id == 0){
            0
        } else {
            val area_cientifica_busca: Option[Map[String, _]] = areas_cientificas.find(area => area("id") == area_cientifica_id)
            var retorno: Int = listarLivrosPorAreaCientifica(area_cientifica_busca.get, livros)

            if(retorno != 0){
                retorno = escolhaLivrosPorAreaCientifica(livros, areas_cientificas)
            }

            0
        }
    }

    def listarLivrosPorAreaCientifica(area_cientifica: Map[String, _],
                                      livros: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de livros por área científica \n")
        println("Listando livros da área científica: ")
        Funcoes.imprimirAreaCientifica(area_cientifica)
        println("\nLivros -")
        println("ID - Nome - Editora - Ano de Publicação - Preço - Área Científica(id)")
        Funcoes.livrosPorAreaCientifica(livros, area_cientifica("id")).map(
            livro => Funcoes.imprimirLivro(livro)
        )

        println("\nInsira 0 para retornar ao inicio ou")
        print("1 para buscar livros por outra área científica: ")

        StdIn.readLine().toInt
    }

    def escolhaLivrosPorAutor(livros: List[Map[String, _]],
                              autores: List[Map[String, _]],
                              livro_autor: List[Map[String, _]],
                                  ): Int = {
        println("Livraria Online - Seleção de livros por autor \n")
        println("Autores registrados no sistema: \n")
        println("ID - Nome - Apelido - Nacionalidade")
        autores.map(autor => Funcoes.imprimirAutor(autor))
        println("\nInsira 0 para voltar ou")
        print("Insira o ID do autor que você quer ver os respectivos livros: ")

        val autor_id: Int = StdIn.readLine().toInt

        if(autor_id == 0){
            0
        } else {
            val autor_busca: Option[Map[String, _]] = autores.find(autor => autor("id") == autor_id)
            var retorno: Int = listarLivrosPorAutor(autor_busca.get, livros, livro_autor)

            if(retorno != 0){
                retorno = escolhaLivrosPorAutor(livros,
                    autores,
                    livro_autor)
            }

            0
        }
    }

    def listarLivrosPorAutor(autor: Map[String, _],
                             livros: List[Map[String, _]],
                             livro_autor: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de livros por autor \n")
        println("Listando livros que têm como autor(a): ")
        Funcoes.imprimirAutor(autor)
        println("\nLivros -")
        println("ID - Nome - Editora - Ano de Publicação - Preço - Área Científica(id)")
        val ids_livros: List[Any] = Funcoes.livrosPorAutor(livro_autor, autor("id"))

        Funcoes.listarLivrosPorId(ids_livros, livros).map(
            livro => Funcoes.imprimirLivro(livro)
        )

        println("\nInsira 0 para retornar ao inicio ou")
        print("1 para buscar livros por outros autores: ")

        StdIn.readLine().toInt
    }

    def escolhaComprasPorMes(vendas: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de compras por mês \n")
        println("Meses que contém compras registrados no sistema: \n")
        println("Mês - Ano")

        // Lista de todas as datas diferentes
        var datas: List[LocalDate] = List()
        vendas.map(venda => datas = venda("data").asInstanceOf[LocalDate] :: datas)

        // Removendo todas as duplicatas de mês/ano
        datas = datas.map(data => LocalDate.of(data.getYear, data.getMonth, 1)).distinct

        // Adicionando em uma lista
        var selecaoDatas: List[LocalDate] = List()
        datas.map(data => {
            selecaoDatas = data :: selecaoDatas
        })

        // Ordenar e imprimir
        selecaoDatas.sortBy(_.getMonthValue).map(data => println(data.getMonthValue + "/" + data.getYear))

        println("\nInsira 0 para voltar ou")
        print("Selecione o mês(número) que você quer buscar as compras: ")
        val valorMes = StdIn.readLine().toInt

        if (valorMes == 0) return 0

        print("Selecione o ano que você quer buscar as compras: ")
        val valorAno = StdIn.readLine().toInt

        var retorno: Int = listarComprasPorMes(valorMes, valorAno, vendas)

        if (retorno != 0) {
            retorno = escolhaComprasPorMes(vendas)
        }
        0
    }

    def listarComprasPorMes(valorMes: Int,
                            valorAno: Int,
                            vendas: List[Map[String, _]]): Int = {
        println("Livraria Online - Seleção de compras por mês \n")
        println("Listando compras feitas no mês de: ")
        println(valorMes + "/" + valorAno)
        println("\nVendas -")
        println("ID - Data - Cliente(id)")
        Funcoes.comprasPorMes(vendas, valorMes, valorAno).map(venda => Funcoes.imprimirVenda(venda))

        println("\nInsira 0 para retornar ao inicio ou")
        print("1 para buscar compras por outra data: ")

        StdIn.readLine().toInt
    }

    inicio()
}
