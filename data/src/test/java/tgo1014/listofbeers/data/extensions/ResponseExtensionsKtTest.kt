package tgo1014.listofbeers.data.extensions

import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response

class ResponseExtensionsKtTest {

    @Test
    fun `GIVEN a success response WHEN body is not null THEN success`() {
        val response = Response.success("")
        val data = response.successOrThrow()
        assert(data == "")
    }

    @Test(expected = Exception::class)
    fun `GIVEN a success response WHEN body is null THEN throws`() {
        val response = Response.success(null)
        response.successOrThrow()
    }

    @Test(expected = Exception::class)
    fun `GIVEN an response WHEN is error THEN throws`() {
        val errorBodyString = "FooBar"
        val body = ResponseBody.create(null, errorBodyString)
        val response = Response.error<String>(404, body)
        assert(response.errorBody()?.string() == errorBodyString)
        response.successOrThrow()
    }


}