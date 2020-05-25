package es.santyarbo.myfootball.ui.favs

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import es.santyarbo.domain.Team
import es.santyarbo.testshared.mockedTeam
import es.santyarbo.usescases.GetTeamsFavorites
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
class FavoritesViewModelTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getFavorites : GetTeamsFavorites

    @Mock
    lateinit var observerTeams: Observer<List<Team>>

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    private lateinit var vm: FavoritesViewModel

    @Before
    fun setUp() {
        vm = FavoritesViewModel(getFavorites, Dispatchers.Unconfined)
        vm.teams.observeForever(observerTeams)
        vm.loading.observeForever(observerLoading)
    }

    @Test
    fun testNull() {
        runBlocking {
            whenever(getFavorites.invoke()).thenReturn(null)
            Assert.assertNotNull(vm.getTeamsFavorites())
        }
    }

    @Test
    fun `success call get teams favorites`() {
        runBlocking {
            val teams = listOf(mockedTeam.copy(1, favorite = true), mockedTeam.copy(23, favorite = true))
            whenever(getFavorites.invoke()).thenReturn(teams)

            vm.getTeamsFavorites()

            verify(observerLoading).onChanged(true)
            verify(observerTeams).onChanged(teams)
            verify(observerLoading).onChanged(false)
        }
    }

    @Test
    fun `call empty favorites`() {
        runBlocking {
            val teams = emptyList<Team>()
            whenever(getFavorites.invoke()).thenReturn(teams)

            vm.getTeamsFavorites()

            verify(observerLoading).onChanged(true)
            verify(observerTeams).onChanged(teams)
            verify(observerLoading).onChanged(false)
        }
    }
}