package de.telran.SpringTechnologyBankApp.controllers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDtoForByCondition;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.ManagerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/managers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Manager Controller API")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/add")
    public ResponseEntity<ManagerDto> createManager(@Valid @RequestBody ManagerDto managerDto) {
        ManagerDto createdManager = managerService.createManager(managerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdManager);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getManagerById(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(managerService.getManagerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerDto> updateManagerById(@Valid
                                                        @PathVariable("id") Long id, @RequestBody ManagerDto managerDto) {
        ManagerDto updatedManager = managerService.updateManagerById(id, managerDto);
        return ResponseEntity.ok(updatedManager);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ManagerDto> updateManagerByIdPatch(@Valid
                                                             @PathVariable("id") Long id, @RequestBody ManagerDto managerDto) {
        return updateManagerById(id, managerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManagerById(@PathVariable Long id) {
        boolean deleted = managerService.deleteManagerById(id);
        if (deleted) {
            return ResponseEntity.ok("Менеджер c id: " + id + " удален успешно");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ManagerDtoForByCondition>> getManagersByStatus(@Valid @PathVariable("status") StatusType status) {
        List<ManagerDtoForByCondition> managers = managerService.getAllManagersWhereStatusTypeIs(status);
        return ResponseEntity.ok(managers);
    }

    @GetMapping("/createdAt")
    public ResponseEntity<List<ManagerDtoForByCondition>> getAllManagersAfterDate(@Valid
                                                                                  @RequestParam("date")
                                                                                  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTime) {
        List<ManagerDtoForByCondition> managers = managerService.getAllManagersCreatedAfterDate(dateTime.atStartOfDay());
        return ResponseEntity.ok(managers);
    }
}