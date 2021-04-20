USE [SearchEngine]
GO

INSERT INTO Documentos (nombre) VALUES ('d1')
INSERT INTO Documentos (nombre) VALUES ('d2')
INSERT INTO Documentos (nombre) VALUES ('d3')
INSERT INTO Documentos (nombre) VALUES ('d4')
INSERT INTO Documentos (nombre) VALUES ('d5')

INSERT INTO Terminos (palabra) VALUES('combustible')
INSERT INTO Terminos (palabra) VALUES('diesel')
INSERT INTO Terminos (palabra) VALUES('maquinaria')
INSERT INTO Terminos (palabra) VALUES('agricultura')
INSERT INTO Terminos (palabra) VALUES('transporte')
INSERT INTO Terminos (palabra) VALUES('paro')
INSERT INTO Terminos (palabra) VALUES('venezolano')
INSERT INTO Terminos (palabra) VALUES('calidad')
INSERT INTO Terminos (palabra) VALUES('argentino')

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(1, 1, 1)
INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(1, 2, 1)
INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(1, 5, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(2, 5, 2)
INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(2, 1, 2)
INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(2, 2, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(3, 1, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(4, 1, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(5, 4, 2)
INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(5, 2, 2)
INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(5, 3, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(6, 3, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(7, 5, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(8, 5, 1)

INSERT INTO Posteo (id_termino, id_documento, tf) VALUES(9, 5, 1)