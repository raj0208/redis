package com.raj.redispubsub.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Notification {
    String from;
    String to;
    String message;
}
