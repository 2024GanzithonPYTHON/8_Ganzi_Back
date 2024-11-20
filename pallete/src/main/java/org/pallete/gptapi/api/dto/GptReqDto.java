package org.pallete.gptapi.api.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GptReqDto {
    private String model;
    private List<Message> messages;

    public GptReqDto(String model, String propmt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", propmt));
    }
}
