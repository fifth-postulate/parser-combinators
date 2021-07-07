import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class MapTest {

    @Test
    fun `given the parser matches the input, the result will be transformed`() {
        val p = satisfy(Char::isDigit)
        val input = "123"

        val intParser = map(p) { value -> value - '0' }
        val result = intParser(input)

        assertThat(result).isEqualTo(listOf(1 to "23"))
    }

    @Test
    fun `given the parser does not match the input, map will not match the input`() {
        val p = satisfy(Char::isDigit)
        val input = "ABC"

        val intParser = map(p) { value -> value - '0' }
        val result = intParser(input)

        assertThat(result).isEqualTo(emptyList())
    }

}