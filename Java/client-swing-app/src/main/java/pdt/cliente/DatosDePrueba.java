package pdt.cliente;

import cfc.servidor.DTOs.*;
import cfc.servidor.enumerados.*;
import cfc.servidor.exepciones.EntityException;
import jakarta.ejb.Local;
import org.apache.sshd.common.util.io.IoUtils;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DatosDePrueba {
    public static void main(String[] args) throws EntityException {
        // -- Perfil de prueba --


        PerfilDTO admin = Conexion.rec_perfil.obtenerPorNombre("Administrador");
        if (admin == null) {
            admin = new PerfilDTO();
            admin.setPermisos(
                    List.of(PermisosEnum.INTERVENCIONES, PermisosEnum.EQUIPOS, PermisosEnum.PERFILES, PermisosEnum.USUARIOS,
                            PermisosEnum.TIPOS_EQUIPOS, PermisosEnum.UBICACIONES, PermisosEnum.TIPOS_INTERVENCIONES, PermisosEnum.MARCAS,
                            PermisosEnum.MODELOS, PermisosEnum.PAISES)
            );
            admin.setNombre("Administrador");
            admin.setEstado(EstadosEnum.ACTIVO);
            admin = Conexion.rec_perfil.registrar(admin);
        }

        PerfilDTO adminaux = Conexion.rec_perfil.obtenerPorNombre("Auxiliar_Administrativo");
        if (adminaux == null) {
            adminaux = new PerfilDTO();
            adminaux.setPermisos(
                    List.of(PermisosEnum.INTERVENCIONES, PermisosEnum.EQUIPOS, PermisosEnum.PERFILES, PermisosEnum.USUARIOS,
                            PermisosEnum.TIPOS_EQUIPOS, PermisosEnum.UBICACIONES, PermisosEnum.TIPOS_INTERVENCIONES, PermisosEnum.MARCAS,
                            PermisosEnum.MODELOS, PermisosEnum.PAISES)
            );
            adminaux.setNombre("Auxiliar_Administrativo");
            adminaux.setEstado(EstadosEnum.ACTIVO);
            adminaux = Conexion.rec_perfil.registrar(adminaux);
        }

        PerfilDTO ingbio = Conexion.rec_perfil.obtenerPorNombre("Ingeniero-Biomédico");
        if (ingbio == null) {
            ingbio = new PerfilDTO();
            ingbio.setPermisos(
                    List.of(PermisosEnum.INTERVENCIONES, PermisosEnum.EQUIPOS, PermisosEnum.UBICACIONES, PermisosEnum.TIPOS_INTERVENCIONES)
            );
            ingbio.setNombre("Ingeniero-Biomédico");
            ingbio.setEstado(EstadosEnum.ACTIVO);
            ingbio = Conexion.rec_perfil.registrar(ingbio);
        }

        PerfilDTO tec = Conexion.rec_perfil.obtenerPorNombre("Técnico");
        if (tec == null) {
            tec = new PerfilDTO();
            tec.setPermisos(
                    List.of(PermisosEnum.INTERVENCIONES, PermisosEnum.EQUIPOS, PermisosEnum.UBICACIONES, PermisosEnum.TIPOS_INTERVENCIONES)
            );
            tec.setNombre("Técnico");
            tec.setEstado(EstadosEnum.ACTIVO);
            tec = Conexion.rec_perfil.registrar(tec);
        }


        // -- Usuario de prueba --
        UsuarioDTO usuario = Conexion.rec_usuario.obtenerPorUsername("adm.adm");
        if (usuario == null) {
            usuario = new UsuarioDTO();
            usuario.setNombre("adm");
            usuario.setApellido("adm");
            usuario.setTelefono("123456789");
            usuario.setIdPerfil(admin.getId());
            usuario.setCedula("55031234");
            usuario.setFechaNacimiento(1992, 12, 10);
            usuario.setEstado(EstadosEnum.VALIDADO);
            usuario.setEmail("alexis.borges@estudiantes.utec.edu.com");
            usuario = Conexion.rec_usuario.registrar(usuario, "55031826Marto$");
        }

        UsuarioDTO usuario2 = Conexion.rec_usuario.obtenerPorUsername("lautaro.negro");
        if (usuario2 == null) {
            usuario2 = new UsuarioDTO();
            usuario2.setNombre("lautaro");
            usuario2.setApellido("negro");
            usuario2.setTelefono("098953097");
            usuario2.setIdPerfil(adminaux.getId());
            usuario2.setCedula("55265358");
            usuario2.setFechaNacimiento(2000, 9, 18);
            usuario2.setEstado(EstadosEnum.VALIDADO);
            usuario2.setEmail("lautaro.negro@estudiantes.utec.edu.com");
            usuario2 = Conexion.rec_usuario.registrar(usuario2, "55265358Demon$");
        }

        UsuarioDTO usuario3 = Conexion.rec_usuario.obtenerPorUsername("bernardo.montaña");
        if (usuario3 == null) {
            usuario3 = new UsuarioDTO();
            usuario3.setNombre("bernardo");
            usuario3.setApellido("montaña");
            usuario3.setTelefono("099771992");
            usuario3.setIdPerfil(ingbio.getId());
            usuario3.setCedula("54445552");
            usuario3.setFechaNacimiento(1997, 2, 9);
            usuario3.setEstado(EstadosEnum.VALIDADO);
            usuario3.setEmail("bernardo.montaña@estudiantes.utec.edu.com");
            usuario3 = Conexion.rec_usuario.registrar(usuario3, "54445552Bemon$");
        }

        UsuarioDTO usuario4 = Conexion.rec_usuario.obtenerPorUsername("denis.mendez");
        if (usuario4 == null) {
            usuario4 = new UsuarioDTO();
            usuario4.setNombre("denis");
            usuario4.setApellido("mendez");
            usuario4.setTelefono("091616232");
            usuario4.setIdPerfil(tec.getId());
            usuario4.setCedula("59198335");
            usuario4.setFechaNacimiento(2001, 8, 14);
            usuario4.setEstado(EstadosEnum.VALIDADO);
            usuario4.setEmail("denis.mendez@estudiantes.utec.edu.com");
            usuario4 = Conexion.rec_usuario.registrar(usuario4, "59198335Tacua$");
        }



        // -- Ubicación de prueba --
        UbicacionDTO ubicacion = Conexion.rec_ubicacion.obtenerPorNombre("Ala médica");

        if (ubicacion == null) {
            ubicacion = new UbicacionDTO();
            ubicacion.setPiso("1");
            ubicacion.setSector(SectoresEnum.OTRO);
            ubicacion.setNombre("Ala médica");
            ubicacion.setEstado(EstadosEnum.ACTIVO);
            ubicacion.setCama("");
            ubicacion.setNumero(2);
            ubicacion.setInstitucion(InstitucionesEnum.CMH);
            ubicacion = Conexion.rec_ubicacion.registrar(ubicacion);
        }

        UbicacionDTO ubicacion2 = Conexion.rec_ubicacion.obtenerPorNombre("Sala de Espera");

        if (ubicacion2 == null) {
            ubicacion2 = new UbicacionDTO();
            ubicacion2.setPiso("3");
            ubicacion2.setSector(SectoresEnum.OTRO);
            ubicacion2.setNombre("Sala de Espera");
            ubicacion2.setEstado(EstadosEnum.ACTIVO);
            ubicacion2.setCama("");
            ubicacion2.setNumero(3);
            ubicacion2.setInstitucion(InstitucionesEnum.CMH);
            ubicacion2 = Conexion.rec_ubicacion.registrar(ubicacion2);
        }

        UbicacionDTO ubicacion3 = Conexion.rec_ubicacion.obtenerPorNombre("Sala de Emergencias");

        if (ubicacion3 == null) {
            ubicacion3 = new UbicacionDTO();
            ubicacion3.setPiso("1");
            ubicacion3.setSector(SectoresEnum.EMERGENCIA);
            ubicacion3.setNombre("Sala de Emergencias");
            ubicacion3.setEstado(EstadosEnum.ACTIVO);
            ubicacion3.setCama("1A");
            ubicacion3.setNumero(1);
            ubicacion3.setInstitucion(InstitucionesEnum.CMH);
            ubicacion3 = Conexion.rec_ubicacion.registrar(ubicacion3);
        }

        // -- Equipo de prueba --
        EquipoDTO equipo = Conexion.rec_equipo.obtenerPorNombre("Electrocardiograma");

        if (equipo == null) {
            equipo = new EquipoDTO();
            equipo.setIdInterna(2);
            equipo.setNombre("Electrocardiograma");
            equipo.setNumeroSerie("7665");
            LocalDate fechaActual = LocalDate.now();
            equipo.setFechaAdquisicion(fechaActual);
            equipo.setGarantia("199924");
            equipo.setProveedor("Neomed");

            // -- Tipo de equipo de prueba --
            TipoEquipoDTO tipoEquipo = Conexion.rec_tipoEquipo.obtenerPorNombre("Electronico");
            if (tipoEquipo == null) {
                tipoEquipo = new TipoEquipoDTO();
                tipoEquipo.setNombre("Electronico");
                tipoEquipo.setEstado(EstadosEnum.ACTIVO);
                tipoEquipo = Conexion.rec_tipoEquipo.registrar(tipoEquipo);
            }
            equipo.setIdTipoEquipo(tipoEquipo.getId());


            // -- Imagen de prueba --
            ImagenDTO imagen = Conexion.rec_imagen.obtenerPorNombre("https://i.postimg.cc/vHsPP1NC/nachocorazon.png");
            if (imagen == null) {
                imagen = new ImagenDTO();
                try {
                    //Esta ruta debe ser absoluta y cambia dependiendo de en donde este el servidor, en caso de error, referenciar a una imagen local
//                    FileInputStream fn = new FileInputStream("C:\\Users\\ale\\Downloads\\f1.jpg");
                    imagen.setUrl("https://i.postimg.cc/vHsPP1NC/nachocorazon.png");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imagen.setNombre("https://i.postimg.cc/vHsPP1NC/nachocorazon.png");
                imagen = Conexion.rec_imagen.registrar(imagen);
            }
            equipo.setIdImagen(imagen.getId());

            // -- Marca de prueba1 --
            MarcaDTO marca = Conexion.rec_marca.obtenerPorNombre("Neomed");
            if (marca == null) {
                marca = new MarcaDTO();
                marca.setNombre("Neomed");
                marca.setEstado(EstadosEnum.ACTIVO);
                marca = Conexion.rec_marca.registrar(marca);
            }

            // -- Modelo de prueba1 --
            ModeloDTO modelo = Conexion.rec_modelo.obtenerPorNombre("E773");
            if (modelo == null) {
                modelo = new ModeloDTO();
                modelo.setEstado(EstadosEnum.ACTIVO);
                modelo.setNombre("E773");
                modelo.setIdMarca(marca.getId());
                modelo = Conexion.rec_modelo.registrar(modelo);
            }


            equipo.setIdModeloEquipo(modelo.getId());

            equipo.setEstado(EstadosEnum.ACTIVO);
            equipo.setIdUbicacion(ubicacion.getId());

            // -- País de prueba --
            PaisDTO pais = Conexion.rec_pais.obtenerPorNombre("Rusia");
            if (pais == null) {
                pais = new PaisDTO();
                pais.setNombre("Rusia");
                pais.setEstado(EstadosEnum.ACTIVO);
                pais = Conexion.rec_pais.registrar(pais);
            }
            equipo.setIdPaisDeOrigen(pais.getId());
            try {
                equipo = Conexion.rec_equipo.registrar(equipo);
            } catch (EntityException e) {
                e.printStackTrace();
            }
        }

        EquipoDTO equipo2 = Conexion.rec_equipo.obtenerPorNombre("Pinza");

        if (equipo2 == null) {
            equipo2 = new EquipoDTO();
            equipo2.setIdInterna(3);
            equipo2.setNombre("Pinza");
            equipo2.setNumeroSerie("1221");
            LocalDate fechaActual = LocalDate.now();
            equipo2.setFechaAdquisicion(fechaActual);
            equipo2.setGarantia("202762");
            equipo2.setProveedor("Linkon");

            // -- Tipo de equipo de prueba --
            TipoEquipoDTO tipoEquipo = Conexion.rec_tipoEquipo.obtenerPorNombre("Quirúrgico");
            if (tipoEquipo == null) {
                tipoEquipo = new TipoEquipoDTO();
                tipoEquipo.setNombre("Quirúrgico");
                tipoEquipo.setEstado(EstadosEnum.ACTIVO);
                tipoEquipo = Conexion.rec_tipoEquipo.registrar(tipoEquipo);
            }
            equipo2.setIdTipoEquipo(tipoEquipo.getId());


            // -- Imagen de prueba --
            ImagenDTO imagen = Conexion.rec_imagen.obtenerPorNombre("https://i.postimg.cc/FHPDsTHQ/f1.jpg");
            if (imagen == null) {
                imagen = new ImagenDTO();
                try {
                    //Esta ruta debe ser absoluta y cambia dependiendo de en donde este el servidor, en caso de error, referenciar a una imagen local
//                    FileInputStream fn = new FileInputStream("C:\\Users\\ale\\Downloads\\f1.jpg");
                    imagen.setUrl("https://i.postimg.cc/FHPDsTHQ/f1.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imagen.setNombre("https://i.postimg.cc/FHPDsTHQ/f1.jpg");
                imagen = Conexion.rec_imagen.registrar(imagen);
            }
            equipo2.setIdImagen(imagen.getId());


            // -- Marca de prueba2 --
            MarcaDTO marca2 = Conexion.rec_marca.obtenerPorNombre("Sanyfico");
            if (marca2 == null) {
                marca2 = new MarcaDTO();
                marca2.setNombre("Sanyfico");
                marca2.setEstado(EstadosEnum.ACTIVO);
                marca2 = Conexion.rec_marca.registrar(marca2);
            }

            // -- Modelo de prueba2 --
            ModeloDTO modelo2 = Conexion.rec_modelo.obtenerPorNombre("RF4111");
            if (modelo2 == null) {
                modelo2 = new ModeloDTO();
                modelo2.setEstado(EstadosEnum.ACTIVO);
                modelo2.setNombre("RF4111");
                modelo2.setIdMarca(marca2.getId());
                modelo2 = Conexion.rec_modelo.registrar(modelo2);
            }


            equipo2.setIdModeloEquipo(modelo2.getId());

            equipo2.setEstado(EstadosEnum.ACTIVO);
            equipo2.setIdUbicacion(ubicacion.getId());

            // -- País de prueba --
            PaisDTO pais = Conexion.rec_pais.obtenerPorNombre("Alemania");
            if (pais == null) {
                pais = new PaisDTO();
                pais.setNombre("Alemania");
                pais.setEstado(EstadosEnum.ACTIVO);
                pais = Conexion.rec_pais.registrar(pais);
            }
            equipo2.setIdPaisDeOrigen(pais.getId());
            try {
                equipo2 = Conexion.rec_equipo.registrar(equipo2);
            } catch (EntityException e) {
                e.printStackTrace();
            }
        }
        EquipoDTO equipo3 = Conexion.rec_equipo.obtenerPorNombre("Ecocardiograma");

        if (equipo3 == null) {
            equipo3 = new EquipoDTO();
            equipo3.setIdInterna(4);
            equipo3.setNombre("Ecocardiograma");
            equipo3.setNumeroSerie("1551");
            LocalDate fechaActual = LocalDate.now();
            equipo3.setFechaAdquisicion(fechaActual);
            equipo3.setGarantia("204362");
            equipo3.setProveedor("Panavox");

            // -- Tipo de equipo de prueba --
            TipoEquipoDTO tipoEquipo = Conexion.rec_tipoEquipo.obtenerPorNombre("Cardíaco");
            if (tipoEquipo == null) {
                tipoEquipo = new TipoEquipoDTO();
                tipoEquipo.setNombre("Cardíaco");
                tipoEquipo.setEstado(EstadosEnum.ACTIVO);
                tipoEquipo = Conexion.rec_tipoEquipo.registrar(tipoEquipo);
            }
            equipo3.setIdTipoEquipo(tipoEquipo.getId());


            // -- Imagen de prueba --
            ImagenDTO imagen = Conexion.rec_imagen.obtenerPorNombre("https://i.postimg.cc/BnZm0rsg/pp-504x498-pad-600x600-f8f8f8.jpg");
            if (imagen == null) {
                imagen = new ImagenDTO();
                try {
                    //Esta ruta debe ser absoluta y cambia dependiendo de en donde este el servidor, en caso de error, referenciar a una imagen local
//                    FileInputStream fn = new FileInputStream("C:\\Users\\ale\\Downloads\\f1.jpg");
                    imagen.setUrl("https://i.postimg.cc/BnZm0rsg/pp-504x498-pad-600x600-f8f8f8.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                imagen.setNombre("https://i.postimg.cc/BnZm0rsg/pp-504x498-pad-600x600-f8f8f8.jpg");
                imagen = Conexion.rec_imagen.registrar(imagen);
            }
            equipo3.setIdImagen(imagen.getId());


            // -- Marca de prueba3 --
            MarcaDTO marca3 = Conexion.rec_marca.obtenerPorNombre("MediShop");
            if (marca3 == null) {
                marca3 = new MarcaDTO();
                marca3.setNombre("MediShop");
                marca3.setEstado(EstadosEnum.ACTIVO);
                marca3 = Conexion.rec_marca.registrar(marca3);
            }

            // -- Modelo de prueba3 --
            ModeloDTO modelo3 = Conexion.rec_modelo.obtenerPorNombre("Prime22");
            if (modelo3 == null) {
                modelo3 = new ModeloDTO();
                modelo3.setEstado(EstadosEnum.ACTIVO);
                modelo3.setNombre("Prime22");
                modelo3.setIdMarca(marca3.getId());
                modelo3 = Conexion.rec_modelo.registrar(modelo3);
            }


            equipo3.setIdModeloEquipo(modelo3.getId());
            equipo3.setEstado(EstadosEnum.ACTIVO);
            equipo3.setIdUbicacion(ubicacion.getId());

            // -- País de prueba --
            PaisDTO pais = Conexion.rec_pais.obtenerPorNombre("Ucrania");
            if (pais == null) {
                pais = new PaisDTO();
                pais.setNombre("Ucrania");
                pais.setEstado(EstadosEnum.ACTIVO);
                pais = Conexion.rec_pais.registrar(pais);
            }
            equipo3.setIdPaisDeOrigen(pais.getId());
            try {
                equipo3 = Conexion.rec_equipo.registrar(equipo3);
            } catch (EntityException e) {
                e.printStackTrace();
            }
        }
        //rellena los datos de un tipoIntervencionDTO
        TipoIntervencionDTO tipoIntervencionDTO = new TipoIntervencionDTO();
        tipoIntervencionDTO.setNombre("Protocolo de Ingreso");
        tipoIntervencionDTO.setEstado(EstadosEnum.ACTIVO);
        tipoIntervencionDTO = Conexion.rec_tipoIntervencion.registrar(tipoIntervencionDTO);

        //rellena los datos de un tipoIntervencionDTO
        TipoIntervencionDTO tipoIntervencionDTO2 = new TipoIntervencionDTO();
        tipoIntervencionDTO2.setNombre("Protocolo de Mantenimiento");
        tipoIntervencionDTO2.setEstado(EstadosEnum.ACTIVO);
        tipoIntervencionDTO2 = Conexion.rec_tipoIntervencion.registrar(tipoIntervencionDTO2);

        //rellena los datos de un tipoIntervencionDTO
        TipoIntervencionDTO tipoIntervencionDTO3 = new TipoIntervencionDTO();
        tipoIntervencionDTO3.setNombre("Protocolo de Atencion");
        tipoIntervencionDTO3.setEstado(EstadosEnum.ACTIVO);
        tipoIntervencionDTO3 = Conexion.rec_tipoIntervencion.registrar(tipoIntervencionDTO3);

        //rellena los datos de un tipoIntervencionDTO
        TipoIntervencionDTO tipoIntervencionDTO4 = new TipoIntervencionDTO();
        tipoIntervencionDTO4.setNombre("Protocolo de Reingreso");
        tipoIntervencionDTO4.setEstado(EstadosEnum.ACTIVO);
        tipoIntervencionDTO4 = Conexion.rec_tipoIntervencion.registrar(tipoIntervencionDTO4);

        IntervencionDTO intervencionDTO = new IntervencionDTO();
        intervencionDTO.setIdTipoIntervencion(tipoIntervencionDTO.getId());
        intervencionDTO.setIdEquipoIntervenido(equipo.getId());
        intervencionDTO.setObservacion("Ingresó al complejo dentro de su respectiva caja de envío.");
        LocalDateTime date = LocalDateTime.now();
        intervencionDTO.setFechaYHora(date);
        intervencionDTO.setMotivo(MotivosEnum.REVISION);
        Conexion.rec_intervencion.registrar(intervencionDTO);

        IntervencionDTO intervencionDTO2 = new IntervencionDTO();
        intervencionDTO2.setIdTipoIntervencion(tipoIntervencionDTO2.getId());
        intervencionDTO2.setIdEquipoIntervenido(equipo2.getId());
        intervencionDTO2.setObservacion("Revision de falla, levanta temperatura.");
        date = LocalDateTime.now();
        date.minusDays(2);
        intervencionDTO2.setFechaYHora(date);
        intervencionDTO2.setMotivo(MotivosEnum.REVISION);
        Conexion.rec_intervencion.registrar(intervencionDTO2);

        IntervencionDTO intervencionDTO3 = new IntervencionDTO();
        intervencionDTO3.setIdTipoIntervencion(tipoIntervencionDTO3.getId());
        intervencionDTO3.setIdEquipoIntervenido(equipo2.getId());
        intervencionDTO3.setObservacion("Solucion de Falla.");
        date = LocalDateTime.now();
        date.plusDays(3);
        intervencionDTO3.setFechaYHora(date);
        intervencionDTO3.setMotivo(MotivosEnum.REPARACION);
        Conexion.rec_intervencion.registrar(intervencionDTO3);

        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setIdUbicacion(ubicacion.getId());
        movimientoDTO.setIdEquipo(equipo.getId());
        movimientoDTO.setUsername(usuario.getUsername());
        movimientoDTO.setEstado(EstadosEnum.ACTIVO);
        movimientoDTO.setComentario("Movimiento al segundo piso esquina de la habitacion");
        movimientoDTO.setFechaDelRegistro(date);
        Conexion.rec_movimiento.registrar(movimientoDTO);

        MovimientoDTO movimientoDTO2 = new MovimientoDTO();
        movimientoDTO2.setIdUbicacion(ubicacion.getId());
        movimientoDTO2.setIdEquipo(equipo2.getId());
        movimientoDTO2.setUsername(usuario2.getUsername());
        movimientoDTO2.setEstado(EstadosEnum.ACTIVO);
        movimientoDTO2.setComentario("Movimiento al tercer piso");
        movimientoDTO2.setFechaDelRegistro(date);
        Conexion.rec_movimiento.registrar(movimientoDTO2);

        MovimientoDTO movimientoDTO3 = new MovimientoDTO();
        movimientoDTO3.setIdUbicacion(ubicacion.getId());
        movimientoDTO3.setIdEquipo(equipo3.getId());
        movimientoDTO3.setUsername(usuario2.getUsername());
        movimientoDTO3.setEstado(EstadosEnum.ACTIVO);
        movimientoDTO3.setComentario("Movimiento al primer piso");
        movimientoDTO3.setFechaDelRegistro(date);
        Conexion.rec_movimiento.registrar(movimientoDTO3);

    }
}