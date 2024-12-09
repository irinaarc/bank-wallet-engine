package com.example.demo.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWalletRequest {


    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$",
            message = "Phone number must be formatted as (XXX)-XXX-XXXX")
    private String phoneNumber;

}
