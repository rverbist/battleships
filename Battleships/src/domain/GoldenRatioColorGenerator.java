package domain;

import java.awt.Color;
import java.util.Random;

public final class GoldenRatioColorGenerator
{
    private static Random _rng = new Random();
    private static float _ratio = (1 + (float)Math.sqrt(5)) / 2;
    
    private float _hue;
    private final float _saturation;
    private final float _brightness;
    
    public GoldenRatioColorGenerator(final float saturation, final float brightness)
    {
        _hue = _rng.nextFloat();
        _saturation = saturation;
        _brightness = brightness;
    }
    
    public Color next()
    {
        _hue += _ratio;
        _hue %= 1;
        // this is awesome and you know it.
        return new Color(Color.HSBtoRGB(_hue, _saturation, _brightness));
    }
}
