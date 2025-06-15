uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;
uniform float GameTime;
uniform vec3 heartPos;
uniform float progress;

in vec2 texCoord;
out vec4 fragColor;

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
    vec2 resolution = vec2(textureSize(DiffuseSampler0, 0));

    vec4 orangeTintedColor = baseColor + vec4(0.839, 0.180, 0.0, 0.0) * max(1.0 - progress * 16.0, 0.0);

    int bars = 32;
    float barCount = mix(2.0, float(bars), smoothstep(0.0, 0.85, progress));
    float redMask = 0.0;
    float darkenMask = 1.0;

    float y = texCoord.y;
    float x = texCoord.x;
    float timeSeed = floor(GameTime * 24000.0);

    float line = float(floor(texCoord.y * 240.0 / 2.0));
    float rnd = rand(line * 12.9898 + floor(GameTime * 48000.0));
    float mask = step(max(1.0 - progress, 0.2), rnd);
    float triangle = 1.0 - abs(texCoord.x * 2.0 - 1.0);
    float shift = (rnd - 0.5) * 2.0 * 0.02 * triangle * mask * progress;
    float shiftedX = texCoord.x + shift;

    for (int i = 0; i < bars; i++) {
        if (float(i) >= barCount) break;

        float id = float(i);
        float randY = rand(id + timeSeed);
        float randOn = rand(id + 100.0 + timeSeed);
        float height = 0.15 + rand(id + 200.0 + timeSeed) * 0.1;

        if (i > barCount - 3) {
            height = 0.25;
        }

        bool isInBar = bool(y > randY && y < randY + height);
        bool isBarVisible = bool(randOn > 0.7 || progress > 0.85);

        if (isBarVisible && isInBar && progress > 0.05) {
            redMask = 0.1;
            darkenMask = 1.0;

            if (i > barCount - 3) {
                float randCubeId = rand(id + 300.0 + timeSeed);

                if (randCubeId < 0.25) {
                    if (shiftedX < 0.25) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                } else if (randCubeId < 0.5) {
                    if (shiftedX >= 0.25 && shiftedX < 0.5) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                } else if (randCubeId < 0.75) {
                    if (shiftedX >= 0.5 && shiftedX < 0.75) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                } else {
                    if (shiftedX >= 0.75) {
                        redMask = 0.4;
                        darkenMask = 0.8;
                    }
                }
            }
        }
    }

    vec4 finalColor;
    if (redMask > 0.0) {
        float aberrationIntensity = redMask * 0.02;
        vec4 aberratedColor = chromaticAberration(DiffuseSampler0, texCoord + vec2(shift, 0.0), aberrationIntensity);

        vec4 tintedAberrated = aberratedColor + vec4(0.839, 0.180, 0.0, 0.0) * max(1.0 - progress * 16.0, 0.0);
        finalColor = (tintedAberrated + vec4(redMask, 0.0, 0.0, 0.0)) * darkenMask;
    } else {
        finalColor = (orangeTintedColor + vec4(redMask, 0.0, 0.0, 0.0)) * darkenMask;
    }

    fragColor = finalColor;
}