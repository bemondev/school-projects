package cfc.servidor.DAOs;

import cfc.servidor.entidades.Modelo;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO para operaciones específicas de la entidad Modelo.
 */
@LocalBean
@Stateless
public class DAOModelo {

    @PersistenceContext
    private EntityManager em;

    /**
     * Obtiene todos los modelos de una marca específica.
     *
     * @param id Id de la marca de los modelos a obtener.
     * @return Lista de modelos de la marca especificada.
     */
    public List<Modelo> obtenerPorMarca(Integer id) {
        // Crea una consulta para obtener todos los modelos de la marca especificada
        TypedQuery<Modelo> query = em.createQuery("SELECT m FROM Modelo m WHERE m.marca.id = :id", Modelo.class);

        // Establece el parámetro de la consulta con el ID de la marca
        query.setParameter("id", id);

        // Ejecuta la consulta y obtiene la lista resultante
        List<Modelo> modelos = query.getResultList();

        // Imprime un log con la cantidad de elementos obtenidos
        System.out.println("[LOG-DAOModelo] obtenerPorMarca - " + id + " - " + modelos.size() + " modelos");

        // Retorna la lista de modelos
        return modelos;
    }
}
