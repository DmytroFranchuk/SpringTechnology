package de.telran.SpringTechnologyBankApp.controllers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.agreement.AgreementDto;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import de.telran.SpringTechnologyBankApp.services.bank.interf.AgreementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1/agreements")
@Tag(name = "Agreement Controller API")
@RequiredArgsConstructor
public class AgreementController {
    private final AgreementService agreementService;

    @PostMapping("/add")
    public ResponseEntity<AgreementDto> createAgreement(@Valid @RequestBody AgreementDto agreementDto) {
        AgreementDto createdAgreement = agreementService.createAgreement(agreementDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAgreement);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgreementDto> getAgreementById(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok(agreementService.getAgreementById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgreementDto> updateAgreementById(
            @Valid
            @PathVariable("id") Long id, @RequestBody AgreementDto agreementDto) {
        AgreementDto updatedAgreement = agreementService.updateAgreementById(id, agreementDto);
        return ResponseEntity.ok(updatedAgreement);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AgreementDto> updateAgreementByIdPatch(
            @Valid
            @PathVariable("id") Long id, @RequestBody AgreementDto agreementDto) {
        return updateAgreementById(id, agreementDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAgreementById(@Valid @PathVariable("id") Long id) {
        agreementService.deleteAgreementById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", String.format("Договор с id %d успешно удален", id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AgreementDto>> getAllAgreementsByStatus(
            @Valid
            @PathVariable(value = "status") StatusType status) {
        List<AgreementDto> agreements = agreementService.getAllAgreementsWhereStatusTypeIs(status);
        return ResponseEntity.ok(agreements);
    }

    @GetMapping("/products/{productType}")
    public ResponseEntity<List<AgreementDto>> getAgreementsByProductType(
            @Valid
            @PathVariable ProductType productType) {
        List<AgreementDto> agreements = agreementService.findAgreementsByProductType(productType);
        return ResponseEntity.ok(agreements);
    }




}
