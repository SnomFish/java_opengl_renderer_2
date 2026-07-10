#version 330 core

#define MAX_LIGHTS 16


struct Light {
    vec4 position;
    vec4 colour;
    float intensity;
};


layout(std140) uniform LightBuffer {
    int lightCount;
    Light lights[MAX_LIGHTS];
};


uniform vec3 viewPosition;
uniform vec3 materialDiffuse;
uniform vec3 materialSpecular;
uniform float materialShininess;

in vec3 FragPosition;
in vec3 Normal;

out vec4 FragColour;

void main()
{
    vec3 N = normalize(Normal);
    vec3 result = materialDiffuse * 0.1;


    for (int i = 0; i < lightCount; i ++) {

        vec3 L = normalize(lights[i].position - FragPosition);


        // diffuse
        float diffuseFactor = max(dot(N, L), 0.0);
        vec3 diffuse = materialDiffuse * lights[i].colour * diffuseFactor * lights[i].intensity;

        
        // specular
        vec3 V = normalize(viewPosition - FragPosition);
        vec3 R = reflect(-L, N);
        vec3 specular = materialSpecular * lights[i].colour * pow(max(dot(V, R), 0.0), materialShininess) * lights[i].intensity;


        result += diffuse + specular;
    }
    
    
    FragColour = vec4(result, 1.0);
}