package domain;

import java.awt.Color;
import java.util.Random;

/**
 * a helper class to generate some neat color schemes, adapted from
 * http://devmag.org.za/2012/07/29/how-to-choose-colours-procedurally-algorithms/
 * @author rverbist
 */
public final class GoldenRatioColorGenerator
{
    private static Random _rng = new Random();
    private static float _ratio = (1 + (float)Math.sqrt(5)) / 2;
    
    private float _hue;
    private final float _saturation;
    private final float _brightness;
    
    /**
     * creates a new {@link GoldenRatioColorGenerator} instance with the given saturation and brightness
     * @param saturation saturation level
     * @param brightness brightness level
     */
    public GoldenRatioColorGenerator(final float saturation, final float brightness)
    {
        _hue = _rng.nextFloat();
        _saturation = saturation;
        _brightness = brightness;
    }
    
    /**
     * generate the next {@link Color} in the sequence
     * @return a {@link Color} object
     */
    public Color next()
    {
        _hue += _ratio;
        _hue %= 1;
        // this is awesome and you know it.
        return new Color(Color.HSBtoRGB(_hue, _saturation, _brightness));
    }
}
