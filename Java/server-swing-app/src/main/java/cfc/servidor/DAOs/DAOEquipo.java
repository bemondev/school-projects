package cfc.servidor.DAOs;

import cfc.servidor.entidades.Equipo;
import cfc.servidor.entidades.Modelo;
import jakarta.ejb.EJB;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operaciones específicas de la entidad Equipo.
 */
@Stateless
@LocalBean
public class DAOEquipo {

    // ================= Inyecciones de dependencias =================

    @EJB
    private DAOModelo daoModelo;

    @PersistenceContext
    private EntityManager em;

    // ======================= Métodos específicos =======================

    /**
     * Obtiene todos los equipos de un modelo específico.
     *
     * @param idModelo Id del modelo de los equipos a obtener.
     * @return Lista de equipos del modelo especificado.
     */
    public List<Equipo> obtenerPorModelo(Integer idModelo) {
        // Crea una consulta para obtener todos los equipos del modelo especificado
        TypedQuery<Equipo> query = em.createQuery("SELECT e FROM Equipo e WHERE e.modeloEquipo.id = :idModelo", Equipo.class);

        // Establece el parámetro de la consulta con el ID del modelo
        query.setParameter("idModelo", idModelo);

        // Ejecuta la consulta y obtiene la lista resultante
        List<Equipo> equipos = query.getResultList();

        // Imprime un log con la cantidad de elementos obtenidos
        System.out.println("[LOG-DAOEquipo] obtenerPorModelo - " + idModelo + " - " + equipos.size() + " equipos");

        // Retorna la lista de equipos
        return equipos;
    }

    /**
     * Obtiene todos los equipos de una marca específica.
     *
     * @param idMarca Id de la marca de los equipos a obtener.
     * @return Lista de equipos de la marca especificada.
     */
    public List<Equipo> obtenerPorMarca(Integer idMarca) {
        // Obtiene todos los modelos de la marca especificada
        List<Modelo> modelosDeMarca = this.daoModelo.obtenerPorMarca(idMarca);

        // Crea una lista para almacenar los equipos de la marca
        List<Equipo> equiposPorMarca = new ArrayList<>();

        // Recorre los modelos de la marca y obtiene los equipos de cada uno
        // y los agrega a la lista de equipos de la marca
        for (Modelo modelo : modelosDeMarca) {
            List<Equipo> equiposPorModelo = this.obtenerPorModelo(modelo.getId());
            equiposPorMarca.addAll(equiposPorModelo);
        }

        // Imprime un log con la cantidad de elementos obtenidos
        System.out.println("[LOG-DAOEquipo] obtenerPorMarca - " + idMarca + " - " + equiposPorMarca.size() + " equipos");

        // Retorna la lista de equipos de la marca
        return equiposPorMarca;
    }

    /**
     * Obtiene todos los equipos de un país específico.
     *
     * @param idPais Id del país de los equipos a obtener.
     * @return Lista de equipos del país especificado.
     */
    public List<Equipo> obtenerPorPais(Integer idPais) {
        // Crea una consulta para obtener todos los equipos del país especificado
        TypedQuery<Equipo> query = em.createQuery("SELECT e FROM Equipo e WHERE e.paisDeOrigen.id = :idPais", Equipo.class);

        // Establece el parámetro de la consulta con el ID del país
        query.setParameter("idPais", idPais);

        // Ejecuta la consulta y obtiene la lista resultante
        List<Equipo> equipos = query.getResultList();

        // Imprime un log con la cantidad de elementos obtenidos
        System.out.println("[LOG-DAOEquipo] obtenerPorPais - " + idPais + " - " + equipos.size() + " equipos");

        // Retorna la lista de equipos
        return equipos;
    }

    /**
     * Obtiene un equipo por su ID interna.
     *
     * @param id Id interna del equipo a obtener.
     * @return Equipo con el ID Interno especificado.
     */
    public Equipo obtenerPorIDInterna(Integer id) {
        // Crea una consulta para obtener el equipo con el ID interno especificado
        TypedQuery<Equipo> query = em.createQuery("SELECT e FROM Equipo e WHERE e.idInterna = :id", Equipo.class);

        // Establece el parámetro de la consulta con el ID interno
        query.setParameter("id", id);

        // Ejecuta la consulta y obtiene el equipo resultante
        Equipo equipo = query.getSingleResult();

        // Imprime un log con el equipo obtenido
        System.out.println("[LOG-DAOEquipo] obtenerPorIDInterna - " + id + " - " + equipo + " equipos");

        // Retorna el equipo obtenido
        return equipo;
    }

    public Equipo obtenerPorNumeroDeSerie(String numero) {
        // Crea una consulta para obtener el equipo con el numero de serie especificado
        TypedQuery<Equipo> query = em.createQuery("SELECT e FROM Equipo e WHERE e.numeroSerie = :numero", Equipo.class);

        // Establece el parámetro de la consulta con el numero de serie
        query.setParameter("numero", numero);

        // Ejecuta la consulta y obtiene el equipo resultante
        Equipo equipo = query.getSingleResult();

        // Imprime un log con el equipo obtenido
        System.out.println("[LOG-DAOEquipo] obtenerPorNumeroDeSerie - " + numero + " - " + equipo + " equipos");

        // Retorna el equipo obtenido
        return equipo;
    }


}