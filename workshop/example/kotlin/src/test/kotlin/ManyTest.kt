import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class ManyTest {

    @Test
    fun `given the parser matches multiple characters from the input, many will match the input`() {
        val p = character('A')
        val input = "AAB"

        val result = many(p)(input)

        assertThat(result).isEqualTo(
            listOf(
                listOf('A', 'A') to "B",
                listOf('A') to "AB",
                emptyList<Char>() to "AAB",
            )
        )
    }

    @Test
    fun `given the parser matches zero characters from empty input, many will match the input`() {
        val p = character('A')
        val input = ""

        val result = many(p)(input)

        assertThat(result).isEqualTo(
            listOf(
                emptyList<Char>() to "",
            )
        )
    }

    @Test
    fun `given the parser matches zero characters from the input, many will match the input`() {
        val p = character('A')
        val input = "aBC"

        val result = many(p)(input)

        assertThat(result).isEqualTo(
            listOf(
                emptyList<Char>() to "aBC",
            )
        )
    }

    // TODO: Test many1
}
