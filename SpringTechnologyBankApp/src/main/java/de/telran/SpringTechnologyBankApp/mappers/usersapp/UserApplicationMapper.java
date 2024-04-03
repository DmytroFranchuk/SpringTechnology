package de.telran.SpringTechnologyBankApp.mappers.usersapp;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.UserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.enums.RoleType;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import de.telran.SpringTechnologyBankApp.entities.usersapp.UserApplication;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserApplicationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "userHistories", ignore = true)
    UserApplication dtoToUserApplication(UserApplicationDto dto);

    default RoleType map(Set<RoleUserApplication> roles) {
        if (roles.size() == 1) {
            return roles.iterator().next().getRoleType();
        } else {
            return roles.stream()
                    .map(RoleUserApplication::getRoleType)
                    .min(Comparator.comparing(RoleType::ordinal))
                    .orElse(null);
        }
    }

    @Mapping(target = "role", source = "roles")
    UserApplicationDto userApplicationToDto(UserApplication user);
}
