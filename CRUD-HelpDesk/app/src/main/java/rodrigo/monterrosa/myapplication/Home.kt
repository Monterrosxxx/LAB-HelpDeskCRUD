package rodrigo.monterrosa.myapplication

import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.claseConexion
import modelo.tbTickets
import java.util.UUID

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNumeroTicket = findViewById<EditText>(R.id.txtNumTicket)
        val txtTituloTicket = findViewById<EditText>(R.id.txtTituloTicket)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripTicket)
        val txtAutor = findViewById<EditText>(R.id.txtAutorTicket)
        val txtEmail = findViewById<EditText>(R.id.txtEmailAutor)
        val txtFechaCreacion = findViewById<EditText>(R.id.txtFechaCreacion)
        val txtEstado = findViewById<EditText>(R.id.txtEstadoTicket)
        val txtFechaFinalizacion = findViewById<EditText>(R.id.txtFechaFinalizacion)
        val btnAgregar = findViewById<Button>(R.id.btnAregar)
        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        rcvTickets.layoutManager = LinearLayoutManager(this)

        //TODO:Intento de reparar la app

        fun obtenerTickets(): List<tbTickets>{

            val objConexion = claseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            if (statement != null) {
                val resulSet = statement.executeQuery("SELECT * FROM tbTickets")

                val listaTicekts = mutableListOf<tbTickets>()

                while (resulSet.next()){

                    val uuid = resulSet.getString("UUID_ticket")
                    val numeroTicket = resulSet.getString("numeroTicket")
                    val tituloTicket = resulSet.getString("tituloTicket")
                    val descripcionTicket = resulSet.getString("descripcionTicket")
                    val nombreAutor = resulSet.getString("nombreAutor")
                    val emailAutor = resulSet.getString("emailAutor")
                    val fechaCreacion = resulSet.getString("fechaCreacion")
                    val ticketEstado = resulSet.getString("ticketEstado")
                    val fechaFinalizacion = resulSet.getString("fechaFinalizacion")

                    val valoresJuntos = tbTickets(uuid, numeroTicket, tituloTicket, descripcionTicket, nombreAutor, emailAutor, fechaCreacion, ticketEstado, fechaFinalizacion)

                    listaTicekts.add(valoresJuntos)
                }

                return listaTicekts
            } else {
                // Manejar el caso en que statement es null...
                return emptyList()
            }
        }

        //TODO:Intento de reparar la app(fin)


        CoroutineScope(Dispatchers.IO).launch {

            val ticketsDB = obtenerTickets()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketsDB)
                rcvTickets.adapter = adapter
            }

        }

        btnAgregar.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {

                val objConexion = claseConexion().cadenaConexion()

                val aggTicket = objConexion?.prepareStatement("INSERT INTO tbTickets (UUID_ticket, numeroTicket, tituloTicket, descripcionTicket, nombreAutor, emailAutor, fechaCreacion, ticketEstado, fechaFinalizacion) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")!!

                aggTicket.setString(1, UUID.randomUUID().toString())
                aggTicket.setString(2, txtNumeroTicket.text.toString())
                aggTicket.setString(3, txtTituloTicket.text.toString())
                aggTicket.setString(4, txtDescripcion.text.toString())
                aggTicket.setString(5, txtAutor.text.toString())
                aggTicket.setString(6, txtEmail.text.toString())
                aggTicket.setString(7, txtFechaCreacion.text.toString())
                aggTicket.setString(8, txtEstado.text.toString())
                aggTicket.setString(9, txtFechaFinalizacion.text.toString())
                aggTicket.executeUpdate()


            }

        }

    }
}