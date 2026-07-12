#version 330 core

#define MAX_LIGHTS 16


struct Light {

    vec3 position;
    vec3 colour;
    float intensity;
    float farPlane;
};


struct Material {

    vec3 diffuse;
    vec3 specular;
    float shininess;
};


uniform int lightCount;
uniform Light lights[MAX_LIGHTS];

uniform samplerCube shadowMaps[MAX_LIGHTS];

uniform Material material;

uniform vec3 viewPosition;


in vec3 FragPosition;
in vec3 Normal;


out vec4 FragColour;



float sampleShadowMap(int index, vec3 direction)
{
    float depth = 1.0;


    switch(index)
    {
        case 0:
            depth = texture(shadowMaps[0], direction).r;
            break;

        case 1:
            depth = texture(shadowMaps[1], direction).r;
            break;

        case 2:
            depth = texture(shadowMaps[2], direction).r;
            break;

        case 3:
            depth = texture(shadowMaps[3], direction).r;
            break;

        case 4:
            depth = texture(shadowMaps[4], direction).r;
            break;

        case 5:
            depth = texture(shadowMaps[5], direction).r;
            break;

        case 6:
            depth = texture(shadowMaps[6], direction).r;
            break;

        case 7:
            depth = texture(shadowMaps[7], direction).r;
            break;

        case 8:
            depth = texture(shadowMaps[8], direction).r;
            break;

        case 9:
            depth = texture(shadowMaps[9], direction).r;
            break;

        case 10:
            depth = texture(shadowMaps[10], direction).r;
            break;

        case 11:
            depth = texture(shadowMaps[11], direction).r;
            break;

        case 12:
            depth = texture(shadowMaps[12], direction).r;
            break;

        case 13:
            depth = texture(shadowMaps[13], direction).r;
            break;

        case 14:
            depth = texture(shadowMaps[14], direction).r;
            break;

        case 15:
            depth = texture(shadowMaps[15], direction).r;
            break;
    }


    return depth;
}



float calculateShadow(int index)
{
    vec3 lightDirection =
        FragPosition - lights[index].position;


    float currentDepth =
        length(lightDirection);


    float closestDepth =
        sampleShadowMap(
            index,
            lightDirection
        );


    closestDepth *= lights[index].farPlane;


    float bias = 0.05;


    if(currentDepth - bias > closestDepth)
        return 1.0;


    return 0.0;
}



void main()
{
    vec3 N = normalize(Normal);


    vec3 result =
        material.diffuse * 0.1;


    for(int i = 0; i < lightCount; i++)
    {

        vec3 L =
            normalize(
                lights[i].position - FragPosition
            );


        float shadow =
            calculateShadow(i);



        float diffuseFactor =
            max(dot(N,L),0.0);


        vec3 diffuse =
            material.diffuse
            * lights[i].colour
            * diffuseFactor
            * lights[i].intensity
            * (1.0-shadow);



        vec3 V =
            normalize(
                viewPosition - FragPosition
            );


        vec3 R =
            reflect(-L,N);


        vec3 specular =
            material.specular
            * lights[i].colour
            * pow(
                max(dot(V,R),0.0),
                material.shininess
            )
            * lights[i].intensity
            * (1.0-shadow);



        result += diffuse + specular;
    }


    FragColour =
        vec4(result,1.0);
}