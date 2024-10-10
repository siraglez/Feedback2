# Feedback2
 
Link al repositorio: https://github.com/siraglez/Feedback2.git

# Gestión de Novelas 

Este proyecto es una aplicación de gestión de novelas construida con Jetpack Compose y Firebase. A continuación, se detallan las principales clases y funcionalidades del código.

## Estructura del Proyecto

El proyecto se compone de varias clases, cada una responsable de diferentes aspectos de la aplicación:

### 1. Clase `Novela`

La clase `Novela` representa una novela y contiene los siguientes campos: `titulo`, `autor`, `anioPublicacion`, `sinopsis`, `esFavorita`, y `resenas`. Estos campos almacenan la información esencial de cada novela y permiten gestionar su estado en la aplicación.

### 2. Clase `NovelaViewModel`

La clase `NovelaViewModel` extiende `ViewModel` y es responsable de gestionar el estado de las novelas en la aplicación. Contiene métodos para agregar, eliminar y marcar novelas como favoritas, así como para cargar novelas desde Firebase Firestore. Utiliza una lista mutable de novelas que se actualiza automáticamente cuando se modifican los datos.

### 3. Clase `MainActivity`

La clase `MainActivity` es la clase principal de la aplicación que inicializa la interfaz de usuario mediante el método `setContent`. Carga la función `NovelaApp`, que configura la navegación y el diseño de la aplicación.

### 4. Composables

La función `NovelaApp` configura el controlador de navegación y muestra la interfaz de usuario de la aplicación. Dentro de esta función, se utilizan componentes de Material Design como `Scaffold` y `TopAppBar` para crear una estructura básica de la aplicación. También gestiona el estado de carga al interactuar con Firebase.

La función `NavigationHost` se encarga de definir las diferentes pantallas de la aplicación y la navegación entre ellas. Incluye la pantalla principal donde se muestran las novelas, así como pantallas para agregar novelas y ver detalles.

### 5. Pantallas Principales

La pantalla principal muestra una lista de novelas con opciones para agregar nuevas novelas y eliminarlas. Al pulsar el botón de "Cargar novelas desde la base de datos", se inicia un proceso de carga que muestra un indicador de progreso mientras se recuperan los datos de Firebase.

### 6. Carga de Novelas desde Firebase

La función `cargarNovelas` dentro del `NovelaViewModel` se encarga de acceder a la base de datos de Firebase Firestore y cargar las novelas almacenadas. Los documentos recuperados se convierten en instancias de la clase `Novela` y se agregan a la lista de novelas en memoria.

### 7. Manejo de Estados

El manejo del estado es crucial en la aplicación. Se utilizan variables `mutableStateOf` para controlar la carga de datos y actualizar la interfaz de usuario en consecuencia. Esto garantiza que los cambios en el estado se reflejen de inmediato en la UI.

## Conclusión

Esta aplicación proporciona una interfaz intuitiva para gestionar novelas, aprovechando la potencia de Jetpack Compose y Firebase. Se pueden agregar, eliminar y marcar novelas como favoritas, así como cargar novelas desde la base de datos, todo con una experiencia de usuario fluida y moderna.
