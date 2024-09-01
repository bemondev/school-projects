package cfc.servidor.enumerados;

/**
 * Enumerado que representa los resultados de un intento de inicio de sesión.
 */
public enum LoginResultEnum {
    INICIO_CORRECTO,
    CREDENCIALES_INCORRECTAS,
    USUARIO_NO_VALIDADO,
    USUARIO_BAJA,
    USUARIO_INEXISTENTE
}
