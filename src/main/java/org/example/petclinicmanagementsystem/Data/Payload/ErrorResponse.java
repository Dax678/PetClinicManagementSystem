package org.example.petclinicmanagementsystem.Data.Payload;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
}
