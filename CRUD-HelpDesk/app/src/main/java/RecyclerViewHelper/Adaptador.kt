package RecyclerViewHelper

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.claseConexion
import modelo.tbTickets
import rodrigo.monterrosa.myapplication.R

class Adaptador (private var Datos:List<tbTickets>): RecyclerView.Adapter<ViewHolder>() {

    fun actualizarLista(nuevaLista:List<tbTickets>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun actualizarListaDespuesdeActualizarlosDatos(uuid: String, nuevoNombre: String){

        val index = Datos.indexOfFirst { it.uuid == uuid }
        Datos[index].tituloTicket == nuevoNombre
        notifyItemChanged(index)
    }

    fun eliminarRegistro(nombreTicket: String, position: Int){

        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(position)

        GlobalScope.launch(Dispatchers.IO){

            val objConexion = claseConexion().cadenaConexion()

            val deleteProducto = objConexion?.prepareStatement("DELETE tbTickets WHERE tituloTicket = ?")!!
            deleteProducto.setString(1, nombreTicket)
            deleteProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

        }

        Datos = listaDatos.toList()
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun actualizarTicket(nombreTicket: String,uuid: String){

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = claseConexion().cadenaConexion()

            val actualizarProducto = objConexion?.prepareStatement("UPDATE tbTickets SET tituloTicket = ? WHERE UUID_ticket = ?")!!
            actualizarProducto.setString(1, nombreTicket)
            actualizarProducto.setString(2, uuid)
            actualizarProducto.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                actualizarListaDespuesdeActualizarlosDatos(uuid, nombreTicket)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ticket = Datos[position]
        holder.textView.text = ticket.tituloTicket

        val item = Datos[position]
        holder.imgEliminar.setOnClickListener {

            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¿Eliminar?")
            builder.setMessage("¿Seguro de quererlo eliminar?")

            builder.setPositiveButton("si"){ dialog, wich->
                eliminarRegistro(item.tituloTicket, position)
            }
            builder.setNegativeButton("No"){ dialog, wich ->

            }

            val alertDialog = builder.create()
            alertDialog.show()

        }

        holder.imgEditar.setOnClickListener {

            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Editar Ticket")

            val cuadtritoNuevoTitulo = EditText(context)

            cuadtritoNuevoTitulo.setHint(item.tituloTicket)
            builder.setView(cuadtritoNuevoTitulo)

            builder.setPositiveButton("Actualizar"){ dialog, wich ->
                actualizarTicket(cuadtritoNuevoTitulo.text.toString(), item.uuid)
            }
            builder.setNegativeButton("Cancelar"){ dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

    }


}