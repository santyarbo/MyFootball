package es.santyarbo.myfootball.ui.leagues

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.santyarbo.domain.League
import es.santyarbo.myfootball.R
import es.santyarbo.myfootball.databinding.RowLeagueItemBinding
import es.santyarbo.myfootball.ui.common.basicDiffUtil
import es.santyarbo.myfootball.ui.common.bindingInflate

class LeaguesAdapter(private val listener: (League) -> Unit) :
    RecyclerView.Adapter<LeaguesAdapter.ViewHolder>() {

    var leagues: List<League> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.leagueId == new.leagueId }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.row_league_item, false))

    override fun getItemCount(): Int = leagues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = leagues[position]
        holder.binding.league = league
        holder.itemView.setOnClickListener { listener(league) }
    }

    class ViewHolder(val binding: RowLeagueItemBinding) : RecyclerView.ViewHolder(binding.root)
}