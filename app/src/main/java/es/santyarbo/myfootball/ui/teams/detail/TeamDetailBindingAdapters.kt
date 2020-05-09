package es.santyarbo.myfootball.ui.teams.detail

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.santyarbo.myfootball.R

@BindingAdapter("favorite")
fun FloatingActionButton.setFavorite(favorite: Boolean?) {
    val icon = if (favorite == true) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
    setImageDrawable(context.getDrawable(icon))
}