package es.santyarbo.myfootball.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import es.santyarbo.myfootball.ui.common.ErrorLayout
import es.santyarbo.myfootball.ui.common.loadSvgUrl
import es.santyarbo.myfootball.ui.common.loadUrl
import kotlinx.android.synthetic.main.error_layout.view.*

@BindingAdapter("url")
fun ImageView.bindUrl(url: String?) {
    if (url != null) loadUrl(url)
}

@BindingAdapter("svgUrl")
fun ImageView.bindSvgUrl(url: String?) {
    if (url != null) loadSvgUrl(url)
}

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = visible?.let {
        if (visible) View.VISIBLE else View.GONE
    } ?: View.GONE
}

@BindingAdapter("onClick")
fun ErrorLayout.setOnActionClick(onClickListener: View.OnClickListener?) {
    btn_retry.setOnClickListener(onClickListener)
}