import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ColorUIResource;

public class MainzVipa
{
    private static final Insets NO_MARGIN = new Insets(0, 0, 0, 0);
    private static final Dimension SQUARE = new Dimension(32, 32);
    private static final Color NORM_COLOR = new ColorUIResource(238, 238, 238);
    private static final Color OPEN_COLOR = new Color(200, 200, 255);

    private Playfield _playfield;
    private JFrame _frame;
    private JButton[][] _buttons;

    public MainzVipa(Playfield playfield)
    {
        _playfield = playfield;
        _frame = new JFrame("MainzVipa");
        int height = _playfield.height();
        int width = _playfield.width();
        _frame.setLayout(new GridLayout(height, width));
        _buttons = new JButton[height][width];
        initButtons();
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setBounds(32, 0, 1, 1);
        _frame.pack();
        _frame.setResizable(false);
        _frame.setVisible(true);
    }

    private void initButtons()
    {
        for (int y = 0; y < _buttons.length; ++y)
        {
            final int Y = y;
            for (int x = 0; x < _buttons[y].length; ++x)
            {
                final int X = x;
                JButton button = new JButton();
                button.setMargin(NO_MARGIN);
                button.setPreferredSize(SQUARE);
                button.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseReleased(MouseEvent event)
                    {
                        if (SwingUtilities.isLeftMouseButton(event))
                        {
                            _playfield.clear(Y, X);
                            updateButtons();
                            checkState();
                        }
                    }
                });
                _buttons[y][x] = button;
                _frame.add(button);
            }
        }
    }

    private void updateButtons()
    {
        int height = _playfield.height();
        int width = _playfield.width();
        for (int y = 0; y < height; ++y)
        {
            for (int x = 0; x < width; ++x)
            {
                Cell cell = _playfield.cellAt(y, x);
                _buttons[y][x].setText(cell.toString());
                Color color;
                switch (_playfield.cellAt(y, x))
                {
                case OPEN:
                    color = OPEN_COLOR;
                    break;
                default:
                    color = NORM_COLOR;
                    break;
                }
                _buttons[y][x].setBackground(color);
            }
        }
    }

    private void checkState()
    {
        switch (_playfield.gameState())
        {
        case PLAY:
            break;

        case WIN:
            JOptionPane.showMessageDialog(null, "You did it!",
                    "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        case LOSE:
            JOptionPane.showMessageDialog(null, "You suck!", "Game Over",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
