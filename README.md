
# Proyecto de Arquitectura Hexagonal - Documentación Detallada

## 📌 Introducción
Este proyecto implementa la **Arquitectura Hexagonal** (también conocida como Ports & Adapters)
en un sistema de gestión de comercios. La arquitectura hexagonal busca desacoplar la lógica de negocio
del resto de las capas (infraestructura, UI, frameworks, etc.), permitiendo que el dominio sea independiente
y testeable.

---

## 🎯 Objetivos del Proyecto
1. Mantener **independencia del dominio**.
2. Facilitar pruebas unitarias e integración.
3. Permitir intercambiar tecnologías de infraestructura sin afectar la lógica de negocio.
4. Mejorar la **mantenibilidad** y **escalabilidad**.
5. Exponer un API REST documentada con **OpenAPI/Swagger**.

---

## 🏗️ Estructura de Carpetas

```
src/main/java/com/miempresa/miapp/
│
├── application      # Casos de uso y orquestación de lógica de negocio
├── domain           # Entidades, VO, interfaces de repositorio y lógica de negocio pura
├── infrastructure   # Adaptadores a tecnologías externas (DB, APIs, mensajería)
```

---

## 📚 Conceptos Clave

### 1. **Domain (Dominio)**
- **Entities**: Objetos con identidad propia que representan conceptos de negocio (Ej: `Merchant`).
- **Value Objects (VO)**: Objetos sin identidad, definidos por sus valores (Ej: `Email`, `Address`).
- **Puertos**: Contratos para persistencia, apis,etc. Independientes de la tecnología.

### 2. **Application**
- **Use Cases / Commands**: Orquestan las operaciones de negocio, llamando a repositorios y servicios externos.
- **DTO (Data Transfer Object)**: Objetos simples para transportar datos entre capas.
- **Command Objects**: Representan instrucciones específicas para ejecutar un caso de uso.

### 3. **Infrastructure**
- **Adaptadores**: Implementaciones de los puertos(interfaces), como son los casos de los puertos repositorio o conectores externos.
- **Mappers**: Transforman de una clase a otra clase, como por ejemplo: DTO ↔ Entity.
- **Controladores REST (Adaptador in)** : Exponen endpoints para clientes externos.

---

## ⚠️ Manejo de Excepciones y Códigos de Error

Se utiliza un **ControllerAdvice** global para manejar todas las excepciones.  
Cada error tiene un **código personalizado**:



<table>
  <tr><th>Http Status Code</th><th>HTTP Status</th><th>Code Response</th><th>Descripción</th></tr>
  <tr><td>400</td><td>BAD_REQUEST</td><td>01</td><td>Datos inválidos</td></tr>
  <tr><td>401</td><td>UNAUTHORIZED</td><td>02</td><td>No autorizado</td></tr>
  <tr><td>404</td><td>NOT_FOUND</td><td>03</td><td>Recurso no encontrado</td></tr>
  <tr><td>404</td><td>NOT_FOUND</td><td>04</td><td>Valor no encontrado en el dominio</td></tr>
  <tr><td>500</td><td>INTERNAL_SERVER_ERROR</td><td>05</td><td>Error interno de infraestructura</td></tr>
</table>

Ejemplo de respuesta:
```json
{
  "codigo": "01",
  "mensaje": "Formato de email inválido",
  "detalle": "El email ingresado no cumple el patrón esperado"
}
```

---

## 🧪 Creación de Unit Tests (Paso a Paso con Explicación Detallada)

En este apartado se documenta, de forma exhaustiva, cómo se crean las pruebas unitarias (Unit Tests) siguiendo buenas prácticas,
usando JUnit 5 y Mockito, y aplicando el patrón BDD (Behavior Driven Development) cuando corresponda.

---

### 1️⃣ Paso 1: Identificar el caso de uso a probar
Antes de escribir un test, debemos identificar **qué comportamiento** queremos validar.

Ejemplo: `CreateMerchantUseCase` es el caso de uso que gestiona la creación de un nuevo comercio (Merchant).  
Nuestro objetivo en la prueba es:
- Asegurarnos de que cuando se envía un comando válido, el comercio se guarda correctamente.
- Verificar que cuando hay datos inválidos, se lanza la excepción correspondiente.

---

### 2️⃣ Paso 2: Mockear dependencias con Mockito
Cuando un caso de uso depende de otras clases (por ejemplo, repositorios, servicios externos, adaptadores),  
se utilizan *mocks* para simular su comportamiento y aislar la lógica interna.

Se hace así:
```java
@Mock
private MerchantRepository repository;
```

Esto evita acceder a bases de datos reales o servicios externos durante el test.

---

### 3️⃣ Paso 3: Configurar el escenario

En Mockito:
- `when(...)` → se usa en estilo clásico.

