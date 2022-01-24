package es.santyarbo.myfootball.data.server

object ApiKeyRetriever {
    init {
        System.loadLibrary("api_keys")
    }

    external fun getFooApiKey() : String
}