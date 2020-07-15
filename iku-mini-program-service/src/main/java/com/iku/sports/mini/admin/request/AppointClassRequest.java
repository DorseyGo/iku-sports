package com.iku.sports.mini.admin.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AppointClassRequest {
    @NotNull
    @NotEmpty
    private String userId;
    @NotNull
    private Integer arrangeClassId;
}
