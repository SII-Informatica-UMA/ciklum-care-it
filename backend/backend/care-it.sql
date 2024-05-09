create sequence sesion_seq start with 1 increment by 50;
create table sesion (presencial boolean, calorias bigint, id bigint not null, id_plan bigint, peso bigint, pulsaciones bigint, fin varchar(50), inicio varchar(50), imagen varchar(2048), video varchar(2048), descripcion varchar(4000), trabajo_realizado varchar(4000), primary key (id));
