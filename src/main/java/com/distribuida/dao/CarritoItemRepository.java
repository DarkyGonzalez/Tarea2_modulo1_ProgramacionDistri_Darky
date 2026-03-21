package com.distribuida.dao;

import com.distribuida.model.Carrito;
import com.distribuida.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

    Optional<CarritoItem> findByCarritoAndIdLibro(Carrito carrito, int idLibro);

    List<CarritoItem> findByCarrito(Carrito carrito);
}
