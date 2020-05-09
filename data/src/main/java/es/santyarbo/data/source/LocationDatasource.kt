package es.santyarbo.data.source

interface LocationDatasource {
    suspend fun findLastRegion(): String?
}