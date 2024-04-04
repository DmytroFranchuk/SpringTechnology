package de.telran.SpringTechnologyBankApp.mappers.bank;

import de.telran.SpringTechnologyBankApp.dtos.bank.manager.ClientForManagerDto;
import de.telran.SpringTechnologyBankApp.entities.bank.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
@Mapper(componentModel = "spring")
public interface ClientForManagerMapper {
    ClientForManagerMapper INSTANCE = Mappers.getMapper(ClientForManagerMapper.class);

    @Named("mapToClients")
    ClientForManagerDto clientToClientForManagerDto(Client client);

//    @Named("sortClientsById")
//    default Comparator<ClientForManagerDto> sortClientsById() {
//        return Comparator.comparing(ClientForManagerDto::getId);
//    }
}
