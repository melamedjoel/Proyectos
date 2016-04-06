CREATE SCHEMA `futbol5` ;


CREATE TABLE `futbol5`.`tl_partido` (
  `ID_Partido` INT NOT NULL AUTO_INCREMENT,
  `FechaHora` DATETIME NOT NULL,
  `Lugar` VARCHAR(45) NOT NULL,
  `Confirmado` TINYINT(1) NULL DEFAULT 0,
  PRIMARY KEY (`ID_Partido`))
ENGINE = InnoDB;

CREATE TABLE `futbol5`.`tl_jugador` (
  `ID_Jugador` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Apodo` VARCHAR(45) NULL,
  `FechaNacimiento` DATETIME NOT NULL,
  `Handicap` INT NULL,
  PRIMARY KEY (`ID_Jugador`));
	
CREATE TABLE `futbol5`.`tl_equipo` (
  `ID_Equipo` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `Local` TINYINT(1) NULL,
  PRIMARY KEY (`ID_Equipo`));
  
 CREATE TABLE `futbol5`.`tl_equipo_jugador_partido` (
  `ID_Equipo_Jugador_Partido` INT NOT NULL AUTO_INCREMENT,
  `ID_Equipo` INT NOT NULL,
  `ID_Jugador` INT NOT NULL,
  `ID_Partido` INT NOT NULL,
  PRIMARY KEY (`ID_Equipo_Jugador_Partido`));

ALTER TABLE `futbol5`.`tl_equipo_jugador_partido` 
ADD INDEX `FK_EqJugPartidoConPartido_idx` (`ID_Partido` ASC),
ADD INDEX `FK_EqJugPartidoConJugador_idx` (`ID_Jugador` ASC),
ADD INDEX `FK_EqJugPartidoConEquipo_idx` (`ID_Equipo` ASC);
ALTER TABLE `futbol5`.`tl_equipo_jugador_partido` 
ADD CONSTRAINT `FK_EqJugPartidoConPartido`
  FOREIGN KEY (`ID_Partido`)
  REFERENCES `futbol5`.`tl_partido` (`ID_Partido`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `FK_EqJugPartidoConJugador`
  FOREIGN KEY (`ID_Jugador`)
  REFERENCES `futbol5`.`tl_jugador` (`ID_Jugador`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `FK_EqJugPartidoConEquipo`
  FOREIGN KEY (`ID_Equipo`)
  REFERENCES `futbol5`.`tl_equipo` (`ID_Equipo`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
 CREATE TABLE `futbol5`.`tl_inscripcion` (
  `ID_Inscripcion` INT NOT NULL AUTO_INCREMENT,
  `ID_Partido` INT NOT NULL,
  `ID_Jugador` INT NOT NULL,
  `Prioridad` INT NULL,
  PRIMARY KEY (`ID_Inscripcion`),
  INDEX `FK_InscripcionConJugador_idx` (`ID_Jugador` ASC),
  INDEX `FK_InscripcionConPartido_idx` (`ID_Partido` ASC),
  CONSTRAINT `FK_InscripcionConJugador`
    FOREIGN KEY (`ID_Jugador`)
    REFERENCES `futbol5`.`tl_jugador` (`ID_Jugador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_InscripcionConPartido`
    FOREIGN KEY (`ID_Partido`)
    REFERENCES `futbol5`.`tl_partido` (`ID_Partido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	
CREATE TABLE `futbol5`.`tl_calificacion` (
  `ID_Calificacion` INT NOT NULL AUTO_INCREMENT,
  `ID_Equipo_Jugador_Partido` INT NOT NULL,
  `Puntaje` DECIMAL(10,2) NULL,
  `Critica` VARCHAR(250) NULL,
  PRIMARY KEY (`ID_Calificacion`),
  INDEX `FK_CalifConEqJugPart_idx` (`ID_Equipo_Jugador_Partido` ASC),
  CONSTRAINT `FK_CalifConEqJugPart`
    FOREIGN KEY (`ID_Equipo_Jugador_Partido`)
    REFERENCES `futbol5`.`tl_equipo_jugador_partido` (`ID_Equipo_Jugador_Partido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `futbol5`.`tl_infraccion` (
  `ID_Infraccion` INT NOT NULL AUTO_INCREMENT,
  `ID_Jugador` INT NOT NULL,
  `Motivo` VARCHAR(250) NULL,
  `Fecha` DATETIME NULL,
  PRIMARY KEY (`ID_Infraccion`),
  INDEX `FK_InfraccionConJugador_idx` (`ID_Jugador` ASC),
  CONSTRAINT `FK_InfraccionConJugador`
    FOREIGN KEY (`ID_Jugador`)
    REFERENCES `futbol5`.`tl_jugador` (`ID_Jugador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	

CREATE TABLE `futbol5`.`tl_persona` (
  `ID_Persona` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_Persona`));

 CREATE TABLE `futbol5`.`tl_amigos_persona` (
  `ID_Jugador` INT NOT NULL,
  `ID_Persona` INT NOT NULL,
  PRIMARY KEY (`ID_Jugador`, `ID_Persona`),
  INDEX `FK_AmigosPersonaConJugador_idx` (`ID_Jugador` ASC),
  INDEX `FK_AmigosPersonaConPersona_idx` (`ID_Persona` ASC),
  CONSTRAINT `FK_AmigosPersonaConJugador`
    FOREIGN KEY (`ID_Jugador`)
    REFERENCES `futbol5`.`tl_jugador` (`ID_Jugador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_AmigosPersonaConPersona`
    FOREIGN KEY (`ID_Persona`)
    REFERENCES `futbol5`.`tl_persona` (`ID_Persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `futbol5`.`tl_estado_mocion` (
  `ID_Estado_Mocion` INT NOT NULL AUTO_INCREMENT,
  `Descripcion` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`ID_Estado_Mocion`));

 CREATE TABLE `futbol5`.`tl_mocion` (
  `ID_Mocion` INT NOT NULL AUTO_INCREMENT,
  `ID_Persona` INT NOT NULL,
  `ID_Estado_Mocion` INT NOT NULL,
  PRIMARY KEY (`ID_Mocion`),
  INDEX `FK_MocionConPersona_idx` (`ID_Persona` ASC),
  INDEX `FK_MocionConEstado_idx` (`ID_Estado_Mocion` ASC),
  CONSTRAINT `FK_MocionConPersona`
    FOREIGN KEY (`ID_Persona`)
    REFERENCES `futbol5`.`tl_persona` (`ID_Persona`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_MocionConEstado`
    FOREIGN KEY (`ID_Estado_Mocion`)
    REFERENCES `futbol5`.`tl_estado_mocion` (`ID_Estado_Mocion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

/*Punto 3A: creamos una vista para obtener los jugadores malos, por lo que solo deberemos llamar a la vista para obtenerlos*/
CREATE VIEW futbol5.jugadoresmalos AS SELECT * FROM futbol5.tl_jugador where Handicap <= 5;
/*Punto 3B: creamos una vista para obtener los jugadores traicioneros por lo que solo deberemos llamar a la vista para obtenerlos*/
CREATE VIEW futbol5.jugadorestraicioneros AS
SELECT * FROM futbol5.tl_jugador WHERE 
ID_Jugador IN
(SELECT ID_Jugador FROM futbol5.tl_infraccion 
WHERE MONTH(Fecha) = MONTH(CURDATE())
GROUP BY ID_Jugador
HAVING COUNT(ID_Jugador) > 3);

/*Punto 3C: creamos una stored procedure para ello*/
USE `futbol5`;
DROP procedure IF EXISTS `jugadoresQuePuedenMejorar`;

DELIMITER $$
USE `futbol5`$$
CREATE PROCEDURE `jugadoresQuePuedenMejorar` ()
BEGIN
	select count(*) from futbol5.jugadoresmalos where 
    (DATE_FORMAT(NOW(), '%Y') - DATE_FORMAT(FechaNacimiento, '%Y') - (DATE_FORMAT(NOW(), '00-%m-%d') < DATE_FORMAT(FechaNacimiento, '00-%m-%d'))) < 25;
END
$$

DELIMITER ;


/*Punto D: Agregamos una columna en la tabla equipo_jugador_partido para saberlo, que se llame activo, por ejemplo, de tipo booleano*/
ALTER TABLE `futbol5`.`tl_equipo_jugador_partido` 
ADD COLUMN `Activo` TINYINT(1) NULL DEFAULT 1 AFTER `ID_Partido`;

/*Punto E: Podriamos hacer un trigger, es decir, disparar una accion ante cada delete que se haga de la tabla en donde se encuentran los
jugadores de un partido, pero no podriamos validar si ofrecio o no reemplazante. Entonces, hacemos una stored procedure y que sea usada
por el programador cuando sea necesario. Además, la lógica de este punto ya fue realizada en el dominio, por lo que evitamos tener que 
cambiarlo 2 veces en caso de que se modifique esta regla de negocio*/
USE `futbol5`;
DROP procedure IF EXISTS `insertarInfraccion`;

DELIMITER $$
USE `futbol5`$$
CREATE PROCEDURE `insertarInfraccion` (in p_id_jugador int, in p_motivo varchar(250), in p_fecha datetime)
BEGIN
	insert into tl_infraccion (ID_Jugador, Motivo, Fecha) VALUES (p_id_jugador, p_motivo, p_fecha);
END
$$

DELIMITER ;





