package tgo1014.listofbeers.repositories

import tgo1014.listofbeers.models.Beer

interface BeersRepository {
    suspend fun getBeers(page: Int, search: String? = null, yeast: String? = null): List<Beer>
}