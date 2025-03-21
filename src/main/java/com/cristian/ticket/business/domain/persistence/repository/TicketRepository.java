package com.cristian.ticket.business.domain.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cristian.ticket.business.domain.persistence.entity.TicketEntity;
import com.cristian.ticket.business.domain.util.Status;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query("SELECT t FROM TicketEntity t WHERE (:usuarioId IS NULL OR t.usuarioId = :usuarioId) AND (:status IS NULL OR t.status = :status)")
    List<TicketEntity> findByUsuarioIdOrStatus(@Param("usuarioId") Long usuarioId, @Param("status") Status status);
}
