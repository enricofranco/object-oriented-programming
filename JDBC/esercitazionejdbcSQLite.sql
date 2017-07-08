DROP TABLE iscritto_corso;
DROP TABLE cliente;
DROP TABLE corso;


CREATE TABLE cliente (
  codicecliente int  NOT NULL,
  nome varchar(50)  NOT NULL,
  cognome varchar(50)  NOT NULL,
  indirizzo varchar(100) NOT NULL,
  cell varchar(50) NULL,
  PRIMARY KEY (codicecliente)
);

CREATE TABLE corso (
  codcorso int NOT NULL,
  nomeC varchar(50)  NOT NULL,
  livello varchar(50)  NOT NULL,
  costo int NOT NULL,
  postidisponibili int NOT NULL,
  PRIMARY KEY (codcorso)
);


CREATE TABLE iscritto_corso (
  codicecliente int NOT NULL,
  codicecorso int NOT NULL,
  dataiscrizione date NOT NULL,
  PRIMARY KEY (codicecliente,codicecorso),
  FOREIGN KEY (codicecliente) REFERENCES cliente(codicecliente),
  FOREIGN KEY (codicecorso) REFERENCES corso(codcorso)
);



INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (1, 'Christian', 'Drago', 'Corso Abruzzi 50, Torino', '4294967295');
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (2, 'Francesco', 'Bianco', 'Via Ignota 71, Milano', '0113336458');
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (3, 'Giacomo', 'Neri', 'Corso Roma 125, Torino', '1115484552');
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (4, 'Alfredo', 'Bianco', 'Corso Milano 63, Roma', '1214444452');
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (5, 'Alessandra', 'Rossi', 'Corso Fantasia 6, Torino', '1215444552');
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (6, 'Gianfranco', 'Neri', 'Via Ignoto 5, Milano', '1278244552');
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (7, 'Luca', 'Rosso', 'Corso Nuovo 12, Napoli', NULL);
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (8, 'Daniele', 'Bianchi', 'Corso Torino 4, Roma', NULL);
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (9, 'Gabriela', 'Rossi', 'Corso Lipari 15, Firenze', NULL);
INSERT INTO cliente (codicecliente, nome, cognome, indirizzo, cell) VALUES (10, 'Lorenzo', 'Neri', 'Via Genova 114, Torino', NULL);


INSERT INTO corso (codcorso, nomeC, livello, costo, postidisponibili) VALUES (1, 'DataBase', 'Avanzato', 100, 4);
INSERT INTO corso (codcorso, nomeC, livello, costo, postidisponibili) VALUES (2, 'Java', 'Base', 50, 10);
INSERT INTO corso (codcorso, nomeC, livello, costo, postidisponibili) VALUES (3, 'DotNet', 'Intermedio', 75, 20);
INSERT INTO corso (codcorso, nomeC, livello, costo, postidisponibili) VALUES (4, 'SistemiOperativi', 'Base', 200, 1);



INSERT INTO iscritto_corso (codicecliente, codicecorso, dataiscrizione) VALUES (1, 1, DATE('2013-05-18'));
INSERT INTO iscritto_corso (codicecliente, codicecorso, dataiscrizione) VALUES (1, 2, DATE('2013-05-18'));
INSERT INTO iscritto_corso (codicecliente, codicecorso, dataiscrizione) VALUES (1, 3, DATE('2013-05-18'));
INSERT INTO iscritto_corso (codicecliente, codicecorso, dataiscrizione) VALUES (2, 3, DATE('2013-05-18'));
INSERT INTO iscritto_corso (codicecliente, codicecorso, dataiscrizione) VALUES (3, 3, DATE('2013-05-18'));



