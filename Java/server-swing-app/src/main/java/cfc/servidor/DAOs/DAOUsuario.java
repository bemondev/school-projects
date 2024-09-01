package cfc.servidor.DAOs;

import cfc.servidor.entidades.Usuario;
import com.google.common.hash.Hashing;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.nio.charset.StandardCharsets;

/**
 * DAO para operaciones específicas de la entidad Usuario.
 */
@LocalBean
@Stateless
public class DAOUsuario {

    @PersistenceContext
    private EntityManager em;

    // ======================= Métodos específicos =======================

    /**
     * Obtiene un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario del usuario a obtener.
     * @return Usuario con el nombre de usuario especificado.
     */
    public Usuario obtenerPorUsuario(String username) {
        // Crea una consulta para obtener el usuario con el nombre de usuario especificado
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE LOWER( u.username ) LIKE LOWER( :username )", Usuario.class);

        // Establece el parámetro de la consulta con el nombre de usuario
        query.setParameter("username", username);

        try {
            // Ejecuta la consulta y obtiene el usuario resultante
            Usuario usuario = query.getSingleResult();

            // Imprime un log con el usuario obtenido
            System.out.println("DAOUsuario: usuario encontrado: " + usuario);

            // Retorna el usuario obtenido
            return usuario;
        } catch (NoResultException e) {
            // Si no se encuentra el usuario, imprime un log y retorna null
            System.out.println("DAOUsuario: usuario con el nombre de usuario: " + username + " no encontrado");
            return null;
        }
    }

    /**
     * Encripta una contraseña con el algoritmo SHA-256.<br>
     * Utiliza la librería Guava de Google.
     *
     * @param password Contraseña a encriptar.
     * @return Contraseña encriptada.
     */
    public String encriptarConstrasenia(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

}
