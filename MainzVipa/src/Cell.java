public enum Cell
{
    OPEN, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, UNKNOWN, BOMB;

    public static Cell fromInt(int n)
    {
        return values()[n];
    }

    @Override
    public String toString()
    {
        switch (this)
        {
        case OPEN:
        case UNKNOWN:
            return "";

        case ONE:
            return "1";
        case TWO:
            return "2";
        case THREE:
            return "3";
        case FOUR:
            return "4";
        case FIVE:
            return "5";
        case SIX:
            return "6";
        case SEVEN:
            return "7";
        case EIGHT:
            return "8";

        case BOMB:
            return "\u0489";

        default:
            throw new AssertionError("exhaustive switch");
        }
    }
}
