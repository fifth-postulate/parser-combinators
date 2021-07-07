import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class ParseATest {

    @Test
    fun `given the input starts with an A, parseA should return an A with the remainder of the input`() {
        val input = "ABC"
        val result = parseA(input)
        assertThat(result).isEqualTo(listOf('A' to "BC"))
    }

    @Test
    fun `given the input does not start with an A, parseA should return an empty list`() {
        val input = "BC"
        val result = parseA(input)
        assertThat(result).isEqualTo(emptyList())
    }

    @Test
    fun `given the input does not start with a capital A, parseA should return an empty list`() {
        val input = "aBC"
        val result = parseA(input)
        assertThat(result).isEqualTo(emptyList())
    }

}