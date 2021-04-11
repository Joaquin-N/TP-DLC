--VOCABULARIO
SELECT termino, COUNT(*) as nr, MAX(tf) as max_tf
FROM Posteo p
GROUP BY termino

--POSTEO
SELECT p.termino, d.nombre as documento, p.tf
FROM Posteo p JOIN Documentos d ON p.id_documento = d.id
ORDER BY p.termino, p.tf DESC