package tgo1014.listofbeers.repositories

import android.annotation.SuppressLint
import retrofit2.Response
import tgo1014.listofbeers.models.Beer
import tgo1014.listofbeers.network.PunkApi
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeersRepository @Inject constructor(private val punkApi: PunkApi) {

    @SuppressLint("SimpleDateFormat")
    private val monthYearFormat = SimpleDateFormat("MM-yyyy")

    suspend fun getBeers(page: Int, after: Date? = null, before: Date? = null): List<Beer> {
        val afterString = after?.let { monthYearFormat.format(after) }
        val beforeString = before?.let { monthYearFormat.format(before) }
        return punkApi.getBeers(
            page = page,
            brewedAfter = afterString,
            brewedBefore = beforeString,
        ).successOrThrow()
    }

    private fun <T> Response<T>.successOrThrow(): T {
        if (isSuccessful && body() != null) {
            return body()!!
        }
        throw Exception(this.errorBody()?.string())
    }
}