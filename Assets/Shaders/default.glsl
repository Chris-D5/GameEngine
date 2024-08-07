#type vertex
#version 330 core

layout (location=0) in vec3	aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexture;

out vec4 fColor;
out vec2 fTexture;

uniform mat4 uProjection;
uniform mat4 uView;

void main() {
	fColor = aColor;
	fTexture = aTexture;
	gl_Position = uProjection * uView * vec4(aPos,1.0);
}	

#type fragment
#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec4 fColor;	
in vec2 fTexture;

out vec4 color;

void main() {
	color = texture(TEX_SAMPLER,fTexture);
}