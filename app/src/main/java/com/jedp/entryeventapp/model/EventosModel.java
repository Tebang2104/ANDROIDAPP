    package com.jedp.entryeventapp.model;

    import java.io.Serializable;

    public class EventosModel implements Serializable {
        private String tipo_evento,nombre_evento,descripcion,lugar,img_url,fecha;

        private String correo, usuarioId,qrURL;

        private int precio,numero_carrito;

        private double telefono;




        public EventosModel(String tipo_evento, String nombre_evento, String img_url, int precio,String descripcion,String lugar, String fecha,String correo, String usuarioId,String qrURL) {
            this.tipo_evento = tipo_evento;
            this.nombre_evento = nombre_evento;
            this.img_url = img_url;
            this.precio = precio;
            this.descripcion = descripcion;
            this.lugar = lugar;
            this.fecha = fecha;
            this.correo = correo;
            this.usuarioId = usuarioId;
            this.qrURL = qrURL;


        }

        public String getTipo_evento() {
            return tipo_evento;
        }

        public void setTipo_evento(String tipo_evento) {
            this.tipo_evento = tipo_evento;
        }

        public String getNombre_evento() {
            return nombre_evento;
        }

        public void setNombre_evento(String nombre_evento) {
            this.nombre_evento = nombre_evento;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getLugar() {
            return lugar;
        }

        public void setLugar(String lugar) {
            this.lugar = lugar;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public double getTelefono() {
            return telefono;
        }

        public void setTelefono(double telefono) {
            this.telefono = telefono;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public int getNumero_carrito() {
            return numero_carrito;
        }

        public void setNumero_carrito(int numero_carrito) {
            this.numero_carrito = numero_carrito;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            correo = correo;
        }

        public String getUsuarioId() {
            return usuarioId;
        }

        public void setUsuarioId(String usuarioId) {
            this.usuarioId = usuarioId;
        }

        public String getQrURL() {
            return qrURL;
        }

        public void setQrURL(String qrURL) {
            this.qrURL = qrURL;
        }
    }
