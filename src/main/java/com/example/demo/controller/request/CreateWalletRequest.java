package com.example.demo.controller.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.processing.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWalletRequest {


    @NotNull(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$",
            message = "Phone number must be formatted as (XXX)-XXX-XXXX")
    private String phoneNumber;

}
