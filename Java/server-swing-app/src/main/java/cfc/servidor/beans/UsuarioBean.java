package cfc.servidor.beans;

import cfc.servidor.DAOs.DAOCRUDGeneral;
import cfc.servidor.DAOs.DAOUsuario;
import cfc.servidor.DTOs.UsuarioDTO;
import cfc.servidor.entidades.Equipo;
import cfc.servidor.entidades.Perfil;
import cfc.servidor.entidades.Usuario;
import cfc.servidor.enumerados.EstadosEnum;
import cfc.servidor.exepciones.EntityException;
import cfc.servidor.local.IUsuarioBeanLocal;
import cfc.servidor.servicios.IUsuarioRemote;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del bean remoto de usuario.
 */
@Stateless
public class UsuarioBean implements IUsuarioRemote, IUsuarioBeanLocal {

    // ================= Inyecciones de dependencias =================

    @EJB
    private DAOUsuario daoUsuario;

    @EJB
    private DAOCRUDGeneral daoCrudGeneral;


    // ======================= Métodos remotos =======================
    @Override
    public UsuarioDTO registrar(UsuarioDTO usuarioDTO, String password) throws EntityException {

        controlarRestricciones(usuarioDTO);

        // Crea una nueva entidad de usuario donde se cargarán los datos
        Usuario usuario = new Usuario();

        // Carga los datos del DTO a la entidad
        this.cargarDatosDTO(usuario, usuarioDTO);

        // Encripta la contraseña y la asigna al usuario
        usuario.setPassword(this.daoUsuario.encriptarConstrasenia(password));

        // Si el username no fue proporcionado, genera uno automáticamente
        // a partir de los nombres y apellidos del usuario.
        if (usuarioDTO.getUsername() == null || usuarioDTO.getUsername().isEmpty()) {
            usuario.setUsername(
                    this.generarUsername(
                            usuarioDTO.getNombre().split(" "),
                            usuarioDTO.getApellido().split(" ")
                    )
            );
        }

        // Registra el usuario en la base de datos
        this.daoCrudGeneral.registrar(usuario);

        // Actualiza el DTO con el ID y username generados
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());

