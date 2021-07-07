import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class TokenTest {
    @Test
    fun `given the input starts with the token, token() should return the token with the remainder of the input`() {
        val token = "if"
        val input = "if a == 1 then 1 else 2"

        val result = token(token)(input)

        assertThat(result).isEqualTo(listOf("if" to " a == 1 then 1 else 2"))
    }

    @Test
    fun `given the input does not start with the token, token() should return an empty list`() {
        val token = "if"
        val input = "while a == 1 do nothing"

        val result = token(token)(input)

        assertThat(result).isEqualTo(emptyList())
    }
}