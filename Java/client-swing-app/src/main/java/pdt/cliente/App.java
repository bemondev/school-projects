package pdt.cliente;

import cfc.servidor.DTOs.UsuarioDTO;
import pdt.cliente.swing.login.JFLogin;

public class App {
    private static UsuarioDTO usuarioLogueado;

    public static void main(String[] args) {

        // Mensaje de bienvenida al iniciar la aplicación
        System.out.println("""
                ░█████╗░██╗░░██╗░█████╗░██╗░░░██╗░█████╗░███╗░░██╗███╗░░██╗███████╗
                ██╔══██╗██║░░██║██╔══██╗╚██╗░██╔╝██╔══██╗████╗░██║████╗░██║██╔════╝
                ██║░░╚═╝███████║███████║░╚████╔╝░███████║██╔██╗██║██╔██╗██║█████╗░░
                ██║░░██╗██╔══██║██╔══██║░░╚██╔╝░░██╔══██║██║╚████║██║╚████║██╔══╝░░
                ╚█████╔╝██║░░██║██║░░██║░░░██║░░░██║░░██║██║░╚███║██║░╚███║███████╗
                ░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚══╝╚═╝░░╚══╝╚══════╝
                               
                ███████╗░█████╗░███╗░░██╗  ░█████╗░██╗░░░░░██╗░░░██╗██████╗░
                ██╔════╝██╔══██╗████╗░██║  ██╔══██╗██║░░░░░██║░░░██║██╔══██╗
                █████╗░░███████║██╔██╗██║  ██║░░╚═╝██║░░░░░██║░░░██║██████╦╝
                ██╔══╝░░██╔══██║██║╚████║  ██║░░██╗██║░░░░░██║░░░██║██╔══██╗
                ██║░░░░░██║░░██║██║░╚███║  ╚█████╔╝███████╗╚██████╔╝██████╦╝
                ╚═╝░░░░░╚═╝░░╚═╝╚═╝░░╚══╝  ░╚════╝░╚══════╝░╚═════╝░╚═════╝░""");

        // Inicia la ventana de inicio de sesión
        JFLogin.getInstancia().setVisible(true); // Hace visible la ventana
    }

    public static UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public static void setUsuarioLogueado(UsuarioDTO usuarioLogueado) {
        App.usuarioLogueado = usuarioLogueado;
    }
}