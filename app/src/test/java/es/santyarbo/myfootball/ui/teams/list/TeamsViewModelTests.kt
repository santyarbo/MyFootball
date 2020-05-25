package es.santyarbo.myfootball.ui.teams.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.domain.ResultWrapper
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.ui.common.ErrorLayout
import es.santyarbo.testshared.mockedTeam
import es.santyarbo.usescases.GetTeamsByLeague
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TeamsViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getTeamsByLeague: GetTeamsByLeague

    @Mock
    lateinit var observerTeams: Observer<List<Team>>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerError: Observer<ErrorLayout.ErrorType>

    private lateinit var vm: TeamsViewModel

    @Before
    fun setUp() {
        vm = TeamsViewModel(1, getTeamsByLeague, Dispatchers.Unconfined)
        vm.teams.observeForever(observerTeams)
        vm.loading.observeForever(observerLoading)
        vm.error.observeForever(observerError)
    }

    @Test
    fun testNull() {
        runBlocking {
            whenever(getTeamsByLeague.invoke(1)).thenReturn(null)
            Assert.assertNotNull(vm.getTeams())
        }
    }

    @Test
    fun `error unknown when call getTeamsByLeague`() {
        runBlocking {
            whenever(getTeamsByLeague.invoke(1)).thenReturn(ResultWrapper.GenericError())

            vm.getTeams()

            verify(observerLoading).onChanged(true)
            verify(observerError).onChanged(ErrorLayout.ErrorType.UNKNOWN)
            verify(observerLoading).onChanged(false)
        }
    }

    @Test
    fun `error network when call getTeamsByLeague`() {
        runBlocking {
            whenever(getTeamsByLeague.invoke(1)).thenReturn(ResultWrapper.NetworkError)

            vm.getTeams()

            verify(observerLoading).onChanged(true)
            verify(observerError).onChanged(ErrorLayout.ErrorType.NETWORK)
            verify(observerLoading).onChanged(false)
        }
    }

    @Test
    fun `success call getTeamsByLeague`() {
        runBlocking {
            val teams = listOf(mockedTeam.copy(1))
            whenever(getTeamsByLeague.invoke(1)).thenReturn(ResultWrapper.Success(teams))

            vm.getTeams()

            verify(observerLoading).onChanged(true)
            verify(observerTeams).onChanged(teams)
            verify(observerLoading).onChanged(false)
        }
    }
}