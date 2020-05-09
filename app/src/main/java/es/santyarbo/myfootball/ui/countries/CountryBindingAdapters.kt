package es.santyarbo.myfootball.ui.countries

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import es.santyarbo.domain.Country

@BindingAdapter("items")
fun RecyclerView.setItems(countries: List<Country>?) {
    (adapter as? CountriesAdapter)?.let {
        it.countries = countries ?: emptyList()
    }
}