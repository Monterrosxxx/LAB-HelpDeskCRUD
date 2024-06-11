package rodrigo.monterrosa.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.claseConexion

class activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCorreo = findViewById<EditText>(R.id.txtCorreoLogin)
        val txtClave = findViewById<EditText>(R.id.txtClaveLogin)
        val btnAcceder = findViewById<Button>(R.id.btnAcceder)
        val btnStartRegistrarse = findViewById<Button>(R.id.btnRegistrarme)

        btnAcceder.setOnClickListener {

            val pantallaHome = Intent(this, Home::class.java)

            GlobalScope.launch(Dispatchers.IO){

                val objConexion = claseConexion().cadenaConexion()

                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE correoElectronico = ? AND clave = ?")!!
                comprobarUsuario.setString(1, txtCorreo.text.toString())
                comprobarUsuario.setString(2, txtClave.text.toString())

                val resultadoComprobacion = comprobarUsuario.executeQuery()
                if (resultadoComprobacion.next()){
                    startActivity(pantallaHome)
                }else{
                    println("Usuario incorrecto, intentelo de nuevo porfavor")
                }

            }

        }

        btnStartRegistrarse.setOnClickListener {
            val pantallaRegistrarme = Intent(this, activity_registrarse::class.java)
            startActivity(pantallaRegistrarme)
        }
    }
}