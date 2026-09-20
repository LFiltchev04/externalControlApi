package com.dash.dashboard.reqSchemas;

import jakarta.validation.constraints.NotBlank;

public class CreateEnvironment {

    @NotBlank(message = "No image name specified")
    private String imageName;

    @NotBlank(message = "No tag name specified")
    private String tagName;
    
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
