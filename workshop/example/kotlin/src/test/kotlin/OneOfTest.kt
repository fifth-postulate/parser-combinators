import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class OneOfTest {

    @Test
    fun `given the input matches the left parser, oneOf will match the input`() {
        val left = character('A')
        val right = character('B')
        val input = "ABC"

        val result = oneOf(left, right)(input)

        assertThat(result).isEqualTo(listOf('A' to "BC"))
    }

    @Test
    fun `given the input matches the right parser, oneOf will match the input`() {
        val left = character('A')
        val right = character('B')
        val input = "BAC"

        val result = oneOf(left, right)(input)

        assertThat(result).isEqualTo(listOf('B' to "AC"))
    }

    @Test
    fun `given the input matches none of the parsers, oneOf will not match the input`() {
        val left = character('A')
        val right = character('B')
        val input = "aBC"

        val result = oneOf(left, right)(input)

        assertThat(result).isEqualTo(emptyList())
    }
}