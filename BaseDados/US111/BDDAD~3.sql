SELECT ALL codcd,titulo,datacompra,valorpago,localcompra from cd;
SELECT * from cd;
SELECT ALL nrmusica,codcd,titulo,interprete,duracao from musica;
SELECT * from musica;
SELECT ALL titulo,datacompra from cd;

SELECT distinct datacompra  from cd;

SELECT distinct codcd,interprete from musica;

SELECT distinct codcd "Código do CD",interprete from musica;

SELECT ALL titulo,valorpago,trunc(0.23*valorpago/1.23,2) "IVA_PAGO" from cd;


