uniform sampler2D DiffuseSampler0;
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

float rand(float x) {
    return fract(sin(x * 12.9898) * 43758.5453);
}

vec4 chromaticAberration(sampler2D tex, vec2 coords, float intensity) {
    vec2 redOffset = vec2(-intensity, 0.0);
    vec2 cyanOffset = vec2(intensity, 0.0);

    vec4 original = texture2D(tex, coords);

    float r = texture2D(tex, coords + redOffset).r;

    float g = texture2D(tex, coords + cyanOffset).g;
    float b = texture2D(tex, coords + cyanOffset).b;

    return vec4(r, g, b, original.a);
}

void main() {
    vec4 baseColor = texture(DiffuseSampler0, texCoord);
    vec2 resolution = textureSize(DiffuseSampler0, 0);

    vec4 orangeTintedColor = baseColor + vec4(0.839, 0.180, 0.0, 0.0) * max(1.0 - progress * 16.0, 0.0);

    int bars = 32;
    float barCount = mix(2.0, float(bars), smoothstep(0.0, 0.85, progress));
    float redMask = 0.0;
    float darkenMask = 1.0;

    float y = texCoord.y;
    float x = texCoord.x;
    float timeSeed = floor(GameTime * 24000.0);

    for (int i = 0; i < bars; i++) {
        if (float(i) >= barCount) break;

        float id = float(i);
        float randY = rand(id + timeSeed);
        float randOn = rand(id + 100.0 + timeSeed);
        float height = 0.15 + rand(id + 200.0 + timeSeed) * 0.1;

        if (i > barCount - 3) {
            height = 0.25;
        }

        bool isInBar = (y > randY && y < randY + height);
        bool isBarVisible = randOn > 0.7 || progress > 0.85;

        if (isBarVisible && isInBar && progress > 0.05) {
            redMask = 0.1;
            darkenMask = 1.0;

            if (i > barCount - 3) {
                float randCubeId = rand(id + 300.0 + timeSeed);

                if (randCubeId < 0.25) {
                    if (x < 0.25) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                } else if (randCubeId < 0.5) {
                    if (x >= 0.25 && x < 0.5) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                } else if (randCubeId < 0.75) {
                    if (x >= 0.5 && x < 0.75) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                } else {
                    if (x >= 0.75) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                }
            }
        }
    }


    vec4 finalColor;
    if (redMask > 0.0) {

        float aberrationIntensity = redMask * 0.02; // Adjust this value for more/less effect
        vec4 aberratedColor = chromaticAberration(DiffuseSampler0, texCoord + vec2(0, 0), aberrationIntensity);

        vec4 tintedAberrated = aberratedColor + vec4(0.839, 0.180, 0.0, 0.0) * max(1.0 - progress * 16.0, 0.0);
        finalColor = (tintedAberrated + vec4(redMask, 0.0, 0.0, 0.0)) * darkenMask;
    } else {
        // No aberration for non-red areas
        finalColor = (orangeTintedColor + vec4(redMask, 0.0, 0.0, 0.0)) * darkenMask;
    }

    fragColor = finalColor;
}