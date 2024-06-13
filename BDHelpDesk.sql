CREATE TABLE tbUsuarios(
UUID_usuario VARCHAR2(50),
correoElectronico VARCHAR2(50),
clave VARCHAR2(50)
)

CREATE TABLE tbTickets (
    UUID_ticket VARCHAR2(50),
    numeroTicket VARCHAR2(20),
    tituloTicket VARCHAR2(100),
    descripcionTicket VARCHAR2(4000),
    nombreAutor VARCHAR2(100),
    emailAutor VARCHAR2(100),
    fechaCreacion VARCHAR2(25),
    ticketEstado VARCHAR2(10),
    fechaFinalizacion VARCHAR2(25)
);

drop table tbTickets

select * from tbTickets

select * from tbUsuarios
delete tbTickets where tituloTicket = 'a'
commit