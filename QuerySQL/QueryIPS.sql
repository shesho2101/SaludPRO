create database ips;
use ips;

-- Tablas
create table usuario(
ID varchar(50) primary key not null,
nombre varchar(50) not null,
apellidos varchar(50),
cargo varchar(50) not null
);


create table medico(
ID_Medico varchar(50) not null,
Especializacion varchar(50),
NumHab int not null
);

create table consultorio(
NumHab int auto_increment primary key not null,
Nombre varchar(40) not null,
CodSede int not null
);

create table sede(
Codigo int auto_increment primary key not null,
nombre varchar(50) not null,
direccion varchar(50)
);

create table paciente(
ID_Paciente varchar(50) not null,
fecha_nacimiento date not null,
edad int not null default(year(current_date()) - year(fecha_nacimiento)),
sexo char(10),
grupoSanguineo enum('A_POSITIVO', 'O_POSITIVO', 'B_POSITIVO', 'AB_POSITIVO', 'A_NEGATIVO', 'O_NEGATIVO', 'B_NEGATIVO', 'AB_NEGATIVO') not null,
telefono varchar(100),
correoElectronico varchar(100) not null
);

create table cita(
NumCita int auto_increment key not null,
NumHab int not null,
ID_Medico varchar(50) not null,
fecha datetime not null,
ID_Paciente varchar(50) not null,
asistencia boolean default(true)
);

create table tratamiento_medico(
codigo_tratamiento int not null,
ID_Medico varchar(50) not null
);

create table tratamiento(
codigo int auto_increment primary key not null,
descripcion text not null,
fecha_inicio date not null default(now()),
fecha_fin date not null ,
ID_Paciente varchar(50) not null,
observaciones text 
);

create table tratamiento_medicamento(
cod_trat int not null,
cod_medicamento int not null
);

create table medicamento(
codigo int auto_increment primary key not null,
nombre varchar(50) not null,
cantidad int not null
);

create table administrador(
ID_Admin varchar(50) not null
);

create table PA(
ID_PA varchar(50) null,
area_atencion varchar(50) not null
);

create table historial_clinico(
historial mediumtext not null,
ID_Paciente varchar(50) not null
);

-- Foreign keys
alter table medico add constraint FK_Medusr foreign key (ID_Medico) references usuario(ID);
alter table paciente add constraint FK_Pacusr foreign key (ID_Paciente) references usuario(ID);
alter table administrador add constraint FK_Adminusr foreign key (ID_Admin) references usuario(ID);
alter table PA add constraint FK_PAusr foreign key (ID_PA) references usuario(ID);

alter table medico add constraint FK_ConsultMed foreign key (NumHab) references consultorio(NumHab);
alter table cita add constraint FK_ConsultCita foreign key (NumHab) references consultorio(NumHab);

alter table consultorio add constraint FK_SedeConsult foreign key (CodSede) references sede(Codigo);

alter table cita add constraint FK_MedicoCita foreign key (ID_Medico) references medico(ID_Medico);
alter table tratamiento_medico add constraint FK_MedicoTrat foreign key (ID_Medico) references medico(ID_Medico);

alter table cita add constraint FK_PacCita foreign key (ID_Paciente) references paciente(ID_Paciente);
alter table tratamiento add constraint FK_PacTrat foreign key (ID_Paciente) references paciente(ID_Paciente);

alter table tratamiento_medico add constraint FK_Tratamiento_Medico foreign key (codigo_tratamiento) references tratamiento(codigo);
alter table tratamiento_medicamento add constraint FK_Tratamiento_Medicamento foreign key (cod_trat) references tratamiento(codigo);

alter table tratamiento_medicamento add constraint FK_MedicamentoTrat foreign key (cod_medicamento) references medicamento(codigo);

alter table historial_clinico add constraint FK_HistorialPac foreign key (ID_Paciente) references paciente(ID_Paciente);