        // Retorna el DTO actualizado
        return usuarioDTO;
    }

    @Override
    public UsuarioDTO obtenerPorUsername(String username) {
        // Obtiene la entidad de usuario por su username
        Usuario usuario = this.daoUsuario.obtenerPorUsuario(username);

        // Si no existe, retorna null
        if (usuario == null) return null;

        // Crea y retorna un DTO con los datos del usuario encontrado
        return new UsuarioDTO(usuario);
    }

    @Override
    public UsuarioDTO obtenerPorID(Integer id) {
        // Obtiene la entidad de usuario por su ID
        Usuario usuario = this.daoCrudGeneral.obtenerPorID(Usuario.class, id);

        // Si no existe, retorna null
        if (usuario == null) return null;

        // Crea y retorna un DTO con los datos del usuario encontrado
        return new UsuarioDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> obtenerTodo() {
        // Obtiene la lista de todos los usuarios registrados
        List<Usuario> listaUsuarios = this.daoCrudGeneral.obtenerTodo(Usuario.class);

        // Crea una lista de DTOs donde se guardarán los datos de los usuarios.
        List<UsuarioDTO> listaUsuariosDTO = new ArrayList<>();

        // Convierte cada entidad de usuario a un DTO y la agrega a la lista
        for (Usuario usuario : listaUsuarios) {
            listaUsuariosDTO.add(new UsuarioDTO(usuario));
        }

        // Retorna la lista de DTOs
        return listaUsuariosDTO;
    }

    @Override
    public void desactivar(Integer id) {
        // Obtiene la entidad de usuario por su ID
        Usuario usuario = this.daoCrudGeneral.obtenerPorID(Usuario.class, id);

        // Si existe, cambia su estado a Eliminado y lo actualiza en la base de datos
        if (usuario != null) {
            usuario.setEstado(EstadosEnum.ELIMINADO);
            this.daoCrudGeneral.actualizar(usuario);
        }
    }

    @Override
    public void activar(Integer id) {
        // Obtiene la entidad de usuario por su ID
        Usuario usuario = this.daoCrudGeneral.obtenerPorID(Usuario.class, id);

        // Si existe, cambia su estado a VALIDADO y lo actualiza en la base de datos
        if (usuario != null) {
            usuario.setEstado(EstadosEnum.VALIDADO);
            this.daoCrudGeneral.actualizar(usuario);
        }
    }

    @Override
    public void actualizar(UsuarioDTO usuarioDTO, String password, String nuevaPassword) throws EntityException {
        controlarRestricciones(usuarioDTO);
        String actualPass = daoUsuario.encriptarConstrasenia(password);
        String nuevaPass = daoUsuario.encriptarConstrasenia(nuevaPassword);

        Usuario usuario = this.daoCrudGeneral.obtenerPorID(Usuario.class, usuarioDTO.getId());

        controlarCotrasenia(usuario, actualPass);
        usuario.setPassword(nuevaPass);
        this.cargarDatosDTO(usuario, usuarioDTO);
        this.daoCrudGeneral.actualizar(usuario);

    }

    @Override
    public void actualizarAdmin(UsuarioDTO usuarioDTO) throws EntityException {

        controlarRestricciones(usuarioDTO);

        Usuario usuario = this.daoCrudGeneral.obtenerPorID(Usuario.class, usuarioDTO.getId());

        this.cargarDatosDTO(usuario, usuarioDTO);
        this.daoCrudGeneral.actualizar(usuario);

    }

    public boolean comprobarContrasenia(String password, UsuarioDTO usuarioDTO) {
        String pass = daoUsuario.encriptarConstrasenia(password);
        Usuario usuario = this.daoCrudGeneral.obtenerPorID(Usuario.class, usuarioDTO.getId());
        return usuario.getPassword().equals(pass);
    }

    @Override
    public void actualizar(UsuarioDTO u) throws EntityException {
        // Controla las restricciones de los datos
        controlarRestricciones(u);

        // Obtiene la entidad de equipo por su ID
        Usuario usuario = this.daoCrudGeneral.obtenerPorID(Usuario.class, u.getId());

        // Si existe, actualiza sus datos y lo actualiza en la base de datos
        if (usuario != null) {
            this.cargarDatosDTO(usuario, u);
            this.daoCrudGeneral.actualizar(usuario);
        }
    }

    // ====================== Métodos privados =======================

    /**
     * Método para cargar los datos de un DTO a una entidad.
     *
     * @param usuario    Entidad a la que se le cargarán los datos
     * @param usuarioDTO DTO con los datos a cargar
     */
    private void cargarDatosDTO(Usuario usuario, UsuarioDTO usuarioDTO) {
        usuario.setId(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCedula(usuarioDTO.getCedula());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setEstado(usuarioDTO.getEstado());
        Perfil perfil = this.daoCrudGeneral.obtenerPorID(Perfil.class, usuarioDTO.getIdPerfil());
        usuario.setPerfil(perfil);
    }

    /**
     * Método para generar un username a partir de los nombres y apellidos del usuario.
     * <br><br>
     * El username se genera a partir de las siguientes combinaciones:
     * <ul>
     *     <li>nombre1.apellido1</li>
     *     <li>nombre1.apellido1.[primer carácter apellido2]</li>
     *     <li>nombre1.[primer carácter nombre2].apellido1</li>
     *     <li>nombre1.[primer carácter nombre2].apellido1.[primer carácter apellido2]</li>
     *     <li>nombre1.apellido1.[iteración]</li>
     *     <li>nombre1.apellido1.[primer carácter apellido2].[iteración]</li>
     *     <li>nombre1.[primer carácter nombre2].apellido1.[iteración]</li>
     *     <li>nombre1.[primer carácter nombre2].apellido1.[primer carácter apellido2].[iteración]</li>
     * </ul>
     *
     * @param nombres   Nombres del usuario
     * @param apellidos Apellidos del usuario
     * @return Username generado
     */
    private String generarUsername(String[] nombres, String[] apellidos) {
        // Lista para almacenar los usernames posibles
        List<String> usernamesPosibles = new ArrayList<>();

        // nombre1.apellido1
        usernamesPosibles.add(nombres[0] + "." + apellidos[0]);

        // -- Agrega las combinaciones posibles a la lista --

        // nombre1.apellido1.[primer carácter apellido2]
        if (apellidos.length > 1) {
            usernamesPosibles.add(nombres[0] + "." + apellidos[0] + "." + apellidos[1].charAt(0));
        }

        // nombre1.[primer carácter nombre2].apellido1
        if (nombres.length > 1) {
            usernamesPosibles.add(nombres[0] + "." + nombres[1].charAt(0) + "." + apellidos[0]);
        }

        // nombre1.[primer carácter nombre2].apellido1.[primer carácter apellido2]
        if (nombres.length > 1 && apellidos.length > 1) {
            usernamesPosibles.add(nombres[0] + "." + nombres[1].charAt(0) + "." + apellidos[0] + "." + apellidos[1].charAt(0));
        }

        // Número de iteraciones para agregar al username si ya existe
        int iteracion = 1;

        // Username resultante del proceso
        String username = null;

        // Busca hasta encontrar un username disponible
        while (username == null) {
            // Itera sobre las combinaciones posibles
            for (String usernamePosible : usernamesPosibles) {
                // Sí pasó la primera iteración, agrega el número de iteraciones al username.
                // Ejemplo: juan.perez.2 o juan.perez.3
                if (iteracion > 1) {
                    usernamePosible += "." + iteracion;
                }

                // Si el username posible no existe, lo asigna como resultado
                // y termina el ciclo.
                if (this.daoUsuario.obtenerPorUsuario(usernamePosible) == null) {
                    username = usernamePosible;
                    break;
                }
            }
            iteracion++; // Incrementa el número de iteraciones
        }

        return username; // Retorna el username generado
    }



    private void controlarRestricciones(UsuarioDTO usuarioDTO) throws EntityException {
        if (usuarioDTO.getIdPerfil() == null){
            throw new EntityException("El perfil no puede ser nulo");
        }
        if (usuarioDTO.getEstado() == null){
            throw new EntityException("El estado no puede ser nulo");
        }
        if (usuarioDTO.getEmail() == null){
            throw new EntityException("El email no puede ser nulo");
        }
        if(usuarioDTO.getCedula() == null){
            throw new EntityException("La cédula no puede ser nula");
        }
        if(usuarioDTO.getFechaNacimiento() == null){
            throw new EntityException("La fecha de nacimiento no puede ser nula");
        }
        if(usuarioDTO.getTelefono() == null){
            throw new EntityException("El teléfono no puede ser nulo");
        }
        if(usuarioDTO.getNombre() == null){
            throw new EntityException("El nombre no puede ser nulo");
        }
        if(usuarioDTO.getApellido() == null){
            throw new EntityException("El apellido no puede ser nulo");
        }
        if(usuarioDTO.getNombre().length() < 3){
            throw new EntityException("El nombre debe tener al menos 3 caracteres");
        }
        if(usuarioDTO.getApellido().length() < 3){
            throw new EntityException("El apellido debe tener al menos 3 caracteres");
        }
        if(usuarioDTO.getNombre().length() > 45){
            throw new EntityException("El usuario no puede tener más de 45 caracteres");
        }
        if (usuarioDTO.getApellido().length() > 45){
            throw new EntityException("El apellido no puede tener más de 45 caracteres");
        }
        if(usuarioDTO.getCedula().length() < 8){
            throw new EntityException("La cédula debe tener al menos 8 caracteres");
        }
        if(usuarioDTO.getCedula().length() > 8){
            throw new EntityException("La cédula no puede tener más de 8 caracteres");
        }
        if(usuarioDTO.getTelefono().length() < 8){
            throw new EntityException("El teléfono debe tener al menos 8 caracteres");
        }
        if(usuarioDTO.getTelefono().length() > 12){
            throw new EntityException("El teléfono no puede tener más de 12 caracteres");
        }
        if(usuarioDTO.getEmail().length() > 100){
            throw new EntityException("El email no puede tener más de 100 caracteres");
        }
        if (usuarioDTO.getFechaNacimiento().isAfter(LocalDate.now())){
            throw new EntityException("La fecha de nacimiento no puede ser mayor a la fecha actual");
        }
        if (usuarioDTO.getEmail().length() > 256){
            throw new EntityException("El username no puede tener más de 45 caracteres");
        }

    }

    private void controlarCotrasenia(Usuario usuario, String password) throws EntityException {
        String pass = usuario.getPassword();
        System.out.println(password);
        System.out.println(pass);
        if(!pass.equals(password)){
            throw new EntityException("Contraseña incorrecta");
        }
    }
}