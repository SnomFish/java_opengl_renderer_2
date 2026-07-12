#version 330 core

in vec4 worldPosition;

uniform vec3 lightPosition;
uniform float farPlane;

void main()
{
    float lightDistance = length(worldPosition.xyz - lightPosition);

    lightDistance = lightDistance / farPlane;

    gl_FragDepth = lightDistance;
}