# qrPayment-arq-hexagonal

# Proyecto QR Payment

#PRIMERA PARTE
Este proyecto está construido en **Java 21** utilizando **Maven** como gestor de dependencias y build system.  
Su propósito es implementar un backend ligero para pagos con QR, basado en especificaciones **Jakarta EE** y librerías auxiliares.

---

## 🚀 Stack Tecnológico

### Lenguaje y Build
- **Java 21**
- **Maven**

### Especificaciones Jakarta EE
- **Jakarta MVC** (`jakarta.mvc-api`) → patrón Modelo-Vista-Controlador
- **Jakarta RESTful Web Services (JAX-RS)** (`jakarta.ws.rs-api`) → endpoints REST
- **Jakarta Servlet API** → manejo de requests/responses HTTP
- **Jakarta XML Bind (JAXB)** → serialización/deserialización XML

### Implementación
- **Jersey** (varios módulos: `jersey-server`, `jersey-container-servlet`, `jersey-container-grizzly2-http`, `jersey-hk2`, `jersey-media-json-jackson`)  
  → implementación de referencia de JAX-RS, con servidor embebido Grizzly e inyección de dependencias HK2.

### JSON y Mapeo de Objetos
- **org.json** → parser simple de JSON
- **Jackson (jackson-datatype-jsr310)** → soporte para tipos de fecha/tiempo de Java

### Generación de QR
- **ZXing (core + javase)** → librería estándar para generar y leer códigos QR

### Base de Datos
- **H2 Database** → base de datos embebida, ideal para pruebas y entornos ligeros

---

## 📖 Clasificación del Proyecto

- **Especificación**: basado en **Jakarta EE APIs** (MVC, JAX-RS, Servlet, JAXB).  
- **Implementación nativa**: usa **Jersey + Grizzly** como runtime, sin frameworks pesados como Spring Boot.  
- **Stack completo**: backend Java EE ligero, con servidor embebido, persistencia en H2, generación de QR y endpoints REST.

---

## ⚙️ En resumen

Este proyecto es una **aplicación Java EE nativa, ligera, basada en especificaciones Jakarta y con Jersey como runtime**, que integra:
- Endpoints REST
- Generación de QR
- Persistencia en H2
- Serialización JSON/XML