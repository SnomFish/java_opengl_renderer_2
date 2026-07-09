#version 330 core

in vec3 FragPosition;
in vec3 Normal;

uniform vec3 lightPosition;
uniform vec3 lightColour;
uniform vec3 objectColour;

out vec4 FragColour;

void main()
{
    vec3 N = normalize(Normal);
    vec3 L = normalize(lightPosition - FragPosition);

    float intensity = max(dot(N, L), 0.0);

    vec3 diffuse = objectColour * lightColour * intensity;

    vec3 ambient = objectColour * 0.1;
    FragColour = vec4(diffuse + ambient, 1.0);
}