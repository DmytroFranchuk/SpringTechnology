package de.telran.SpringTechnologyBankApp.mappers.usersapp;

import de.telran.SpringTechnologyBankApp.dtos.usersapp.RoleUserApplicationDto;
import de.telran.SpringTechnologyBankApp.entities.usersapp.RoleUserApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleUserApplicationMapper {

    @Mapping(target = "roleType", source = "roleType")
    RoleUserApplicationDto RoleUserApplicationToDto(RoleUserApplication entity);

    @Mapping(target = "users", ignore = true)
    @Mapping(target = "id", ignore = true)
    RoleUserApplication RoleUserApplicationDtoToEntity(RoleUserApplicationDto dto);

}
