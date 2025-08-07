package com.example.LibraryTracker.mapper;

import com.example.LibraryTracker.dto.user.CreateUserDto;
import com.example.LibraryTracker.dto.user.UserResponseDto;
import com.example.LibraryTracker.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface UserMapper {

    // Kayıt sırasında: DTO → Entity
    User toEntity(CreateUserDto dto);

    // Kullanıcıyı dışarı dönerken: Entity → DTO
    UserResponseDto toDto(User user);
}
