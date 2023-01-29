package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для {@link UserEntity} и {@link UserDto}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * @param user {@link UserDto}.
     * @return {@link UserEntity}.
     */
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserDto user);

    /**
     * @param user {@link UserEntity}.
     * @return {@link UserDto}.
     */
    UserDto toDto(UserEntity user);

    /**
     * @param userDto {@link UserDto}.
     * @param user {@link UserEntity}.
     * @return {@link UserEntity}.
     */
    @Mapping(target = "id", ignore = true)
    UserEntity mergeToEntity(UserDto userDto, @MappingTarget UserEntity user);

    /**
     * @param users {@link List<UserEntity>}.
     * @return {@link List<UserDto>}.
     */
    List<UserDto> toDtoList(List<UserEntity> users);
}
