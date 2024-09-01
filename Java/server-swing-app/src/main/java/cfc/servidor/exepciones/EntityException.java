package cfc.servidor.exepciones;

/**
 * Excepción lanzada al ocurrir un error persistiendo o actualizando una entidad
 * en la base de datos.<br>
 * Ejemplo: Violación de restricciones de unicidad, tamaño/longitud de campos, etc.
 */
public class EntityException extends Exception {
    public EntityException(String message) {
        super(message);
    }
}
