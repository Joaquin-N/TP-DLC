USE [SearchEngine]
GO

--VOCABULARIO
SELECT t.palabra, COUNT(*) as nr, MAX(p.tf) as max_tf
FROM Posteo p JOIN Terminos t ON t.id = p.id_termino
GROUP BY t.palabra

--POSTEO
SELECT t.palabra, d.nombre as documento, p.tf
FROM Posteo p 
JOIN Documentos d ON p.id_documento = d.id
JOIN Terminos t ON p.id_termino = t.id
ORDER BY t.palabra, p.tf DESC