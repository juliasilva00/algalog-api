package com.algaworks.algalogapi.controller;

import java.util.List;

import com.algaworks.algalogapi.domain.model.Cliente;
import com.algaworks.algalogapi.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    /*public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    } Uma outra forma de fazer a injeção de dependencias sem usar o Autowired*/

    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findByNomeContaining("a");
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId){
        return clienteRepository.findById(clienteId)
                .map(cliente -> ResponseEntity.ok(cliente))
                .orElse(ResponseEntity.notFound().build());

        /*Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.notFound().build();*/

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid  @RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
        //o request body vai vincular o parametro do metodo ao corpo da requisição, ou seja, o nosso JSON vai ser transformado em um obj JAVA
    }


    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> atualizar(@PathVariable  Long clienteId, @Valid @RequestBody Cliente cliente){

        if(!clienteRepository.existsById(clienteId)){
            return  ResponseEntity.notFound().build();
        }

        //Setamos o id pois ele nao vai no corpo da atualização, por isso vamos atribuir um id para o cliente forçando uma atualização
        // o JPA é intelegente para ver que se ja tem um ID nao vamos criar um novo recurso e sim autaliza-lo
        cliente.setId(clienteId);
        cliente = clienteRepository.save(cliente);

        return  ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> remover(@PathVariable  Long clienteId){
        if(!clienteRepository.existsById(clienteId)){
            return  ResponseEntity.notFound().build();
        }

        clienteRepository.deleteById(clienteId);

        return ResponseEntity.noContent().build();
    }
}
