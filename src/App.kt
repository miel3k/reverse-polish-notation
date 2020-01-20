import java.io.File
import java.util.*

fun main() {
    val input = readExpressionFromFile("data/input.txt")
    val reversePolishNotation = Rpn(input)
    reversePolishNotation.convertToPostfix()
    println("RPN: " + reversePolishNotation.output.joinToString(" "))
}

fun readExpressionFromFile(path: String) = File(path)
    .inputStream()
    .bufferedReader()
    .readLine()
    .let { split(it, Rpn.OPERATORS.joinToString("")) }

fun split(expression: String, delimiter: String): List<String> {
    val tokenList = mutableListOf<String>()
    val stringTokenizer = StringTokenizer(expression, delimiter, true)
    while (stringTokenizer.hasMoreTokens()) {
        tokenList.add(stringTokenizer.nextToken())
    }
    return tokenList
}