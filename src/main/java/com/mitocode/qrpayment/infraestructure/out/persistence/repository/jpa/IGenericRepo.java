package com.mitocode.qrpayment.infraestructure.out.persistence.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
//T se pasa desde los repos pero no tiene un @entity y no se puede crear el bean, jpa no sabe como tarbajarlo
//se agrega los siguiente
//si se cambia de jpa a mongo u otro gesto de bd se cambia solo aqui
@NoRepositoryBean
public interface IGenericRepo<T, ID> extends JpaRepository<T, ID> {

}
