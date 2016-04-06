/*Insercion de jugadores*/
INSERT INTO futbol5.tl_jugador 
(Nombre, Apodo, FechaNacimiento, Handicap)
VALUES
('Eric Lif', 'Eric', date('1994-03-29'), 7),
('Nicolas Orchow', 'Nico', '1994-03-22', 10),
('Julian Fuks', 'Chavo', '1994-11-24', 5),
('Axel Suvalski', 'Suva', '1994-10-21', 3),
('Joel Melamed', 'Joe', '1994-06-14', 7),
('Diego Nicolaievsky', 'Nicola', '1994-05-19', 9),
('Facundo Schusterman', 'Facu', '1996-04-09', 10);


/*Insercion de 2 partidos*/
INSERT INTO futbol5.tl_partido
(FechaHora, Lugar, Confirmado)
VALUES
(curdate(), 'Plaza Devoto', true),
('2014-10-25', 'Club Pedro Lozano', false);

/*Insercion de personas*/
INSERT INTO futbol5.tl_persona 
(nombre) values ('Diego Sperber'), ('Leo Zelcer'), ('Juan Alonso'), ('Julian Micheles'),('Alejandro Otero');

/*Insercion de inscripciones*/
INSERT INTO futbol5.tl_inscripcion
(ID_Partido, ID_Jugador, Prioridad)
VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 1),
(1, 5, 1),
(1, 6, 1),
(1, 7, 1);

/*Insercion de 2 equipos*/
INSERT INTO futbol5.tl_equipo
(nombre, local) values
('La Banda Del Chavo',true),
('Visitantes', false);

/*Insercion de equipos por partido y jugador*/
INSERT INTO futbol5.tl_equipo_jugador_partido
(ID_Equipo, ID_Jugador, ID_Partido) VALUES
(1, 1, 1),
(2, 2, 1),
(1, 3, 1),
(2, 4, 1),
(1, 5, 1),
(2, 6, 1),
(1, 7, 1);

/*Insercion de calificaciones*/
INSERT INTO futbol5.tl_calificacion
(ID_Equipo_Jugador_Partido, Puntaje, Critica)
VALUES
(1, 10, 'Es un crack!!'),
(2, 4, 'Es medio bastante malo pero metio algunos goles'),
(3, 8, 'La descocio'),
(4, 5, 'Se dio cuenta que era malisimo y se fue a atajar. Ahi la rompio');

/*Insercion de infraccion*/
insert into futbol5.tl_infraccion
(ID_Jugador, Motivo, Fecha)
VALUES
(4, 'Tenia cosas que hacer', curdate()),
(4, 'Tenia cosas que hacer devuelta', curdate()),
(4, 'Se enfermo', curdate()),
(4, 'Tenia cosas que hacer una vez mas', curdate());





