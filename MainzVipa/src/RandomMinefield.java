import java.util.Random;

public class RandomMinefield implements Minefield
{
    private final boolean[][] _minefield;
    private final int _numberOfMines;

    public RandomMinefield(int height, int width, int numberOfMines)
    {
        _minefield = new boolean[height][width];
        _numberOfMines = numberOfMines;
        Random rng = new Random();
        for (int i = 0; i < numberOfMines; ++i)
        {
            int y;
            int x;
            do
            {
                y = rng.nextInt(height);
                x = rng.nextInt(width);
            }
            while (_minefield[y][x]);
            _minefield[y][x] = true;
        }
    }

    public RandomMinefield(int size, int numberOfMines)
    {
        this(size, size, numberOfMines);
    }

    @Override
    public int height()
    {
        return _minefield.length;
    }

    @Override
    public int width()
    {
        return _minefield[0].length;
    }

    @Override
    public int numberOfMines()
    {
        return _numberOfMines;
    }

    @Override
    public boolean isOnField(int y, int x)
    {
        return (x >= 0) && (x < width()) && (y >= 0) && (y < height());
    }

    @Override
    public boolean isMineAt(int y, int x)
    {
        return isOnField(y, x) && _minefield[y][x];
    }

    @Override
    public int countNeighbors(int y, int x)
    {
        int neighbors = 0;
        if (isMineAt(y - 1, x - 1)) ++neighbors;
        if (isMineAt(y - 1, x)) ++neighbors;
        if (isMineAt(y - 1, x + 1)) ++neighbors;
        if (isMineAt(y, x - 1)) ++neighbors;
        if (isMineAt(y, x + 1)) ++neighbors;
        if (isMineAt(y + 1, x - 1)) ++neighbors;
        if (isMineAt(y + 1, x)) ++neighbors;
        if (isMineAt(y + 1, x + 1)) ++neighbors;
        return neighbors;
    }
}
