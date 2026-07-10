#version 330 core

#define MAX_LIGHTS 16


struct Light {
    vec3 position;
    vec3 colour;
    float intensity;
};

struct Material {
    vec3 diffuse;
    vec3 specular;
    float shininess;
};


uniform Light lights[MAX_LIGHTS];
uniform Material material;

uniform vec3 viewPosition;

in vec3 FragPosition;
in vec3 Normal;

out vec4 FragColour;

void main()
{
    vec3 N = normalize(Normal);
    vec3 result = material.diffuse * 0.1;


    for (int i = 0; i < MAX_LIGHTS; i ++) {

        vec3 L = normalize(lights[i].position - FragPosition);


        // diffuse
        float diffuseFactor = max(dot(N, L), 0.0);
        vec3 diffuse = material.diffuse * lights[i].colour * diffuseFactor * lights[i].intensity;

        
        // specular
        vec3 V = normalize(viewPosition - FragPosition);
        vec3 R = reflect(-L, N);
        vec3 specular = material.specular * lights[i].colour * pow(max(dot(V, R), 0.0), material.shininess) * lights[i].intensity;


        result += diffuse + specular;
    }
    
    
    FragColour = vec4(result, 1.0);
}