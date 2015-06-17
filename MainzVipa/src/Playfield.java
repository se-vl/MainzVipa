public class Playfield
{
    private final Minefield _minefield;
    private final Cell[][] _playfield;
    private int _cellsToClear;
    private GameState _gameState;

    public Playfield(Minefield minefield)
    {
        _minefield = minefield;
        int height = _minefield.height();
        int width = _minefield.width();
        _playfield = new Cell[height][width];
        for (Cell[] row : _playfield)
        {
            java.util.Arrays.fill(row, Cell.UNKNOWN);
        }
        _cellsToClear = height * width - _minefield.numberOfMines();
        _gameState = GameState.PLAY;
    }

    public Cell cellAt(int y, int x)
    {
        return _playfield[y][x];
    }

    public int height()
    {
        return _minefield.height();
    }

    public int width()
    {
        return _minefield.width();
    }

    public void clear(int y, int x)
    {
        if (_playfield[y][x] == Cell.UNKNOWN)
        {
            if (_minefield.isMineAt(y, x))
            {
                revealBombs();
                _gameState = GameState.LOSE;
            }
            else
            {
                explore(y, x);
                if (_cellsToClear == 0)
                {
                    _gameState = GameState.WIN;
                }
            }
        }
    }

    private void revealBombs()
    {
        for (int y = 0; y < height(); ++y)
        {
            for (int x = 0; x < width(); ++x)
            {
                if (_minefield.isMineAt(y, x))
                {
                    _playfield[y][x] = Cell.BOMB;
                }
            }
        }
    }

    private void explore(int y, int x)
    {
        int neighbors = _minefield.countNeighbors(y, x);
        _playfield[y][x] = Cell.fromInt(neighbors);
        --_cellsToClear;
        if (neighbors == 0)
        {
            tryExplore(y - 1, x);
            tryExplore(y, x - 1);
            tryExplore(y, x + 1);
            tryExplore(y + 1, x);
        }
    }

    private void tryExplore(int y, int x)
    {
        if (_minefield.isOnField(y, x) && _playfield[y][x] == Cell.UNKNOWN)
        {
            explore(y, x);
        }
    }

    public GameState gameState()
    {
        return _gameState;
    }
}
