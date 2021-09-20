package main.scala

import java.time.LocalDate

object Funcoes {
    // FUNCIONALIDADE 1: VENDAS POR PUBLICAÇÃO

    // Nota sobre a busca recursiva:
    // Essa função retorna uma lista de IDs das vendas encontradas
    // e a função abaixo retorna as vendas com um array de IDs recebidos
    // devido ao fato que é uma relação Many To Many / n pra n
    // Essas funções para retornar os IDs e as Vendas em si
    // foram separadas pra diminuir a complexidade de cada uma
    def vendasPorPublicacao(compra_livro: List[Map[String, _]],
                           livro_id: Any,
                           vendasEncontradas: List[Any] = Nil): List[Any] = {
        if (compra_livro.isEmpty) vendasEncontradas.reverse
        else  {
            if (compra_livro.head("livro_id") == livro_id){
                vendasPorPublicacao(compra_livro.tail, livro_id, compra_livro.head("compra_id") :: vendasEncontradas)
            } else {
                vendasPorPublicacao(compra_livro.tail, livro_id, vendasEncontradas)
            }
        }
    }

    def listarVendasPorId(ids: List[Any],
                         vendas: List[Map[String, _]],
                         vendasEncontradas: List[Map[String, _]] = Nil): List[Map[String, _]] = {
        if(vendas.isEmpty) vendasEncontradas.reverse
        else {
            if(ids.contains(vendas.head("id"))) {
                listarVendasPorId(ids, vendas.tail, vendas.head :: vendasEncontradas)
            } else {
                listarVendasPorId(ids, vendas.tail, vendasEncontradas)
            }
        }
    }

    // FUNCIONALIDADE 2: COMPRAS POR CLIENTE

    // Nota: Como a relação é 1 pra n em compra -> cliente
    // apenas uma função é necessária
    def comprasPorCliente(compras: List[Map[String, _]],
                         cliente_id: Any,
                         comprasEncontradas: List[Map[String, _]] = Nil): List[Map[String, _]] = {
        if (compras.isEmpty) comprasEncontradas.reverse
        else  {
            if (compras.head("cliente_id") == cliente_id){
                comprasPorCliente(compras.tail, cliente_id, compras.head :: comprasEncontradas)
            } else {
                comprasPorCliente(compras.tail, cliente_id, comprasEncontradas)
            }
        }
    }

    // FUNCIONALIDADE 3: LIVROS POR ÁREA CIENTÍFICA
    def livrosPorAreaCientifica(livros: List[Map[String, _]],
                                area_cientifica_id: Any,
                                livrosEncontrados: List[Map[String, _]] = Nil): List[Map[String, _]] = {
        if (livros.isEmpty) livrosEncontrados.reverse
        else  {
            if (livros.head("area_cientifica_id") == area_cientifica_id){
                livrosPorAreaCientifica(livros.tail, area_cientifica_id, livros.head :: livrosEncontrados)
            } else {
                livrosPorAreaCientifica(livros.tail, area_cientifica_id, livrosEncontrados)
            }
        }
    }

    // FUNCIONALIDADE 4: LIVROS POR AUTOR

    // Relação Many to Many, requer duas funções como vendasPorPublicacao
    def livrosPorAutor(livro_autor: List[Map[String, _]],
                       autor_id: Any,
                       livrosEncontrados: List[Any] = Nil): List[Any] = {
        if (livro_autor.isEmpty) livrosEncontrados.reverse
        else  {
            if (livro_autor.head("autor_id") == autor_id){
                livrosPorAutor(livro_autor.tail, autor_id, livro_autor.head("livro_id") :: livrosEncontrados)
            } else {
                livrosPorAutor(livro_autor.tail, autor_id, livrosEncontrados)
            }
        }
    }

    def listarLivrosPorId(ids: List[Any],
                          livros: List[Map[String, _]],
                          livrosEncontrados: List[Map[String, _]] = Nil): List[Map[String, _]] = {
        if(livros.isEmpty) livrosEncontrados.reverse
        else {
            if(ids.contains(livros.head("id"))) {
                listarLivrosPorId(ids, livros.tail, livros.head :: livrosEncontrados)
            } else {
                listarLivrosPorId(ids, livros.tail, livrosEncontrados)
            }
        }
    }

    // FUNCIONALIDADE 5: COMPRAS POR MÊS
    def comprasPorMes(vendas: List[Map[String, _]],
                      valorMes: Int,
                      valorAno: Int,
                      vendasEncontradas: List[Map[String, _]] = Nil): List[Map[String, _]] = {
        if (vendas.isEmpty) vendasEncontradas.reverse
        else  {
            val data: LocalDate = vendas.head("data").asInstanceOf[LocalDate]
            if (data.getMonthValue == valorMes && data.getYear == valorAno){
                comprasPorMes(vendas.tail, valorMes, valorAno, vendas.head :: vendasEncontradas)
            } else {
                comprasPorMes(vendas.tail, valorMes, valorAno, vendasEncontradas)
            }
        }
    }

    // Funções de impressão
    def imprimirLivro(livro: Map[String, _]) = {
        print(livro("id") + " - ")
        print(livro("titulo") + " - ")
        print(livro("ISBN") + " - ")
        print(livro("editora") + " - ")
        print(livro("anoPublicacao") + " - R$")
        print(livro("preco") + " - ")
        println(livro("area_cientifica_id"))
    }

    def imprimirVenda(venda: Map[String, _]) = {
        print(venda("id") + " - ")
        print(venda("data") + " - ")
        println(venda("cliente_id"))
    }

    def imprimirCliente(cliente: Map[String, _]) = {
        print(cliente("id") + " - ")
        print(cliente("nome") + " - ")
        print(cliente("apelido") + " - ")
        print(cliente("nacionalidade") + " - ")
        print(cliente("email") + " - ")
        print(cliente("senha") + " - ")
        print(cliente("numeroCartaoCredito") + " - ")
        print(cliente("profissao") + " - ")
        print(cliente("endereco") + " - ")
        println(cliente("telefone"))
    }

    def imprimirAreaCientifica(area_cientifica: Map[String, _]) = {
        print(area_cientifica("id") + " - ")
        println(area_cientifica("descricao"))
    }

    def imprimirAutor(autor: Map[String, _]) = {
        print(autor("id") + " - ")
        print(autor("nome") + " - ")
        print(autor("apelido") + " - ")
        println(autor("nacionalidade"))
    }
}
