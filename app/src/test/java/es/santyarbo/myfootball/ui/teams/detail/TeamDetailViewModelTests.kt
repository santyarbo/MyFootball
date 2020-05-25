package es.santyarbo.myfootball.ui.teams.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.domain.Team
import es.santyarbo.testshared.mockedTeam
import es.santyarbo.usescases.FindTeamById
import es.santyarbo.usescases.ToggleTeamFavorite
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
class TeamDetailViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findTeamById: FindTeamById

    @Mock
    lateinit var toggleTeamFavorite: ToggleTeamFavorite

    @Mock
    lateinit var observerTeam: Observer<Team>

    private lateinit var vm: TeamDetailViewModel

    @Before
    fun setUp() {
        vm = TeamDetailViewModel(1, findTeamById, toggleTeamFavorite, Dispatchers.Unconfined)
        vm.team.observeForever(observerTeam)
    }

    @Test
    fun testNull() {
        runBlocking {
            whenever(findTeamById.invoke(1)).thenReturn(null)
            Assert.assertNotNull(vm.findTeam())
        }
    }

    @Test
    fun `observing LiveData finds the team`() {
        runBlocking {
            val team = mockedTeam.copy(1)
            whenever(findTeamById.invoke(1)).thenReturn(team)

            vm.findTeam()

            verify(observerTeam).onChanged(team)
        }
    }

    @Test
    fun `when favorite clicked, the toggleTeamFavorite use case is invoked`() {
        runBlocking {
            val team = mockedTeam.copy(1)

            whenever(findTeamById.invoke(1)).thenReturn(team)
            vm.findTeam()

            whenever(toggleTeamFavorite.invoke(team)).thenReturn(team.copy(favorite = !team.favorite))
            vm.onFavoriteClicked()

            verify(toggleTeamFavorite).invoke(team)
        }
    }

}