package Interfaces;

import java.util.List;

public interface CRUD<T> {

    int agregar(T obj);

    int actualizar(T obj);

    int eliminar(int id);

    T buscarPorId(int id);

    List<T> listarTodos();
}
