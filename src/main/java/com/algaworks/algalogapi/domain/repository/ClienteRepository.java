package com.algaworks.algalogapi.domain.repository;

import com.algaworks.algalogapi.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//gerencia os dados da entidade cliente
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    List<Cliente> findByNome(String nome);
    List<Cliente> findByNomeContaining(String nome);



}
