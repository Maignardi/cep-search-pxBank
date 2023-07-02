import retrofit2.Response

class ViaCepRepository {
    private val apiService: ViaCepApiService = RetrofitClient.apiService

    suspend fun consultarCep(cep: String): Result<Endereco> {
        return try {
            val response: Response<Endereco> = apiService.consultarCep(cep)
            if (response.isSuccessful) {
                val endereco = response.body()
                if (endereco != null) {
                    Result.success(endereco)
                } else {
                    Result.failure(Exception("Falha ao consultar o CEP"))
                }
            } else {
                Result.failure(Exception("Falha ao consultar o CEP"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
