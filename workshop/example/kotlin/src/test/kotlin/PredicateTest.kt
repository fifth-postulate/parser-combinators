import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class PredicateTest {

    @Test
    fun `given the predicate matches the first character of the input, satisfy returns the first character with the remainder of the input`() {
        val predicate: (Char) -> Boolean = { c -> c == 'A' }
        val input = "ABC"

        val result = satisfy(predicate)(input)

        assertThat(result).isEqualTo(listOf('A' to "BC"))
    }

    @Test
    fun `given the predicate does not match the first character of the input, satisfy returns an empty list`() {
        val predicate: (Char) -> Boolean = { c -> c == '!' }
        val input = "ABC"

        val result = satisfy(predicate)(input)

        assertThat(result).isEqualTo(emptyList())
    }
}
