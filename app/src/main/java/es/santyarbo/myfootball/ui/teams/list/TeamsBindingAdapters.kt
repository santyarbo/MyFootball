package es.santyarbo.myfootball.ui.teams.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.santyarbo.domain.Team

@BindingAdapter("items")
fun RecyclerView.setItems(teams: List<Team>?) {
    (adapter as? TeamsAdapter)?.let {
        it.teams = teams ?: emptyList()
    }
}