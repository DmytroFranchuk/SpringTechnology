package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ClientForManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ManagerDto;
import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ProductForManagerDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import de.telran.SpringTechnologyBankApp.entities.bank.Manager;
import de.telran.SpringTechnologyBankApp.entities.bank.Product;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring",
        uses = {ProductForManagerMapper.class, ClientForManagerMapper.class}
)
public interface ManagerMapper {

    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    Manager managerDtoToManager(ManagerDto managerDto);

    @Mapping(target = "products", qualifiedByName = "mapToProducts")
    @Mapping(target = "clients", qualifiedByName = "mapToClients")
    ManagerDto managerToManagerDto(Manager manager);

    @Named("mapToProducts")
    default List<ProductForManagerDto> mapProducts(Set<Product> products) {
        return products.stream()
                .map(ProductForManagerMapper.INSTANCE::productToProductForManagerDto)
                .sorted(Comparator.comparing(ProductForManagerDto::getId))
                .collect(Collectors.toList());
    }

    @Named("mapToClients")
    default List<ClientForManagerDto> mapClients(Set<Client> clients) {
        return clients.stream()
                .map(ClientForManagerMapper.INSTANCE::clientToClientForManagerDto)
                .sorted(Comparator.comparing(ClientForManagerDto::getId))
                .collect(Collectors.toList());
    }
}

//    @Mapping(target = "products", qualifiedByName = "sortProductsById")
//    @Mapping(target = "clients", qualifiedByName = "sortClientsById")

//    ManagerDto managerToManagerDto(Manager manager);
//    @IterableMapping(qualifiedByName = "mapToProducts")
//    default List<ProductForManagerDto> sortProductsById(Set<Product> products, @Context ProductForManagerMapper productForManagerMapper) {
//        return products.stream()
//                .map(productForManagerMapper::productToProductForManagerDto)
//                .sorted(Comparator.comparing(ProductForManagerDto::getId))
//                .collect(Collectors.toList());
//    }
//
//    @IterableMapping(qualifiedByName = "mapToClients")
//    default List<ClientForManagerDto> sortClientsById(Set<Client> clients, @Context ClientForManagerMapper clientForManagerMapper) {
//        return clients.stream()
//                .map(clientForManagerMapper::clientToClientForManagerDto)
//                .sorted(Comparator.comparing(ClientForManagerDto::getId))
//                .collect(Collectors.toList());
//    }
//
//    @Named("mapToProducts")
//    List<ProductForManagerDto> mapToProducts(Set<Product> products);
//
//    @Named("mapToClients")

//    List<ClientForManagerDto> mapToClients(Set<Client> clients);


//    @IterableMapping(qualifiedByName = "mapToProducts")
//    @IterableMapping(
//            qualifiedByName = "mapToProducts",
//            elementTargetType = ProductForManagerDto.class,

//            qualifiedBy = {})

//    @IterableMapping(qualifiedByName = "mapToClients")
//    @IterableMapping(
//            qualifiedByName = "mapToClients",
//            elementTargetType = ClientForManagerDto.class,
//            qualifiedBy = {})

//    @Named("mapToProducts")
//    default List<ProductForManagerDto> mapProducts(Set<Product> products) {
//        return products.stream()
//                .map(ProductForManagerMapper.INSTANCE::productToProductForManagerDto)
//                .sorted(Comparator.comparing(ProductForManagerDto::getId))
//                .collect(Collectors.toList());
//    }

//    @Named("mapToClients")
//    default List<ClientForManagerDto> mapClients(Set<Client> clients) {
//        return clients.stream()
//                .map(ClientForManagerMapper.INSTANCE::clientToClientForManagerDto)
//                .sorted(Comparator.comparing(ClientForManagerDto::getId))
//                .collect(Collectors.toList());
//    }


//    @IterableMapping(qualifiedByName = "mapToClients")
//    List<ClientForManagerDto> mapToClients(Set<Client> clients);
//
//    @IterableMapping(qualifiedByName = "mapToProducts")
//    List<ProductForManagerDto> mapToProducts(Set<Product> products);

//    @Named("mapToProducts")
//    default List<ProductForManagerDto> mapToProducts(Set<Product> products) {
//        return products.stream()
//                .map(ProductForManagerMapper.INSTANCE::productToProductForManagerDto)
//                .sorted(Comparator.comparing(ProductForManagerDto::getId))
//                .collect(Collectors.toList());
//    }

//    @IterableMapping(qualifiedByName = "mapToProducts")

//@Mapping(target = "clients", expression = "java(null)")
//@Mapping(target = "products", expression = "java(null)")


//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "roleType", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "clients", ignore = true)
//    @Mapping(target = "products", ignore = true)


//    @Mapping(target = "login", ignore = true)
//    @Mapping(target = "password", ignore = true)


//    @Mapping(target = "id", source ="id")
//    @Mapping(target = "firstName", source ="")
//    @Mapping(target = "lastName", source ="")
//    @Mapping(target = "login", source ="")
//    @Mapping(target = "password", source ="")
//    @Mapping(target = "email", source ="")
//    @Mapping(target = "description", source ="")
//    @Mapping(target = "statusType", source ="")
//    @Mapping(target = "roleType", source ="")
//    @Mapping(target = "createdAt", source ="")
//    @Mapping(target = "updatedAt", source ="")
//    @Mapping(target = "clients", source ="")
//    @Mapping(target = "products", source ="")
