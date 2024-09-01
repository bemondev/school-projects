package com.ale.servidorappmobilecoffee;

import com.ale.servidorappmobilecoffee.entity.Producto;
import com.ale.servidorappmobilecoffee.enums.Category;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;

@Path("/product")
public class CafeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public ArrayList<Producto> obtenerTodos() {
        return cargarDatos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filter")
    public ArrayList<Producto> obtenerPorNombre(@QueryParam("name") String name, @QueryParam("category") String category) {
        ArrayList<Producto> productos = new ArrayList<>();
        for (Producto prod : cargarDatos()) {
            boolean matchesName = (name == null || prod.getName().toLowerCase().contains(name.toLowerCase()));
            boolean matchesCategory = (category == null || category.equalsIgnoreCase(prod.getCategoria().toString()));

            if (matchesName && matchesCategory) {
                productos.add(prod);
            }
        }
        return productos;
    }

    public ArrayList<Producto> cargarDatos(){
        Producto coffee1 = new Producto();
        coffee1.setId(1);
        coffee1.setName("Espresso");
        coffee1.setDescription("Intenso café espresso, perfecto para un shot de energía.");
        coffee1.setPrice(2.50);
        coffee1.setUrlImagen("https://i.postimg.cc/rsL80M9K/1633094402-984-front-95017-cafe-espr.webp");
        coffee1.setCategoria(Category.BEBIDA_CALIENTE);

        Producto coffee2 = new Producto();
        coffee2.setId(2);
        coffee2.setName("Latte");
        coffee2.setDescription("Suave y cremoso latte con leche al vapor y un toque de espuma.");
        coffee2.setPrice(3.75);
        coffee2.setUrlImagen("https://i.postimg.cc/fWxW4SwX/Vanilla-Latte-Contact-Shadow-Green.webp");
        coffee2.setCategoria(Category.BEBIDA_CALIENTE);

        Producto coffee3 = new Producto();
        coffee3.setId(3);
        coffee3.setName("Cappuccino");
        coffee3.setDescription("Clásico cappuccino con un equilibrio perfecto entre espresso, leche y espuma.");
        coffee3.setPrice(3.50);
        coffee3.setUrlImagen("https://i.postimg.cc/L5YvgFNg/frothy-coffee-cappuccino-whipped-milk-600nw-2365979437.webp");
        coffee3.setCategoria(Category.BEBIDA_CALIENTE);

        Producto coffee4 = new Producto();
        coffee4.setId(4);
        coffee4.setName("Mocha");
        coffee4.setDescription("Delicioso mocha con un toque de chocolate, espresso y leche.");
        coffee4.setPrice(4.00);
        coffee4.setUrlImagen("https://i.postimg.cc/7hMqSdBX/Nes-Web3-Article-Header-Mocha-1448x1240.webp");
        coffee4.setCategoria(Category.BEBIDA_CALIENTE);

        Producto coffee5 = new Producto();
        coffee5.setId(5);
        coffee5.setName("Americano");
        coffee5.setDescription("Café americano, suave y ligero, preparado con agua caliente sobre espresso.");
        coffee5.setPrice(2.75);
        coffee5.setUrlImagen("https://i.postimg.cc/K8c8DchT/q-Vy-Dqgrl6x.jpg");
        coffee5.setCategoria(Category.BEBIDA_CALIENTE);

        Producto coffee6 = new Producto();
        coffee6.setId(6);
        coffee6.setName("Café con Leche");
        coffee6.setDescription("Café suave mezclado con leche caliente.");
        coffee6.setPrice(3.00);
        coffee6.setUrlImagen("https://i.postimg.cc/JzT46wR2/XYR7-MYDFHRADVFO7-YD3-GHG7-QHU.avif");
        coffee6.setCategoria(Category.BEBIDA_CALIENTE);

        Producto postre1 = new Producto();
        postre1.setId(7);
        postre1.setName("Cheesecake");
        postre1.setDescription("Delicioso cheesecake con una base crujiente de galleta y un suave relleno de queso crema.");
        postre1.setPrice(4.50);
        postre1.setUrlImagen("https://i.postimg.cc/fLSLSHng/cheesecake-1-22-500x500.jpg");
        postre1.setCategoria(Category.POSTRE);

        Producto postre2 = new Producto();
        postre2.setId(8);
        postre2.setName("Brownie");
        postre2.setDescription("Brownie de chocolate, denso y húmedo, con nueces crujientes.");
        postre2.setPrice(3.00);
        postre2.setUrlImagen("https://i.postimg.cc/nc4X1wsy/como-hacer-brownies-caseros-51664-600-square.jpg");
        postre2.setCategoria(Category.POSTRE);

        Producto postre3 = new Producto();
        postre3.setId(9);
        postre3.setName("Tarta de Manzana");
        postre3.setDescription("Clásica tarta de manzana con un toque de canela y una base crujiente.");
        postre3.setPrice(4.00);
        postre3.setUrlImagen("https://i.postimg.cc/NFh061QG/bizcocho-de-manzana-y-crema-62414859-1200x1200.jpg");
        postre3.setCategoria(Category.POSTRE);

        Producto snack1 = new Producto();
        snack1.setId(10);
        snack1.setName("Croissant");
        snack1.setDescription("Crujiente croissant de mantequilla, perfecto para acompañar un café.");
        snack1.setPrice(2.25);
        snack1.setUrlImagen("https://i.postimg.cc/YS9jMrX7/Grandcroissant2140.png");
        snack1.setCategoria(Category.SNACK);

        Producto snack2 = new Producto();
        snack2.setId(11);
        snack2.setName("Panini");
        snack2.setDescription("Panini caliente relleno de jamón, queso y tomate.");
        snack2.setPrice(5.00);
        snack2.setUrlImagen("https://i.postimg.cc/QxGB1rMg/Panini-casero-shutterstock-793084687-500x500.jpg");
        snack2.setCategoria(Category.SNACK);

        Producto snack3 = new Producto();
        snack3.setId(12);
        snack3.setName("Muffin");
        snack3.setDescription("Esponjoso muffin con chispas de chocolate, ideal para acompañar una bebida.");
        snack3.setPrice(2.75);
        snack3.setUrlImagen("https://i.postimg.cc/Zn3C2wwy/images.jpg");
        snack3.setCategoria(Category.SNACK);

        ArrayList list = new ArrayList<Producto>();
        list.add(coffee1);
        list.add(coffee2);
        list.add(coffee3);
        list.add(coffee4);
        list.add(coffee5);
        list.add(coffee6);
        list.add(postre1);
        list.add(postre2);
        list.add(postre3);
        list.add(snack1);
        list.add(snack2);
        list.add(snack3);

        return list;
    }

}