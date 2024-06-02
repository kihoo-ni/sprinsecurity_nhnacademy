package com.nhnacademy.springsecurityhomework.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @Min(1)
    private Integer age;

    @JsonSerialize(using = ToStringSerializer.class)
    private Role role;


}
