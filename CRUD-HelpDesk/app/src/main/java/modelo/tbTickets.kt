package modelo

data class tbTickets(
    val uuid: String,
    val numeroTicket: String,
    val tituloTicket: String,
    val descripcionTicket: String,
    val nombreAutor: String,
    val emailAutor: String,
    val fechaCreacion: String,
    val ticketEstado: String,
    val fechaFinalizacion: String
)
