package com.cristian.ticket.business.domain.persistence.entity;

import com.cristian.ticket.business.domain.persistence.entity.base.BaseEntity;
import com.cristian.ticket.business.domain.util.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class TicketEntity extends BaseEntity {
    
    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Long usuarioId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
