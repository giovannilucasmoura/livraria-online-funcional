package main.scala

import com.opencsv.CSVParserBuilder

import java.io.FileInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.io.Source;

object Dados {
    val formato_data = "yyyy-MM-dd"

    val parser = new CSVParserBuilder()
        .withSeparator(',')
        .withEscapeChar('\"')
        .withQuoteChar('\'')
        .build()

    def csvParser(arquivo: String) = {
        Source.fromInputStream(new FileInputStream(arquivo))
            .getLines()
            .drop(1) // Pular linha do cabeçalho
    }

    def carregarClientesCsv(arquivo: String) = {
        var linhas = csvParser(arquivo)

        linhas.map(linha => Map(
            ("id" -> parser.parseLine(linha)(0).toInt),
            ("nome" -> parser.parseLine(linha)(1)),
            ("apelido" -> parser.parseLine(linha)(2)),
            ("nacionalidade" -> parser.parseLine(linha)(3)),
            ("email" -> parser.parseLine(linha)(4)),
            ("senha" -> parser.parseLine(linha)(5)),
            ("numeroCartaoCredito" -> parser.parseLine(linha)(6)),
            ("profissao" -> parser.parseLine(linha)(7)),
            ("endereco" -> parser.parseLine(linha)(8)),
            ("telefone" -> parser.parseLine(linha)(9))
        )).toList
    }

    def carregarAutoresCsv(arquivo: String) = {
        var linhas = csvParser(arquivo)

        linhas.map(linha => Map(
            ("id" -> parser.parseLine(linha)(0).toInt),
            ("nome" -> parser.parseLine(linha)(1)),
            ("apelido" -> parser.parseLine(linha)(2)),
            ("nacionalidade" -> parser.parseLine(linha)(3))
        )).toList
    }

    def carregarAreasCientificasCsv(arquivo: String) = {
        var linhas = csvParser(arquivo)

        linhas.map(linha => Map(
            ("id" -> parser.parseLine(linha)(0).toInt),
            ("descricao" -> parser.parseLine(linha)(1))
        )).toList
    }

    def carregarLivrosCsv(arquivo: String) = {
        var linhas = csvParser(arquivo)

        linhas.map(linha => Map(
            ("id" -> parser.parseLine(linha)(0).toInt),
            ("titulo" -> parser.parseLine(linha)(1)),
            ("ISBN" -> parser.parseLine(linha)(2)),
            ("editora" -> parser.parseLine(linha)(3)),
            ("anoPublicacao" -> parser.parseLine(linha)(4).toInt),
            ("preco" -> parser.parseLine(linha)(5)),
            ("area_cientifica_id" -> parser.parseLine(linha)(6).toInt)
        )).toList
    }

    def carregarComprasCsv(arquivo: String) = {
        var linhas = csvParser(arquivo)
        val dtf = DateTimeFormatter.ofPattern(formato_data)

        linhas.map(linha => Map(
            ("id" -> parser.parseLine(linha)(0).toInt),
            ("data" -> LocalDate.parse(parser.parseLine(linha)(1), dtf)),
            ("cliente_id" -> parser.parseLine(linha)(2).toInt)
        )).toList
    }

    def carregarLivroAutorCsv(arquivo: String) = {
        var linhas = csvParser(arquivo)

        linhas.map(linha => Map(
            ("livro_id" -> parser.parseLine(linha)(0).toInt),
            ("autor_id" -> parser.parseLine(linha)(1).toInt)
        )).toList
    }

    def carregarCompraLivroCsv(arquivo: String) = {
        var linhas = csvParser(arquivo)

        linhas.map(linha => Map(
            ("compra_id" -> parser.parseLine(linha)(0).toInt),
            ("livro_id" -> parser.parseLine(linha)(1).toInt)
        )).toList
    }
}