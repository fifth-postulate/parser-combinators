import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.Test

class LazyTest {

    @Test
    fun `given the parser matches the input, lazy will match the input`() {
        var timesCalled = 0
        val p = character('A')
        val producer = {
            timesCalled++
            p
        }
        val input = "ABC"

        // Create the parser, we expect that the producer is not called until needed.
        val lazyP = lazy(producer)

        assertThat(timesCalled).isEqualTo(0)

        val result = lazyP(input)

        assertThat(timesCalled).isEqualTo(1)
        assertThat(result).isEqualTo(listOf('A' to "BC"))
    }

}