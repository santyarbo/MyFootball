package es.santyarbo.data.repository

import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.data.repository.PermissionChecker.Permission.COARSE_LOCATION
import es.santyarbo.data.source.LocationDatasource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDatasource: LocationDatasource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(locationDatasource, permissionChecker)
    }

    @Test
    fun `return region from location data source when permission granted`() {
        runBlocking {
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
            whenever(locationDatasource.findLastRegion()).thenReturn("ES")

            val region = regionRepository.findLastRegion()

            Assert.assertEquals("ES", region)
        }
    }

    @Test
    fun `return region when coarse permission not granted`() {
        runBlocking {
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)

            val region = regionRepository.findLastRegion()

            Assert.assertEquals(RegionRepository.DEFAULT_REGION, region)
        }
    }
}