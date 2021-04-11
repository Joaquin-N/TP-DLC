USE [SearchEngine]
GO

INSERT INTO Documentos (nombre) VALUES ('d1')
INSERT INTO Documentos (nombre) VALUES ('d2')
INSERT INTO Documentos (nombre) VALUES ('d3')
INSERT INTO Documentos (nombre) VALUES ('d4')
INSERT INTO Documentos (nombre) VALUES ('d5')

INSERT INTO Posteo (termino, id_documento, tf) VALUES('combustible', 1, 1)
INSERT INTO Posteo (termino, id_documento, tf) VALUES('combustible', 2, 1)
INSERT INTO Posteo (termino, id_documento, tf) VALUES('combustible', 5, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('diesel', 5, 2)
INSERT INTO Posteo (termino, id_documento, tf) VALUES('diesel', 1, 2)
INSERT INTO Posteo (termino, id_documento, tf) VALUES('diesel', 2, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('maquinaria', 1, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('agricultura', 1, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('transporte', 4, 2)
INSERT INTO Posteo (termino, id_documento, tf) VALUES('transporte', 2, 2)
INSERT INTO Posteo (termino, id_documento, tf) VALUES('transporte', 3, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('paro', 3, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('venezolano', 5, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('calidad', 5, 1)

INSERT INTO Posteo (termino, id_documento, tf) VALUES('argentino', 5, 1)