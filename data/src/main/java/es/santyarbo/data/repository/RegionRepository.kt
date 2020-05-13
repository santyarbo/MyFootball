package es.santyarbo.data.repository

import es.santyarbo.data.source.LocationDatasource

class RegionRepository(
    private val locationDataSource: LocationDatasource,
    private val permissionChecker: PermissionChecker
) {
    companion object {
         const val DEFAULT_REGION = "US"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}


interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}