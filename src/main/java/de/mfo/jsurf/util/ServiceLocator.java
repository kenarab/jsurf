package de.mfo.jsurf.util;

import de.mfo.jsurf.parser.ParserService;

public class ServiceLocator
{
    public static ParserService getParserService()
    {
	return new ParserService();
    }

    public static MathService getMathService()
    {
	return new JsMathService();
    }
}
