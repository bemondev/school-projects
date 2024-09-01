package cfc.servidor.DAOs;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO para operaciones CRUD genéricas.
 * <br><br>
 * Define métodos genéricos para registrar, obtener, actualizar y eliminar
 * entidades de cualquier tipo.
 */
@Stateless
@LocalBean
public class DAOCRUDGeneral {

    /**
     * Inyección de dependencia del EntityManager.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Registra una entidad en la base de datos.
     *
     * @param entidad Entidad a registrar.
     */
    public void registrar(Object entidad) {
        // Persiste el objeto en la base de datos
        em.persist(entidad);

        // Hace un flush para que los cambios se reflejen en la base de datos inmediatamente
        em.flush();

        // Imprime un log con el objeto registrado
        System.out.println("[LOG-DAOCRUDGeneral] registrar - " + entidad);
    }

    /**
     * Obtiene todas las entidades de una clase en la base de datos.
     *
     * @param claseDelObjeto Clase de la entidad a obtener.
     * @param <ENTIDAD>      Tipo de la clase de la entidad.
     * @return Lista de entidades de la clase especificada.
     */
    public <ENTIDAD> List<ENTIDAD> obtenerTodo(Class<ENTIDAD> claseDelObjeto) {
        // Crea una consulta para obtener todas las entidades de la clase especificada
        TypedQuery<ENTIDAD> query = em.createQuery("SELECT o FROM " + claseDelObjeto.getSimpleName() + " o", claseDelObjeto);

        // Ejecuta la consulta y obtiene la lista resultante
        List<ENTIDAD> lista = query.getResultList();

        // Imprime un log con la cantidad de elementos obtenidos
        System.out.println("[LOG-DAOCRUDGeneral] obtenerTodo - " + claseDelObjeto.getSimpleName() + ": " + lista.size() + " elementos");

        // Retorna la lista obtenida
        return lista;
    }

    /**
     * Obtiene una entidad por su nombre en la base de datos.
     *
     * @param claseDelObjeto Clase de la entidad a obtener.
     * @param nombre         Nombre de la entidad a obtener.
     * @param <ENTIDAD>      Tipo de la clase de la entidad.
     * @return Entidad con el nombre especificado.
     */
    public <ENTIDAD> ENTIDAD obtenerPorNombre(Class<ENTIDAD> claseDelObjeto, String nombre) {
        try {

            // Agrega un comodín al final del nombre para obtener coincidencias parciales
            nombre += "%";

            // Crea una consulta para obtener la entidad con el nombre especificado
            TypedQuery<ENTIDAD> query = em.createQuery("SELECT o FROM " + claseDelObjeto.getSimpleName() + " o WHERE LOWER( o.nombre ) LIKE LOWER( :nombre )", claseDelObjeto);

            // Establece el parámetro de la consulta
            query.setParameter("nombre", nombre);

            // Ejecuta la consulta y obtiene la entidad resultante
            ENTIDAD ENTIDAD = query.getSingleResult();

            // Imprime un log con la entidad obtenida
            System.out.println("[LOG-DAOCRUDGeneral] obtenerPorNombre - " + claseDelObjeto.getSimpleName() + " - " + nombre + " - " + ENTIDAD);

            // Retorna la entidad obtenida
            return ENTIDAD;
        } catch (NoResultException e) {
            // Si no se encuentra la entidad, imprime un log y retorna null
            System.out.println("[LOG-DAOCRUDGeneral] obtenerPorNombre - " + claseDelObjeto.getSimpleName() + " - " + nombre + " - NoResultException");
            return null;
        }
    }

    /**
     * Obtiene una entidad por su ID en la base de datos.
     *
     * @param claseDelObjeto Clase de la entidad a obtener.
     * @param id             Id de la entidad a obtener.
     * @param <ENTIDAD>      Tipo de la clase de la entidad.
     * @return Entidad con el ID especificado.
     */
    public <ENTIDAD> ENTIDAD obtenerPorID(Class<ENTIDAD> claseDelObjeto, Integer id) {
        try {
            // Crea una consulta para obtener la entidad con el ID especificado
            TypedQuery<ENTIDAD> query = em.createQuery("SELECT o FROM " + claseDelObjeto.getSimpleName() + " o WHERE o.id = :id", claseDelObjeto);

            // Establece el parámetro de la consulta
            query.setParameter("id", id);

            // Ejecuta la consulta y obtiene la entidad resultante
            ENTIDAD ENTIDAD = query.getSingleResult();

            // Imprime un log con la entidad obtenida
            System.out.println("[LOG-DAOCRUDGeneral] obtenerPorID - " + claseDelObjeto.getSimpleName() + " - " + id + " - " + ENTIDAD);

            // Retorna la entidad obtenida
            return ENTIDAD;
        } catch (NoResultException e) {
            // Si no se encuentra la entidad, imprime un log y retorna null
            System.out.println("[LOG-DAOCRUDGeneral] obtenerPorID - " + claseDelObjeto.getSimpleName() + " - " + id + " - NoResultException");
            return null;
        }
    }

    /**
     * Actualiza una entidad en la base de datos.
     *
     * @param entidad Entidad a actualizar.
     */
    public void actualizar(Object entidad) {
        // Actualiza la entidad en la base de datos
        em.merge(entidad);

        // Hace un flush para que los cambios se reflejen en la base de datos inmediatamente
        em.flush();

        // Imprime un log con el objeto actualizado
        System.out.println("[LOG-DAOCRUDGeneral] actualizar - " + entidad);
    }

    /**
     * Elimina una entidad de la base de datos.
     *
     * @param entidad Entidad a eliminar.
     */
    public void eliminar(Object entidad) {
        // Elimina la entidad de la base de datos
        em.remove(entidad);

        // Hace un flush para que los cambios se reflejen en la base de datos inmediatamente
        em.flush();

        // Imprime un log con el objeto eliminado
        System.out.println("[LOG-DAOCRUDGeneral] eliminar - " + entidad);
    }

}
