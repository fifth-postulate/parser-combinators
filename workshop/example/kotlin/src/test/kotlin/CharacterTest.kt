import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class CharacterTest {

    @Test
    fun `given the input if the input starts with an A, character 'A' should return an A with the remainder`() {
        val input = "ABC"
        val result = characterA(input)
        assertThat(result).isEqualTo(listOf('A' to "BC"))
    }

    @Test
    fun `given the input does not start with an A, character 'A' should return an empty list`() {
        val input = "BC"
        val result = characterA(input)
        assertThat(result).isEqualTo(emptyList())
    }

    @Test
    fun `given the input does not start with a capital A, character 'A' should return an empty list`() {
        val input = "aBC"
        val result = characterA(input)
        assertThat(result).isEqualTo(emptyList())
    }

    @Test
    fun `given the input does not start with a capital A, character 'a' should return an empty list`() {
        val input = "aBC"
        val result = character('a')(input)
        assertThat(result).isEqualTo(listOf('a' to "BC"))
    }

}