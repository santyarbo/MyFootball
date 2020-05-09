package es.santyarbo.myfootball.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import es.santyarbo.myfootball.R
import kotlinx.android.synthetic.main.error_layout.view.*

class ErrorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    enum class ErrorType {
        UNKNOWN, NETWORK, NOT_FOUND, NOT_RESULTS
    }

   init {
       inflate(context, R.layout.error_layout, this)
       iv_icon.setImageResource(R.drawable.ic_undraw_error_generic)
       tv_title.text = context.getString(R.string.error_unknown_title)
       tv_description.text = context.getString(R.string.error_unknown_message);

       if (attrs != null) {
           val a = getContext().theme.obtainStyledAttributes(
               attrs,
               R.styleable.ErrorLayout,
               0, 0
           )

           if (a.hasValue(R.styleable.ErrorLayout_type)) {
               val errorType = ErrorType.values()[a.getInt(R.styleable.ErrorLayout_type, 0)]
               setErrorType(errorType)
           }
       }
   }

    private fun setErrorType(errorType: ErrorType) {
        tv_description.visibility = View.VISIBLE
        btn_retry.visibility = View.VISIBLE
        when(errorType) {
            ErrorType.NETWORK -> {
                iv_icon.setImageResource(R.drawable.ic_undraw_error_connection)
                tv_title.text = context.getString(R.string.error_connection_title)
                tv_description.text = context.getString(R.string.error_connection_message)
            }
            ErrorType.NOT_FOUND -> {
                iv_icon.setImageResource(R.drawable.ic_undraw_page_not_found)
                tv_title.text = context.getString(R.string.error_not_found_title)
                tv_description.text = context.getString(R.string.error_not_found_message)
            }

            ErrorType.NOT_RESULTS -> {
                iv_icon.setImageResource(R.drawable.ic_undraw_not_results)
                tv_title.text = context.getString(R.string.error_no_results_title)
                tv_description.visibility = View.GONE
                btn_retry.visibility = View.GONE
            }
            else -> {
                iv_icon.setImageResource(R.drawable.ic_undraw_error_generic)
                tv_title.text = context.getString(R.string.error_unknown_title)
                tv_description.text = context.getString(R.string.error_unknown_message);
            }
        }
    }

    fun setOnActionClickListener(onClickListener: OnClickListener?) {
        btn_retry.setOnClickListener(onClickListener)
    }
}