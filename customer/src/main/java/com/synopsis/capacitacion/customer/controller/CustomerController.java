package com.synopsis.capacitacion.customer.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.synopsis.capacitacion.customer.entity.Customer;
import com.synopsis.capacitacion.customer.entity.CustomerProduct;
import com.synopsis.capacitacion.customer.repository.CustomerRepository;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    private final WebClient.Builder webClientBuilder;

    public CustomerController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    HttpClient client = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .responseTimeout(Duration.ofSeconds(1))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    // Listar clientes
    @GetMapping("/listar")
    public List<Customer> list() {
        return customerRepository.findAll();
    }

    // Buscar cliente por id
    @GetMapping("/buscar/{id}")
    public Customer get(@PathVariable(name = "id") long id) {
        return customerRepository.findById(id).get();
    }

    // Nuevo cliente
    @PostMapping("/insertar")
    public ResponseEntity<?> post(@RequestBody Customer input) {

        input.getProducts().forEach(x -> x.setCustomer(input));
        Customer save = customerRepository.save(input);
        return ResponseEntity.ok(save);
    }

    // Modificar datos de cliente
    @PutMapping("/modificar/{id}")
    public ResponseEntity<?> put(@PathVariable(name = "id") long id, @RequestBody Customer input) {
        Customer find = customerRepository.findById(id).get();
        if (find != null) {
            find.setCode(input.getCode());
            find.setName(input.getName());
            find.setIban(input.getIban());
            find.setPhone(input.getPhone());
            find.setSurname(input.getSurname());
        }
        Customer save = customerRepository.save(find);
        return ResponseEntity.ok(save);
    }

    // Borrar datos del cliente
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        Optional<Customer> findById = customerRepository.findById(id);
        if (findById.get() != null) {
            customerRepository.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }

    // Listar transacciones del cliente
    @GetMapping("/listar2/{id}")
    public Customer getFull(@PathVariable(name = "id") long id) {

        Customer customer = customerRepository.findById(id).get();

        List<CustomerProduct> products = customer.getProducts();

        products.forEach(
                product -> {
                    String productName = getProductName(product.getProductId());
                    product.setProductName(productName);
                });
        return customer;
    }

    // Listar transacciones del cliente detallado
    @GetMapping("/listar3/{id}")
    public Customer getFull2(@PathVariable(name = "id") long id) {

        Customer customer = customerRepository.findById(id).get();

        List<CustomerProduct> products = customer.getProducts();

        products.forEach(
                product -> {
                    String productName = getProductName(product.getProductId());
                    product.setProductName(productName);
                });

        List<JsonNode> transactions = getTransactionsByIban(customer.getIban());
        customer.setTransactions(transactions);

        return customer;
    }

    private String getProductName(long id) {

        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://host.docker.internal:8080/ms-product/v1/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://host.docker.internal:8080/ms-product/v1/product"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();

        String name = block.get("name").asText();
        return name;
    }

    private List<JsonNode> getTransactionsByIban(String iban) {

        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://host.docker.internal:8080/ms-transactions/v1/transactions")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://host.docker.internal:8080/ms-transactions/v1/transactions"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/iban" + iban)
                .retrieve().bodyToMono(JsonNode.class).block();

        List<JsonNode> transactions = new ArrayList<>();
        if (block.isArray()) {
            for (JsonNode transaction : block) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    // PROCEDIMIENTOS ALMACENADOS
    //
    // Listar clientes
    // @GetMapping("/SPlistar")
    // public ResponseEntity<List<Customer>> SPlist() {
    // List<Customer> customers = ICustomerService.getAllCustomers();
    // return ResponseEntity.ok(customers);
    // }

    @GetMapping("/SPlistar")
    public ResponseEntity<List<Customer>> SPlist() {
        List<Customer> customers = customerRepository.findAll();
        return ResponseEntity.ok(customers);
    }

    // @GetMapping("/SPlistar")
    // public List<Customer> SPlist() {
    //     return ICustomerService.getAllCustomers();
    // }
    


    // @GetMapping("/listar")
    // public List<Transactions> list() {
    //     return ITransactionsService.getAllTransactions();
    // }
}
