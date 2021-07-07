import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class SucceedFailTest {

    @Test
    fun `Succeed should return parser that returns a singleton list with the value and not take any input`() {
        val value = "Some val"
        val input = "Some input"

        val result = succeed(value)(input)

        assertThat(result).isEqualTo(listOf(value to input))
    }

    @Test
    fun `Fail should return parser that returns an empty list`() {
        val input = "Some input"

        val result = fail<Any>()(input)

        assertThat(result).isEqualTo(emptyList())
    }
}