Ejemplo:
```java
when(repository.save(any())).return(new Merchant("Axel Store", "axel@mail.com"));
```
Esto significa: *Dado que* cuando se llame a `repository.save(...)` con cualquier objeto, devolverá el objeto configurado.



---

### 4️⃣ Paso 4: Ejecutar el caso de uso
Este es el momento de invocar la lógica a probar.

Ejemplo:
```java
Merchant result = useCase.execute(command);
```
Aquí:
- `command` es un DTO o Command con los datos de entrada.
- `useCase.handle(command)` es la ejecución real del flujo.

---

### 5️⃣ Paso 5: Verificar resultados y comportamientos
En Mockito, el método `verify(...)` asegura que un método fue llamado **exactamente** un número de veces esperado.

Ejemplo:
```java
verify(repository, times(1)).save(any());
```
Esto significa: "Verifica que el método `save` del repositorio fue llamado **exactamente una vez** con cualquier objeto".

También verificamos valores con **asserts**:
```java
assertEquals("Axel Store", result.getName());
```
Esto asegura que el nombre devuelto es el esperado.

---

### 6️⃣ Paso 6: Probar casos positivos y negativos
Es importante cubrir:
- **Caso positivo**: flujo normal esperado.
- **Caso negativo**: entradas inválidas, excepciones, errores de negocio.

Ejemplo de caso negativo:
```java
assertThrows(InvalidDataException.class, () -> {
    useCase.execute(new CreateMerchantCommand("", "correo@invalido"));
});
```

---

## 💡 Ejemplo Completo
```java
@Test
void debeCrearMerchantCorrectamente() {
    // Given
    CreateMerchantCommand command = new CreateMerchantCommand("Axel Store", "axel@mail.com");
    when(repository.save(any())).thenReturn(new Merchant("Axel Store", "axel@mail.com"));

    // When
    Merchant result = useCase.execute(command);

    // Then
    assertEquals("Axel Store", result.getName());
    verify(repository, times(1)).save(any());
}

@Test
void debeFallarCuandoDatosSonInvalidos() {
    // Given
    CreateMerchantCommand command = new CreateMerchantCommand("", "correo@invalido");

    // When & Then
    assertThrows(InvalidDataException.class, () -> useCase.execute(command));
    verify(repository, never()).save(any());
}
```

En este ejemplo:
- Se simula el repositorio con `when(...)`.
- Se ejecuta el caso de uso (`useCase.execute(...)`).
- Se validan resultados (`assertEquals`) y comportamientos (`verify(...)`).
- Se cubren casos positivos y negativos.



# 📊 Integración con SonarQube usando Docker

Esta sección describe **cómo integrar un proyecto Java con SonarQube** usando Docker, siguiendo configuraciones estándar adoptadas por muchas empresas para asegurar calidad de código, mantenibilidad y escalabilidad.

---

## 1️⃣ ¿Por qué usar SonarQube?
SonarQube es una herramienta que analiza el código fuente en busca de:
- Errores
- Vulnerabilidades
- Code Smells (malos olores de código)
- Cobertura de tests
- Cumplimiento de estándares de codificación

En entornos empresariales, SonarQube es clave porque:
- Permite definir **Quality Gates** para bloquear el despliegue de código con problemas críticos.
- Centraliza el análisis de calidad de varios proyectos.
- Se integra fácilmente con pipelines CI/CD (Jenkins, GitLab, GitHub Actions, etc.).

---

## 2️⃣ Levantar SonarQube con Docker

**Archivo `docker-compose.yml` estándar:**

```yaml
version: "3"
services:
  sonarqube:
    image: sonarqube:9.9-community
    container_name: sonarqube
    ports:
      - "9000:9000"
    environment:
      - SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
    volumes:
      - sonarqube_data:/opt/sonarqube/data
      - sonarqube_extensions:/opt/sonarqube/extensions
      - sonarqube_logs:/opt/sonarqube/logs

  postgres:
    image: postgres:13
    container_name: sonarqube_db
    environment:
      - POSTGRES_USER=sonar
      - POSTGRES_PASSWORD=sonar
      - POSTGRES_DB=sonarqube
    volumes:
      - postgresql:/var/lib/postgresql

volumes:
  sonarqube_data:
  sonarqube_extensions:
  sonarqube_logs:
  postgresql:
```

🔹 **Razones de esta configuración:**
- `sonarqube:9.9-community` → versión LTS estable recomendada para empresas.
- Uso de **PostgreSQL** (base de datos recomendada por SonarQube).
- Volúmenes para persistir datos y no perder configuración entre reinicios.
- Puerto `9000` expuesto para acceder desde navegador.

---

## 3️⃣ Configuración del proyecto Java para SonarQube

