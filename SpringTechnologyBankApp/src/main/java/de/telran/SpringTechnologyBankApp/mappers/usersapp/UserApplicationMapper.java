package de.telran.SpringTechnologyBankApp.mappers.usersapp;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserApplicationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "userHistories", ignore = true)
    UserApplication dtoToUserApplication(UserApplicationDto dto);


    UserApplicationDto userApplicationToDto(UserApplication user);

}
