
import com.innogames.testmemorycard.network.GeoDataResponse
import retrofit2.http.*

interface RequestAPI {

    @GET("json")
    suspend fun getFactAboutNumber(): GeoDataResponse
}