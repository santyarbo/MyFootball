package es.santyarbo.myfootball.ui.leagues

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.santyarbo.domain.League

@BindingAdapter("items")
fun RecyclerView.setItems(leagues: List<League>?) {
    (adapter as? LeaguesAdapter)?.let {
        it.leagues = leagues ?: emptyList()
    }
}