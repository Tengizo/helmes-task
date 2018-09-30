package com.helmes.task.rest.dto;

import com.helmes.task.persistence.model.Sector;
import com.helmes.task.persistence.model.User;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserUpdateDTO {
    private Long id;
    @Size(min = 3, max = 20, message = "Name length should be from 3 to 20 characters")
    private String name;
    private List<Long> sectorIds;

    private Boolean agreeToTerms;
    public User getUpdatedUser(User user) {
        if (name != null) {
            user.setName(name);
        }
        if (!CollectionUtils.isEmpty(sectorIds)) {
            user.setSectors(sectorIds.stream().map(Sector::new).collect(Collectors.toList()));
        }
        return user;
    }
}
