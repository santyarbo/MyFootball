package es.santyarbo.myfootball.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import es.santyarbo.myfootball.R
import es.santyarbo.myfootball.data.server.common.FootballApi
import es.santyarbo.myfootball.utils.fromJson
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get


class LeaguesUiTest : KoinTest{

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson("leagues.json")
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<FootballApi>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

   @Test
    fun checkToolbarTitle() {
        activityTestRule.launchActivity(null)

        Espresso.onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(hasDescendant(withText("España"))))
    }

    @Test
    fun clickLeagueNavigatesToTeams() {
        activityTestRule.launchActivity(null)

        Espresso.onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )

        Espresso.onView(withId(R.id.toolbar))
            .check(ViewAssertions.matches(hasDescendant(withText("Selecciona el equipo"))))
    }
}