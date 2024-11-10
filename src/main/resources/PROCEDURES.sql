DELIMITER $$

USE BDEMPRESA$$

DROP PROCEDURE IF EXISTS PR_CAMBIODOMICILIO$$
CREATE PROCEDURE PR_CAMBIODOMICILIO(IN NSS_IN VARCHAR(15), IN RUA_IN VARCHAR(30),IN NUMERO_RUA_IN INT, IN PISO_IN VARCHAR(4), IN CP_IN CHAR(5), IN LOCALIDADE_IN VARCHAR(25))
MODIFIES SQL DATA
BEGIN
	UPDATE EMPREGADO
    SET	RUA = RUA_IN, NUMERO_RUA = NUMERO_RUA_IN, PISO = PISO_IN, CP = CP_IN, LOCALIDADE = LOCALIDADE_IN
    WHERE NSS = NSS_IN;
END$$

DROP PROCEDURE IF EXISTS PR_DATOSPROXECTOS$$
CREATE PROCEDURE PR_DATOSPROXECTOS(
		IN NUM_PROXECTO_IN INT UNSIGNED, 
        OUT NOME_PROXECTO_OUT VARCHAR(25), 
        OUT LUGAR_OUT VARCHAR(25), 
        OUT NUM_DEPARTAMENTO_OUT INT UNSIGNED
)
READS SQL DATA
BEGIN
	SELECT  NOME_PROXECTO,
            LUGAR,
            NUM_DEPARTAMENTO
	INTO NOME_PROXECTO_OUT, LUGAR_OUT, NUM_DEPARTAMENTO_OUT
    FROM PROXECTO
    WHERE NUM_PROXECTO = NUM_PROXECTO_IN;
    
    IF ROW_COUNT() = 0 THEN
        SET NOME_PROXECTO_OUT = NULL;
        SET LUGAR_OUT = NULL;
        SET NUM_DEPARTAMENTO_OUT = NULL;
    END IF;
	
END$$

DROP PROCEDURE IF EXISTS PR_DEPARTCONTROLAPROXEC$$
CREATE PROCEDURE PR_DEPARTCONTROLAPROXEC(IN NUM_PROXECTOS_IN INT UNSIGNED)
READS SQL DATA
BEGIN
		SELECT 	D.NUM_DEPARTAMENTO,
				D.NOME_DEPARTAMENTO,
                D.NSS_DIRIGE,
                D.DATA_DIRECCION
			FROM DEPARTAMENTO AS D LEFT JOIN PROXECTO AS P /*(0,N)*/
				ON D.NUM_DEPARTAMENTO = P.NUM_DEPARTAMENTO
		GROUP BY D.NUM_DEPARTAMENTO
		HAVING COUNT(P.NUM_PROXECTO) >= NUM_PROXECTOS_IN;
END$$

