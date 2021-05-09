USE [SearchEngine]
GO

--VOCABULARIO
SELECT pa.palabra, COUNT(*) as nr, MAX(p.tf) as max_tf
FROM Posteo p JOIN Palabras pa ON pa.id = p.id_palabra
GROUP BY pa.palabra

--POSTEO
SELECT pa.palabra, d.documento, p.tf
FROM Posteo p 
JOIN Documentos d ON p.id_documento = d.id
JOIN Palabras pa ON p.id_palabra = pa.id
ORDER BY pa.palabra, p.tf DESC

--POSTEO PARA 1 PALABRA
SELECT TOP [R] d.documento, p.tf
FROM Posteo p 
JOIN Documentos d ON p.id_documento = d.id
JOIN Palabras pa ON p.id_palabra = pa.id
WHERE pa.palabra = [palabra]
ORDER BY p.tf DESC