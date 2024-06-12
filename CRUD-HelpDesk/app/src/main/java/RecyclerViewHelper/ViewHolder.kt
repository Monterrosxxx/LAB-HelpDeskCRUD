package RecyclerViewHelper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rodrigo.monterrosa.myapplication.R

class ViewHolder (View: View): RecyclerView.ViewHolder(View) {

    val txtNombreCard = View.findViewById<TextView>(R.id.txtNombreCard)
}