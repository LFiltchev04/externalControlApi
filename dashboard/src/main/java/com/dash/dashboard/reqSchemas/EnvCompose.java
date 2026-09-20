package com.dash.dashboard.reqSchemas;
import jakarta.validation.constraints.NotBlank;

public class EnvCompose {
    @NotBlank(message = "No hashes specified")
    public String[] blobSet;
    
    @NotBlank(message = "No image name specified")
    public String manifestName;
    
    @NotBlank(message = "No generation tag specified")
    public int generationTag;

    @NotBlank(message = "No description specified")
    public String description;
}
