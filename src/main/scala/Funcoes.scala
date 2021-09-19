package main.scala

object Funcoes {
    // -- VENDAS --

    // Nota sobre a busca recursiva:
    // Essa função retorna uma lista de IDs das vendas encontradas
    // e a função abaixo retorna as vendas com um array de IDs recebidos
    // Essas funções para retornar os IDs e as Vendas em si
    // foram separadas pra diminuir a complexidade de cada uma
    def vendasPorPublicacao(compra_livro: List[Map[String, _]],
                           livro_id: Any,
                           vendasEncontradas: List[Any] = Nil): List[Any] = {
        if (compra_livro.isEmpty) vendasEncontradas
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
        if(vendas.isEmpty) vendasEncontradas
        else {
            if(ids.contains(vendas.head("id"))) {
                listarVendasPorId(ids, vendas.tail, vendas.head :: vendasEncontradas)
            } else {
                listarVendasPorId(ids, vendas.tail, vendasEncontradas)
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
        println(livro("dominio"))
    }

    def imprimirVenda(venda: Map[String, _]) = {
        print(venda("id") + " - ")
        print(venda("data") + " - ")
        println(venda("cliente_id"))
    }
}
