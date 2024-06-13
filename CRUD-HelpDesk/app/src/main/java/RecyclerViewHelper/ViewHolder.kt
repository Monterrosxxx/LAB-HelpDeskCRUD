package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rodrigo.monterrosa.myapplication.R

class ViewHolder (View: View): RecyclerView.ViewHolder(View){

    val textView: TextView = View.findViewById(R.id.txtNombreCard)
    val imgEliminar: ImageView = View.findViewById(R.id.imgEliminar)
    val imgEditar: ImageView = View.findViewById(R.id.imgEditar)

}


