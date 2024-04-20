import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class PaintFinal extends JFrame {
    JPanel colorPanel;
    JTextField redTextField;
    private int red;
    JTextField greenTextField;
    private int green;
    JTextField blueTextField;
    private int blue;
    JPanel widthPanel;
    JTextField widthTextField;
    private int widthInt;
    JButton fill;
    JPanel eraserPanel;
    private static boolean eraser;
    JButton reset;
    JPanel paintPanel;
    private Point lastPoint;
    private Graphics graphics;
    private Graphics2D graphics2D;
    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;
    public PaintFinal() {
        super("Paint");
        addComponents();
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void addColor() {
        colorPanel = new JPanel();
        colorPanel.setBackground(Color.white);
        JLabel redLabel = new JLabel("red:");
        redTextField = new JTextField(5);
        JLabel greenLabel = new JLabel("green:");
        greenTextField = new JTextField(5);
        JLabel blueLabel = new JLabel("blue:");
        blueTextField = new JTextField(5);
        colorPanel.add(redLabel);
        colorPanel.add(redTextField);
        colorPanel.add(greenLabel);
        colorPanel.add(greenTextField);
        colorPanel.add(blueLabel);
        colorPanel.add(blueTextField);
    }
    private void addWidth() {
        widthPanel = new JPanel();
        widthPanel.setBackground(Color.white);
        JLabel widthLabel = new JLabel("input width:");
        widthTextField = new JTextField(5);
        widthPanel.add(widthLabel);
        widthPanel.add(widthTextField);
    }
    private void addFill() {
        fill = new JButton("fill");
        fill.setBackground(Color.white);
        fill.addActionListener(e -> {
            if (colorPickedAndValid()) {
                paintPanel.setBackground(new Color(red, green, blue));
            }
        });
    }
    private void addEraser() {
        eraserPanel = new JPanel();
        eraserPanel.setBackground(Color.white);
        JLabel eraser = new JLabel("eraser:");
        JButton on = new JButton("on");
        on.setBackground(Color.white);
        on.addActionListener(e -> eraserOn());
        JButton off = new JButton("off");
        off.setBackground(Color.white);
        off.addActionListener(e -> eraserOff());
        eraserPanel.add(eraser);
        eraserPanel.add(on);
        eraserPanel.add(off);
    }
    private void addReset() {
        reset = new JButton();
        reset.setText("reset");
        reset.setBackground(Color.white);
        reset.addActionListener(e -> {
            paintPanel.repaint();
            paintPanel.setBackground(Color.white);
        });
    }
    private void addPaint() {
        paintPanel = new JPanel();
        paintPanel.setBackground(Color.white);
        paintPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastPoint = e.getPoint();
                graphics = paintPanel.getGraphics();
                graphics2D = (Graphics2D) graphics;
                setColorAndStroke();
                graphics2D.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());
            }
        });
        paintPanel.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                graphics2D.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());
                lastPoint = e.getPoint();
            }
        });
    }
    private void addComponents() {
        Container pane = getContentPane();
        addColor();
        addWidth();
        addFill();
        addEraser();
        addReset();
        addPaint();
        JPanel colorWidthFill = new JPanel();
        colorWidthFill.setBackground(Color.white);
        colorWidthFill.add(colorPanel);
        colorWidthFill.add(widthPanel);
        colorWidthFill.add(fill);
        JPanel eraseReset = new JPanel();
        eraseReset.setBackground(Color.white);
        eraseReset.add(eraserPanel);
        eraseReset.add(reset);
        pane.setLayout(new BorderLayout());
        pane.add(colorWidthFill, BorderLayout.PAGE_START);
        pane.add(paintPanel, BorderLayout.CENTER);
        pane.add(eraseReset, BorderLayout.PAGE_END);
    }
    public static void eraserOn() {
        eraser = true;
    }
    public static void eraserOff() {
        eraser = false;
    }
    public boolean colorPickedAndValid() {
        if (validString(redTextField.getText()) && validString(greenTextField.getText()) && validString(blueTextField.getText())) {
            red = Integer.parseInt(redTextField.getText());
            green = Integer.parseInt(greenTextField.getText());
            blue = Integer.parseInt(blueTextField.getText());
            return  red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255;
        }
        return false;
    }
    public boolean widthPickedAndValid() {
        if (validString(widthTextField.getText())) {
            widthInt = Integer.parseInt(widthTextField.getText());
            return widthInt > 0;
        }
        return false;
    }
    public void setColorAndStroke() {
        if (colorPickedAndValid()) {
            graphics.setColor(new Color(red, green, blue));
        }
        else {
            graphics.setColor(Color.black);
        }
        if (widthPickedAndValid()) {
            graphics2D.setStroke(new BasicStroke(widthInt, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        else {
            graphics2D.setStroke(new BasicStroke(1));
        }
        if (eraser) {
            graphics.setColor(new Color(paintPanel.getBackground().getRGB()));
        }
    }
    public boolean validString(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException exception) {
            return false;
        }
    }
    public static void main(String[] args) {
        new PaintFinal();
    }
}