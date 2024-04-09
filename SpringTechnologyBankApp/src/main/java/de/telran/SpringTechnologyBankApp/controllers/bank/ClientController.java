package de.telran.SpringTechnologyBankApp.controllers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.client.ClientDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.product.ProductDto;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        ClientDto createdClient = clientService.createClient(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClientById(@Valid
                                                      @PathVariable("id") Long id, @RequestBody ClientDto clientDto) {
        ClientDto updatedClient = clientService.updateClientById(id, clientDto);
        return ResponseEntity.ok(updatedClient);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDto> updateClientByIdPatch(@Valid
                                                           @PathVariable("id") Long id, @RequestBody ClientDto clientDto) {
        return updateClientById(id, clientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteClientById(@Valid @PathVariable("id") Long id) {
        clientService.deleteClientById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", String.format("Клиент с id %d успешно удален", id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/with-more")
    public List<ClientDto> getClientsWithBalanceMoreThan(@Valid
            @RequestParam("balance") BigDecimal balance) {
        return clientService.getAllClientsWhereBalanceMoreThan(balance);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClientDto>> getAllClientsByStatus(@Valid @PathVariable("status") StatusType status) {
        List<ClientDto> clients = clientService.getAllClientsWhereStatusTypeIs(status);
        return ResponseEntity.ok(clients);
    }


}
