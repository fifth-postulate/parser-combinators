import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class JustTest {

    @Test
    fun `given the parser matches the entire input, just will match the input`() {
        val p = character('A')
        val input = "A"

        val result = just(p)(input)

        assertThat(result).isEqualTo(listOf('A' to ""))
    }

    @Test
    fun `given the parser does not match the entire input, just will not match the input`() {
        val p = character('A')
        val input = "AB"

        val result = just(p)(input)

        assertThat(result).isEqualTo(emptyList())
    }
}
