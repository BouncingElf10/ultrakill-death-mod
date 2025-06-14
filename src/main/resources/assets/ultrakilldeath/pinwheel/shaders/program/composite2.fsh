uniform sampler2D Composite1ColorBuffer;
uniform sampler2D DiffuseSampler1;
uniform sampler2D DiffuseDepthSampler;
uniform float GameTime;
uniform vec3 heartPos;
uniform float progress;

in vec2 texCoord;
out vec4 fragColor;


vec4 sharpen(sampler2D tex, vec2 coords, vec2 renderSize, float intensity) {
    float dx = 1.0 / renderSize.x;
    float dy = 1.0 / renderSize.y;
    vec4 original = texture2D(tex, coords);
    vec4 sum = vec4(0.0);
    sum += -1.0 * texture2D(tex, coords + vec2(-dx,  0.0));
    sum += -1.0 * texture2D(tex, coords + vec2( 0.0, -dy));
    sum +=  5.0 * texture2D(tex, coords + vec2( 0.0,  0.0));
    sum += -1.0 * texture2D(tex, coords + vec2( 0.0,  dy));
    sum += -1.0 * texture2D(tex, coords + vec2( dx,  0.0));
    return mix(original, sum, intensity);
}

void main() {
    vec4 orginalColor = texture(DiffuseSampler1, texCoord);
    vec4 baseColor = texture(Composite1ColorBuffer, texCoord);
    vec2 editedCoords = texCoord;

    vec2 resolution = textureSize(Composite1ColorBuffer, 0);
    vec4 sharpendColor = sharpen(Composite1ColorBuffer, editedCoords, resolution / 1.0 + round(progress), pow(progress, 2.0) * 8.0);

    fragColor = sharpendColor;
}