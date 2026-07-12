#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 uv;


uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;


out vec3 FragPosition;
out vec3 Normal;


void main()
{
    vec4 worldPosition = model * vec4(position, 1.0);

    FragPosition = worldPosition.xyz;

    Normal = mat3(transpose(inverse(model))) * normal;

    gl_Position =
        projection *
        view *
        worldPosition;
}