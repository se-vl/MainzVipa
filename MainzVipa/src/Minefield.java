public interface Minefield
{
    public int height();

    public int width();

    public int numberOfMines();

    public boolean isOnField(int y, int x);

    public boolean isMineAt(int y, int x);

    public int countNeighbors(int y, int x);
}
