import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class MainzVipa
{
    private static final int SIZE = 8;
    private static final int MINES = 10;

    private static final Insets NO_MARGIN = new Insets(0, 0, 0, 0);
    private static final Dimension SQUARE = new Dimension(32, 32);
    private static final Color NORM_COLOR = new ColorUIResource(238, 238, 238);
    private static final Color OPEN_COLOR = new Color(200, 200, 255);

    private boolean[][] _minefield;
    private int _countdown;
    private JFrame _frame;
    private JButton[][] _buttons;

    public MainzVipa()
    {
        _minefield = new boolean[SIZE][SIZE];
        Random rng = new Random();
        for (int i = 0; i < MINES; ++i)
        {
            int y;
            int x;
            do
            {
                y = rng.nextInt(SIZE);
                x = rng.nextInt(SIZE);
            }
            while (_minefield[y][x]);
            _minefield[y][x] = true;
        }
        _countdown = SIZE * SIZE - MINES;
        _frame = new JFrame("MainzVipa");
        _frame.setLayout(new GridLayout(SIZE, SIZE));
        _buttons = new JButton[SIZE][SIZE];
        for (int y = 0; y < SIZE; ++y)
        {
            final int Y = y;
            for (int x = 0; x < SIZE; ++x)
            {
                final int X = x;
                JButton button = new JButton();
                button.setBackground(NORM_COLOR);
                button.setMargin(NO_MARGIN);
                button.setPreferredSize(SQUARE);
                button.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseReleased(MouseEvent event)
                    {
                        if (SwingUtilities.isLeftMouseButton(event))
                        {
                            if (_minefield[Y][X])
                            {
                                for (int y = 0; y < SIZE; ++y)
                                {
                                    for (int x = 0; x < SIZE; ++x)
                                    {
                                        if (_minefield[y][x])
                                        {
                                            _buttons[y][x].setText("\u0489");
                                        }
                                    }
                                }
                                JOptionPane.showMessageDialog(null,
                                        "You suck!", "Game Over",
                                        JOptionPane.ERROR_MESSAGE);
                                System.exit(0);
                            }
                            else
                            {
                                clear(Y, X);
                            }
                        }
                    }
                });
                _buttons[y][x] = button;
                _frame.add(button);
            }
        }
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setBounds(32, 0, 1, 1);
        _frame.pack();
        _frame.setResizable(false);
        _frame.setVisible(true);
    }

    private void clear(int y, int x)
    {
        if (y >= 0 && y < SIZE && x >= 0 && x < SIZE)
        {
            JButton button = _buttons[y][x];
            if (button.getText().isEmpty())
            {
                for (MouseListener listener : button.getMouseListeners())
                {
                    button.removeMouseListener(listener);
                }
                int n = 0;
                if (isMineAt(y - 1, x - 1)) ++n;
                if (isMineAt(y - 1, x)) ++n;
                if (isMineAt(y - 1, x + 1)) ++n;
                if (isMineAt(y, x - 1)) ++n;
                if (isMineAt(y, x + 1)) ++n;
                if (isMineAt(y + 1, x - 1)) ++n;
                if (isMineAt(y + 1, x)) ++n;
                if (isMineAt(y + 1, x + 1)) ++n;
                if (n > 0)
                {
                    button.setText(String.valueOf(n));
                }
                else
                {
                    button.setText(" ");
                    button.setBackground(OPEN_COLOR);
                    clear(y - 1, x);
                    clear(y, x - 1);
                    clear(y, x + 1);
                    clear(y + 1, x);
                }
                --_countdown;
                if (_countdown == 0)
                {
                    JOptionPane.showMessageDialog(null, "You did it!",
                            "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private boolean isMineAt(int y, int x)
    {
        return y >= 0 && y < SIZE && x >= 0 && x < SIZE && _minefield[y][x];
    }

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
                new MainzVipa();
            }
        });
    }
}
