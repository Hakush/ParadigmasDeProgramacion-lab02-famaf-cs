---
# title: Laboratorio de Programación Orientada a Objetos
### authors: Facundo Sharry, Alejandro Pitt, Juan Gonzalez
---
# Laboratorio de Programación Orientada a Objetos Grupo 30

Integrantes:

- Facundo Sharry
- Juan Cruz González
- Alejandro Pitt

El enunciado del laboratorio se encuentra en [este link](https://docs.google.com/document/d/1wLhuEOjhdLwgZ4rlW0AftgKD4QIPPx37Dzs--P1gIU4/edit#heading=h.xe9t6iq9fo58).

## 1. Tareas
Pueden usar esta checklist para indicar el avance.

### Verificación de que pueden hacer las cosas.
- [X] Java 17 instalado. Deben poder compilar con `make` y correr con `make run` para obtener el mensaje de ayuda del programa.

### 1.1. Interfaz de usuario
- [X] Estructurar opciones
- [X] Construir el objeto de clase `Config`

### 1.2. FeedParser
- [X] `class Article`
    - [X] Atributos
    - [X] Constructor
    - [X] Método `print`
    - [X] _Accessors_
- [X] `parseXML`

### 1.3. Entidades nombradas
- [X] Pensar estructura y validarla con el docente
- [X] Implementarla
- [X] Extracción
    - [X] Implementación de heurísticas
- [X] Clasificación
    - [X] Por tópicos
    - [X] Por categorías
- Estadísticas
    - [X] Por tópicos
    - [X] Por categorías
    - [X] Impresión de estadísticas

### 1.4 Limpieza de código
- [X] Pasar un formateador de código
- [X] Revisar TODOs

## 2. Experiencia
En nuestro caso este proyecto fue nuestra primera experiencia con programación orientada a objetos y con java. Nos costó un poco al principio pensar la forma en que íbamos a organizar los datos que debíamos guardar para cada entidad que queríamos representar como una clase, pero luego de ver algunos ejemplos y pensar que caracteristicas tenian sentido para representar cada entidad logramos crear clases que nos permitieran instanciar las entidades que encontramos en los artículos. Creemos que hay aspectos que podrían mejorarse para que la ampliación para nuevas categorías sea más simple pero no logramos idear una forma de realizarlo, creemos que este es un aspecto que podríamos trabajar para mejorar en el paradigma ya que entendemos que la ampliación y extensión de lo que podemos representar como objetos la clave de la programación orientada a objetos. Creemos que logramos el objetivo propuesto en la consigna de forma modular y con las clases necesarias para la representación de los objetos que necesitamos durante el desarrollo.  

## 3. Preguntas
1. Explicar brevemente la estructura de datos elegida para las entidades nombradas.
Nuestra clase madre de todas las entidades nombradas es NamedEntity, en la cual incluimos los atributos ‘canonicalName’ para el nombre de la entidad, ‘topics’ como una lista para los topicos/etiquetas que tenga la entidad, ‘counter’ como contador de ocurrencias de dicha entidad, también puede usarse como indicador de relevancia de la entidad y por último ‘category’ como indicador de a qué categoría corresponde. Esto con su constructor, sets y gets apropiados. 
Luego pensamos las demás categorías como clases hijas de NamedEntity, ya que todas poseen los atributos de una NamedEntity y además algunos extra. Las clases hijas son Person para instanciar personas, con atributos firstName y lastName ademas de personId, ya que dos personas podrían compartir nombre.
La siguiente clase es Location para instanciar lugares, cuenta con los atributos cannonicalForm, country, latitude y longitude, para caracterizar mejor donde se encuentra, en nuestro caso no los llenamos.    
Además la clase Organization para instanciar las diferentes organizaciones que poseen los atributos canonicalForm, numbreOfEmployees y type.
Por último la clase Other para demás instancias que no encajen en ninguna categoría presente, cuenta con el atributo type.
En las clases donde usamos el atributo cannonicalForm pensamos en esto como una forma independiente de cannonicalName de escribir el nombre, ya que hay diferentes formas de escribir un nombre.

2. Explicar brevemente cómo se implementaron las heurísticas de extracción.
Al principio se pensaron un par de metodos para definir las palabras candidatas de diferentes heuristicas, la "heuristica segura", por ejemplo, toma elementos de la primera heuristica, "heuristica mayuscula" la provista como ejemplo, y elimina las palabras que aparecen una unica vez por articulo para evitar unos cuantos falsos positivos, por eso el nombre. Luego de crear otras diversas heuristicas, nos dimos cuenta que todas usan un mismo método, por lo que decidimos poner en practica lo aprendido en el teorico de la materia y creamos la clase HEURISTIC la cual sirve como interfaz y madre de todas las heuristicas.
Se implementó una clase llamada HeruristicFactory, la cual se usa para nombrar, describir y facilitar la seleccion de la heuristica que quiere aplicar el usuario.
Finalmente se experimentó con heuristicas menos utiles, como LongWord, la cual toma palabras de 7 o mas letras; pues el objetivo no es buscar palabras largas sino palabras claves.

## 4. Extras
Como extra hicimos muchas heuristicas, mas de los que se esperaban en la consigna, algunas simples y otras mas complicadas.