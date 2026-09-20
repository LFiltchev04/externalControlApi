package com.dash.dashboard;

import jakarta.validation.constraints.NotBlank;


public record CreateEnvironment() {
    @NotBlank(message = "No generations specified")
    String[] GenerationHashes;
    
    @NotBlank(message = "No image name specified")
    String ImageName;

    @NotBlank(message = "No tag name specified")
    String TagName;
}
