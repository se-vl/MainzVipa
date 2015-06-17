import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Startup
{
    private static final int SIZE = 8;
    private static final int MINES = 10;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager
                            .getCrossPlatformLookAndFeelClassName());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                new MainzVipa(new Playfield(new RandomMinefield(SIZE, MINES)));
            }
        });
    }
}
