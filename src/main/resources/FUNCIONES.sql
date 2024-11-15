DELIMITER $$

USE BDEMPRESA$$

DROP FUNCTION IF EXISTS FN_EMPDEPART$$
CREATE FUNCTION FN_EMPDEPART(NOME_DEPARTAMENTO_IN VARCHAR(25))
RETURNS INT
READS SQL DATA
BEGIN
	DECLARE NUM_EMPREGADOS INT;
    SELECT COUNT(*) INTO NUM_EMPREGADOS
		FROM EMPREGADO
		WHERE NUM_DEPARTAMENTO_PERTENECE = (
												SELECT NUM_DEPARTAMENTO
													FROM DEPARTAMENTO
                                                    WHERE NOME_DEPARTAMENTO = NOME_DEPARTAMENTO_IN
                                            );
	RETURN NUM_EMPREGADOS;
END$$


