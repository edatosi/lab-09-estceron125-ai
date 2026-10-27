package listas;

import java.util.NoSuchElementException;

/**
 * Lista simplemente enlazada genérica.
 *
 * INSTRUCCIONES PARA EL ESTUDIANTE
 * --------------------------------
 * 1. Cada método marcado con "TODO" lanza UnsupportedOperationException.
 *    Reemplace esa línea por su implementación.
 * 2. NO modifique las pruebas (src/test). 
 * 3. Resuelva en orden (Etapa 1, 2, 3...): cada etapa usa las anteriores.
 * 4. Solo se permite usar nodos propios (clase Nodo). No use
 *    java.util.LinkedList ni ArrayList dentro de esta clase.
 */
public class ListaEnlazada<T> {

    /** Primer nodo de la lista (null cuando la lista está vacía). */
    private Nodo<T> cabeza;

    /** Cantidad de elementos. Debe mantenerse actualizada en cada operación. */
    private int cantidad;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cantidad = 0;
    }

    // =====================================================================
    // ETAPA 1. Estado de la lista
    // =====================================================================

    /** @return true si la lista no tiene elementos. */
    public boolean estaVacia() {
        return cantidad == 0;
    }

    /** @return número de elementos de la lista. */
    public int tamanio() {
        return cantidad;
    }

    // =====================================================================
    // ETAPA 2. Agregar en los extremos
    // =====================================================================

    /** Inserta el dato como nuevo primer elemento. */
    public void agregarAlInicio(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.setSiguiente(cabeza);
        cabeza = nuevoNodo;
        cantidad++;
    }

    /** Inserta el dato como nuevo último elemento. */
    public void agregarAlFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        cantidad++;
    }

    // =====================================================================
    // ETAPA 3. Consultar elementos
    // =====================================================================

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        return cabeza.getDato();
    }

    /** @throws NoSuchElementException si la lista está vacía */
    public T obtenerUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    /**
     * @param indice posición (0 = primer elemento)
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    // =====================================================================
    // ETAPA 4. Búsqueda
    // =====================================================================

    /** @return true si algún elemento es igual (equals) al dato. Admite null. */
    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if ((dato == null && actual.getDato() == null) || 
                (dato != null && dato.equals(actual.getDato()))) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /** @return posición de la primera aparición del dato, o -1 si no está. */
    public int indiceDe(T dato) {
        Nodo<T> actual = cabeza;
        int indice = 0;
        while (actual != null) {
            if ((dato == null && actual.getDato() == null) || 
                (dato != null && dato.equals(actual.getDato()))) {
                return indice;
            }
            actual = actual.getSiguiente();
            indice++;
        }
        return -1;
    }

    // =====================================================================
    // ETAPA 5. Insertar y reemplazar en cualquier posición
    // =====================================================================

    /**
     * Inserta el dato en la posición indicada, desplazando los siguientes.
     * Se permite indice == tamanio() (equivale a agregar al final).
     *
     * @throws IndexOutOfBoundsException si indice < 0 o indice > tamanio()
     */
    public void insertar(int indice, T dato) {
        if (indice < 0 || indice > tamanio()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        if (indice == 0) {
            agregarAlInicio(dato);
        } else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < indice - 1; i++) {
                actual = actual.getSiguiente();
            }
            Nodo<T> nuevoNodo = new Nodo<>(dato);
            nuevoNodo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevoNodo);
            cantidad++;
        }
    }

    /**
     * Reemplaza el dato de la posición indicada.
     *
     * @return el dato que había antes
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T reemplazar(int indice, T dato) {
        if (indice < 0 || indice >= tamanio()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        T datoAnterior = actual.getDato();
        actual.setDato(dato);
        return datoAnterior;
    }

    // =====================================================================
    // ETAPA 6. Eliminar
    // =====================================================================

    /**
     * Elimina y retorna el primer elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        T datoEliminado = cabeza.getDato();
        cabeza = cabeza.getSiguiente();
        cantidad--;
        return datoEliminado;
    }

    /**
     * Elimina y retorna el último elemento.
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        if (cantidad == 1) {
            return eliminarPrimero();
        }
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente().getSiguiente() != null) {
            actual = actual.getSiguiente();
        }
        T datoEliminado = actual.getSiguiente().getDato();
        actual.setSiguiente(null);
        cantidad--;
        return datoEliminado;
    }

    /**
     * Elimina y retorna el elemento de la posición indicada.
     * @throws IndexOutOfBoundsException si indice < 0 o indice >= tamanio()
     */
    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamanio()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
        if (indice == 0) {
            return eliminarPrimero();
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice - 1; i++) {
            actual = actual.getSiguiente();
        }
        T datoEliminado = actual.getSiguiente().getDato();
        actual.setSiguiente(actual.getSiguiente().getSiguiente());
        cantidad--;
        return datoEliminado;
    }

    /**
     * Elimina la primera aparición del dato.
     * @return true si se eliminó algo, false si el dato no estaba
     */
    public boolean eliminarValor(T dato) {
        if (estaVacia()) {
            return false;
        }
        if ((dato == null && cabeza.getDato() == null) || 
            (dato != null && dato.equals(cabeza.getDato()))) {
            eliminarPrimero();
            return true;
        }
        Nodo<T> actual = cabeza;
        while (actual.getSiguiente() != null) {
            if ((dato == null && actual.getSiguiente().getDato() == null) || 
                (dato != null && dato.equals(actual.getSiguiente().getDato()))) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                cantidad--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /** Deja la lista vacía. */
    public void vaciar() {
        cabeza = null;
        cantidad = 0;
    }

    // =====================================================================
    // ETAPA 7. Representación en texto
    // =====================================================================

    /**
     * Formato: "[1, 2, 3]". Lista vacía: "[]".
     * Un elemento null se imprime como "null".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Nodo<T> actual = cabeza;
        while (actual != null) {
            sb.append(actual.getDato());
            if (actual.getSiguiente() != null) {
                sb.append(", ");
            }
            actual = actual.getSiguiente();
        }
        sb.append("]");
        return sb.toString();
    }

    // =====================================================================
    // ETAPA 8. Retos
    // =====================================================================

    /**
     * Invierte la lista EN SITIO: no cree nodos nuevos, solo cambie
     * las referencias 'siguiente' de los nodos existentes.
     */
    public void invertir() {
        Nodo<T> anterior = null;
        Nodo<T> actual = cabeza;

        while (actual != null) {
            Nodo<T> siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }

        cabeza = anterior;
    }

    /**
     * Retorna el elemento del medio. Si el tamaño es par, el segundo de los
     * dos centrales (ej.: [1,2,3,4] -> 3). Pista: dos punteros, uno avanza
     * de a 1 y otro de a 2.
     *
     * @throws NoSuchElementException si la lista está vacía
     */
    public T obtenerMedio() {
        if (estaVacia()) {
            throw new NoSuchElementException("La lista está vacía");
        }
        Nodo<T> lento = cabeza;
        Nodo<T> rapido = cabeza;
        while (rapido != null && rapido.getSiguiente() != null) {
            lento = lento.getSiguiente();
            rapido = rapido.getSiguiente().getSiguiente();
        }
        return lento.getDato();
    }

    /**
     * Elimina los elementos repetidos dejando solo la primera aparición
     * de cada uno, conservando el orden. Ej.: [1,2,1,3,2] -> [1,2,3]
     */
    public void eliminarDuplicados() {
        if (estaVacia()) {
            return;
        }

        Nodo<T> actual = cabeza;
        while (actual != null) {
            Nodo<T> previo = actual;
            Nodo<T> buscador = actual.getSiguiente();

            while (buscador != null) {
                boolean esDuplicado = (actual.getDato() == null && buscador.getDato() == null)
                    || (actual.getDato() != null && actual.getDato().equals(buscador.getDato()));

                if (esDuplicado) {
                    previo.setSiguiente(buscador.getSiguiente());
                    cantidad--;
                    buscador = previo.getSiguiente();
                } else {
                    previo = buscador;
                    buscador = buscador.getSiguiente();
                }
            }

            actual = actual.getSiguiente();
        }
    }
}
