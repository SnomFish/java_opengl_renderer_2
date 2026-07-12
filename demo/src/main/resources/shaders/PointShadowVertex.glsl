#version 330 core

layout(location=0) in vec3 position;

uniform mat4 model;
uniform mat4 shadowMatrix;

out vec4 worldPosition;

void main()
{
    worldPosition = model * vec4(position,1.0);

    gl_Position = shadowMatrix * worldPosition;
}