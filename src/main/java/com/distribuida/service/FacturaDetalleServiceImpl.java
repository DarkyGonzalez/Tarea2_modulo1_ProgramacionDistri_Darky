package com.distribuida.service;

import com.distribuida.dao.FacturaDetalleRepository;
import com.distribuida.model.FacturaDetalle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaDetalleServiceImpl implements FacturaDetalleService {

    @Autowired
    private FacturaDetalleRepository facturaDetalleRepository;

    @Override
    public List<FacturaDetalle> findAll() {
        return facturaDetalleRepository.findAll();
    }

    @Override
    public Optional<FacturaDetalle> findOne(int id) {
        return facturaDetalleRepository.findById(id);
    }

    @Override
    public FacturaDetalle save(FacturaDetalle facturaDetalle) {
        return facturaDetalleRepository.save(facturaDetalle);
    }

    @Override
    public FacturaDetalle update(int id, FacturaDetalle facturaDetalle) {
        Optional<FacturaDetalle> facturaDetalleOptional = facturaDetalleRepository.findById(id);
        if (facturaDetalleOptional.isPresent()) {
            FacturaDetalle facturaDetalleExistente = facturaDetalleOptional.get();
            facturaDetalleExistente.setCantidad(facturaDetalle.getCantidad());
            facturaDetalleExistente.setSubtotal(facturaDetalle.getSubtotal());
            facturaDetalleExistente.setFactura(facturaDetalle.getFactura());
            facturaDetalleExistente.setLibro(facturaDetalle.getLibro());
            return facturaDetalleRepository.save(facturaDetalleExistente);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        if (facturaDetalleRepository.existsById(id)) {
            facturaDetalleRepository.deleteById(id);
        }
    }
}

