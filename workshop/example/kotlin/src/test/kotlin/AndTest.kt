import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class AndTest {

    @Test
    fun `given the input matches the left parser and then the right parser, and will match the input`() {
        val left = character('A')
        val right = character('B')
        val input = "ABCD"

        val result = and(left, right)(input)

        assertThat(result).isEqualTo(listOf('A' to 'B' to "CD"))
    }

    @Test
    fun `given the input does not match the left parser, and will not match the input`() {
        val left = character('A')
        val right = character('B')
        val input = "BAC"

        val result = and(left, right)(input)

        assertThat(result).isEqualTo(emptyList())
    }

}
