import java.util.*

class Rpn(
    private val input: List<String>,
    val output: Stack<String> = Stack(),
    private val operators: Stack<String> = Stack()
) {

    fun convertToPostfix() {
        input.forEach { token ->
            when {
                token == "(" -> operators.push(token)
                token == ")" -> pushBracketOperators()
                isOperator(token) && operators.isEmpty() -> operators.push(token)
                isOperator(token) -> evaluateOperator(token)
                else -> output.push(token)
            }
            println("Processed token: $token")
            val outputText = output.joinToString(" ")
            val stackText = operators.joinToString(" ")
            println("Current stack: $stackText")
            println("Current output $outputText")
            println()
        }
        while (!operators.isEmpty()) {
            output.push(operators.pop())
        }
    }

    private fun evaluateOperator(token: String) {
        val operatorPriority = getOperatorPriority(token)
        val stackValue = operators.peek()
        val stackValuePriority = getOperatorPriority(stackValue)
        if (operatorPriority > stackValuePriority) {
            operators.push(token)
        } else {
            pushOperatorsWithHigherPriority(operatorPriority)
            operators.push(token)
        }
    }

    private fun pushOperatorsWithHigherPriority(operatorPriority: Int) {
        var isPriorityHigher = true
        while (isPriorityHigher) {
            if (operators.isEmpty()) break
            val nextStackValue = operators.peek()
            val nextStackValuePriority =
                getOperatorPriority(nextStackValue)
            if (nextStackValuePriority >= operatorPriority) {
                output.push(operators.pop())
            } else {
                isPriorityHigher = false
            }
        }
    }

    private fun pushBracketOperators() {
        var isOpeningBracket = false
        while (!isOpeningBracket) {
            val stackValue = operators.pop()
            when {
                stackValue == "(" -> isOpeningBracket = true
                isOperator(stackValue) -> output.push(stackValue)
            }
        }
    }

    private fun isOperator(token: String) = OPERATORS.any { it == token }

    private fun getOperatorPriority(operator: String) = when (operator) {
        "+", "-" -> 1
        "*", "/" -> 2
        "^" -> 3
        else -> 0
    }

    companion object {
        val OPERATORS = listOf("+", "*", "/", "-", "(", ")", "^")
    }
}