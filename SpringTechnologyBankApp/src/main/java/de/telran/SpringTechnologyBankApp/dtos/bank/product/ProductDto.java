package de.telran.SpringTechnologyBankApp.dtos.bank.product;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import de.telran.SpringTechnologyBankApp.entities.enums.CurrencyCode;
import de.telran.SpringTechnologyBankApp.entities.enums.ProductType;
import de.telran.SpringTechnologyBankApp.entities.enums.StatusType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MapKeyEnumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.websocket.OnMessage;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.mapstruct.EnumMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static de.telran.SpringTechnologyBankApp.entities.enums.StatusType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name", "statusType", "limitSum"})
@ToString(of = {"name", "statusType", "limitSum"})
public class ProductDto {
    private Long id;
    private String name;

//    @NotBlank(message = "значение не может быть пустым или содержать только пробелы")
    private StatusType statusType;
//    @NotBlank(message = "значение не может быть пустым или содержать только пробелы")
    private ProductType productType;
//    @NotBlank(message = "значение не может быть пустым или содержать только пробелы")
    private CurrencyCode currencyCode;

    private BigDecimal interestRate;
    @Positive(message = "должно быть положительным")
    private BigDecimal limitSum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Positive(message = "должно быть положительным")
    @NotNull(message = "не должно быть null")
//    @NotFound
    private Long managerId;
    private Set<AgreementForProductDto> agreements = new HashSet<>();
}