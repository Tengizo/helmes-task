package com.helmes.task.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helmes.task.persistence.model.Sector;
import com.helmes.task.persistence.model.User;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserAddDTO {
    @NotNull(message = "Name should't be empty")
    @Size(min = 3, max = 20, message = "Name length should be from 3 to 20 characters")
    private String name;

    @NotEmpty(message = "Sector should't be empty")
    @NotNull(message = "Sector should't be null")
    private List<Long> sectorIds;

    @NotNull
    @AssertTrue(message = "you should agree to terms")
    private Boolean agreeToTerms;

    @JsonIgnore
    public User getUser() {
        return User.builder()
                .name(name)
                .agreeToTerms(agreeToTerms)
                .sectors(
                        sectorIds.stream().map(Sector::new).collect(Collectors.toList())

                ).build();
    }
}