En tu `pom.xml` agrega el plugin oficial de Sonar:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.8</version>
            <executions>
                <execution>
                    <goals>
                        <goal>prepare-agent</goal>
                    </goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals>
                        <goal>report</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>

        <plugin>
            <groupId>org.sonarsource.scanner.maven</groupId>
            <artifactId>sonar-maven-plugin</artifactId>
            <version>3.9.1.2184</version>
        </plugin>
    </plugins>
</build>
```

Archivo `sonar-project.properties` (en la raíz del proyecto):

```properties
sonar.projectKey=mi-proyecto-java
sonar.projectName=Mi Proyecto Java
sonar.projectVersion=1.0
sonar.sourceEncoding=UTF-8

# Directorios de código y tests
sonar.sources=src/main/java
sonar.tests=src/test/java

# Exclusiones (opcional)
sonar.exclusions=**/generated/**,**/dto/**

# Cobertura (usando Jacoco)
sonar.java.coveragePlugin=jacoco
sonar.jacoco.reportPaths=target/site/jacoco/jacoco.xml
```

🔹 **Razones de esta configuración:**
- **`sonar.sources`**: Analiza únicamente el código fuente.
- **`sonar.tests`**: Asegura que solo se cuenten tests en métricas de cobertura.
- **Exclusiones**: Evita falsos positivos en código generado o DTOs.
- **Jacoco**: Estándar de cobertura de código en proyectos Java.

---

## 4️⃣ Ejecución del análisis

1. Levantar SonarQube:
   ```bash
   docker-compose up -d
   ```

2. Acceder al dashboard:  
   👉 http://localhost:9000

3. Ingresar a la pestaña de security de Sonar:  
   👉 Ingresar a my account /security

4. Generar token :  
   Ingresar el nombre del token
   Seleccionar User Token
   Seleccionar Date experation
mi caso: squ_de3b128d1d02174a84a796f4fb24755265eb4d5a
5. Compilar y analizar el proyecto:
   ```bash
   mvn clean install
mvn clean verify sonar:sonar -D"sonar.host.url=http://localhost:9000" -D"sonar.login=<TOKEN_GENERADO_EN_SONAR>"

   ```



---

## 5️⃣ Buenas prácticas adoptadas en empresas

- **Quality Gate**: Configurar umbrales mínimos (ej. cobertura ≥ 80%, 0 vulnerabilidades críticas).
- **Integración CI/CD**: Ejecutar análisis en cada merge request y bloquear si falla el Quality Gate.
- **Revisiones periódicas**: Revisar métricas mensuales para detectar deuda técnica.

---


---

## 🔄 Cambios Realizados en el Proyecto

### Dominio
- Agregadas validaciones en `Merchant` para evitar estados inválidos.
- Creación de Value Objects para email y dirección.

### Application
- Uso de **Command Objects** para separar entrada de datos de la lógica interna.
- Refactor de casos de uso para cumplir **SRP (Single Responsibility Principle)**.

### Infraestructura
- Mejorado manejo de excepciones en adaptadores.
- Documentación OpenAPI con `@Schema` para `ApiError`.

Ejemplo:
```java
@Schema(name = "ApiError", description = "Estructura de error de la API")
public class ApiError {
    private String codigo;
    private String mensaje;
    private String detalle;
}
```

---

# Integración con Keycloak

Esta sección describe **cómo integrar un proyecto Java con Keycloak** usando Docker, siguiendo configuraciones estándar.

---

## 1️⃣ Installación de Keycloak

1. Levantar Keycloak

docker run -d --name keycloak-dev -p 8383:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:24.0.3 start-dev
1.1crear realm porque ese va en path de la solicitud el token
2. Ingresar Al dashboard de Keycloak
3. Ingresar a Clients
4. Ingresar a Create Client
5. Colar un clientId = Example keycloak-arq-payment
6. Activar flags de Client authorization y Authorization
7. colocar las cabeceras con los valores de clint id genErado y el cliente secret , en mi caso:

	7.1 keycloak-arq-payment que se saca de client/client details/settings/clinet id
	
	7.2  NC6VyzRgl4ljILCRqEVTbz4aXFOp8ql4 que se saca de client/client details/credentials/client secret
---

## 📌 Retos para Mejorar
1. Implementar **reintentos automáticos** en fallos de integración (patrón Retry).
2. Mejorar seguridad usando **JWT y OAuth2**.


---

## 📖 Glosario de Términos
- **Entity**: Objeto con identidad propia persistente.
- **VO (Value Object)**: Objeto sin identidad, inmutable, definido por su valor.
- **DTO**: Objeto para transferir datos entre capas.
- **Command**: Instrucción específica para un caso de uso.
- **ControllerAdvice**: Clase global para manejar excepciones.
- **OpenAPI**: Especificación para documentar APIs REST.
- **SonarQube**: Herramienta para análisis de calidad de código.

---
