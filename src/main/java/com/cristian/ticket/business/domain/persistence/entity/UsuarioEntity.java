package com.cristian.ticket.business.domain.persistence.entity;

import com.cristian.ticket.business.domain.persistence.entity.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "usuario")
public class UsuarioEntity extends BaseEntity {

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;
}
