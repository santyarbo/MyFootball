package es.santyarbo.myfootball.ui.teams.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.santyarbo.domain.Team
import es.santyarbo.myfootball.R
import es.santyarbo.myfootball.databinding.RowTeamItemBinding
import es.santyarbo.myfootball.ui.common.basicDiffUtil
import es.santyarbo.myfootball.ui.common.bindingInflate

class TeamsAdapter(private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    var teams: List<Team> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.teamId == new.teamId }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            parent.bindingInflate(
                R.layout.row_team_item,
                false
            )
        )


    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = teams[position]
        holder.binding.team = team
        holder.itemView.setOnClickListener { listener(team) }
    }

    class ViewHolder(val binding: RowTeamItemBinding) : RecyclerView.ViewHolder(binding.root)
}