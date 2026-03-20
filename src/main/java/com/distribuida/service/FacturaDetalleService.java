package com.distribuida.service;

import com.distribuida.model.FacturaDetalle;

import java.util.List;
import java.util.Optional;

public interface FacturaDetalleService {

    public List<FacturaDetalle> findAll();

    public Optional<FacturaDetalle> findOne(int id);

    public FacturaDetalle save(FacturaDetalle facturaDetalle);

    public FacturaDetalle update(int id, FacturaDetalle facturaDetalle);

    public void delete(int id);

}

