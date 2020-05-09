package es.santyarbo.myfootball.ui.countries

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.santyarbo.domain.Country
import es.santyarbo.myfootball.R
import es.santyarbo.myfootball.databinding.RowCountryItemBinding
import es.santyarbo.myfootball.ui.common.basicDiffUtil
import es.santyarbo.myfootball.ui.common.bindingInflate

class CountriesAdapter(private val listener: (Country) -> Unit) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    var countries: List<Country> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.row_country_item, false))

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countries[position]
        holder.binding.country = country
        holder.itemView.setOnClickListener { listener(country) }
    }

    class ViewHolder(val binding: RowCountryItemBinding) : RecyclerView.ViewHolder(binding.root)
}