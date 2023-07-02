import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApiService {
    @GET("{cep}/json/")
    suspend fun consultarCep(@Path("cep") cep: String): Response<Endereco>
}
