#version 330 core

in vec3 FragPosition;
in vec3 Normal;

uniform vec3 lightPosition;
uniform vec3 lightColour;
uniform float lightIntensity;
uniform vec3 viewPosition;
uniform vec3 materialDiffuse;
uniform vec3 materialSpecular;
uniform float materialShininess;

out vec4 FragColour;

void main()
{
    vec3 N = normalize(Normal);
    vec3 L = normalize(lightPosition - FragPosition);


    // ambient
    vec3 ambient = materialDiffuse * 0.1;


    // diffuse
    float intensity = max(dot(N, L), 0.0);
    vec3 diffuse = materialDiffuse * lightColour * intensity * lightIntensity;

    
    // specular
    vec3 V = normalize(viewPosition - FragPosition);
    vec3 R = reflect(-L, N);
    vec3 specular = materialSpecular * lightColour * pow(max(dot(V, R), 0.0), materialShininess) * lightIntensity;


    vec3 result = ambient + diffuse + specular;
    FragColour = vec4(result, 1.0);
}