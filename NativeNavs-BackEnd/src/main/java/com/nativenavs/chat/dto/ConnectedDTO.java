package com.nativenavs.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectedDTO {
    private boolean oneUserConnected;
    private boolean twoUserConnected;
}
