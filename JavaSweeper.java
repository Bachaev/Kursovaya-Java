import java.awt.BorderLayout;
import java.awt.Dimension;
import sweeper.Box; //импортим наш класс, чтобы работал цикл, перебирающий картинки
import sweeper.Coord;
import sweeper.Game;
import sweeper.Level;
import sweeper.Ranges;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class JavaSweeper extends JFrame { //главный класс

    private Game game;//объявляем переменную Гейм
    private Level level;
    private JPanel panel; //объявляем переменную класса панель
    private JLabel label; //метка для сообщения о состоянии игры
    private JFrame frame1;
    private int COLS=8; //сколько у нас будет столбцов
    private int ROWS=8; //сколько будет строк
    private int BOMBS=8; //сколько будет бомб
    private final int IMAGE_SIZE = 50; //размер картинки


    public static void main(String[] args) {
        new JavaSweeper(); //создаем класс джава свипер
    }

    private JavaSweeper() { //конструктор класса
        game = new Game(COLS, ROWS, BOMBS); //инициализируем фасадный класс гейм
        game.start(); //запускаем игру
        setImages(); //подгружаем все картинки
        initPanel(); //инициализируем панель
        initButton();// вызываем кнопку
        initLabel(); //вызываем метку
        initFrame(); //инициалиризуем фрейм
        Difficulty();
    }

    private void initLabel() { //метод для добавления метки на экран
        label = new JLabel("Давайте начнем!"); //создаем метку
        label.setSize(400,50);
        add(label,BorderLayout.BEFORE_FIRST_LINE);
    }
    private void initButton() {
        JPanel myBottomPanel = new JPanel();
        JButton resetButton = new JButton("Заново");
        JButton table = new JButton("Статистика");
        myBottomPanel.add(table);
        myBottomPanel.add(resetButton);
        add(myBottomPanel, BorderLayout.SOUTH);
        resetButton.addMouseListener(new MouseAdapter() { //добавляем слушатель мышки (адаптер мышки)
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.start();
                    panel.repaint();
                    label.setText(getMessage());
                }
            }
        });
        table.addMouseListener(new MouseAdapter() { //добавляем слушатель мышки (адаптер мышки)
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.initTable();
                }
            }
        });
    }
    private void Difficulty(){
        JPanel BottomPanel2 = new JPanel();
        JButton Button1 = new JButton("easy");
        JButton Button2 = new JButton("medium");
        JButton Button3 = new JButton("hard");
        JLabel lb = new JLabel("Cложность:");
        BottomPanel2.add(lb);
        BottomPanel2.add(Button1);
        BottomPanel2.add(Button2);
        BottomPanel2.add(Button3);
        add(BottomPanel2,BorderLayout.BEFORE_FIRST_LINE);
        Button1.addMouseListener(new MouseAdapter()
        { //добавляем слушатель мышки (адаптер мышки)
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    COLS = 8;
                    ROWS = 8;
                    BOMBS = 8;
                    game = new Game(COLS, ROWS, BOMBS); //инициализируем фасадный класс гейм
                    initFrame();
                    initPanel();
                    game.start();
                    label.repaint();
                    panel.repaint();
                    repaint();
                    pack();
                }
            }
        });
        Button2.addMouseListener(new MouseAdapter()
        {

            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    COLS = 10;
                    ROWS = 10;
                    BOMBS = 16;
                    game = new Game(COLS, ROWS, BOMBS); //инициализируем фасадный класс гейм
                    initFrame();
                    initPanel();
                    game.start();
                    label.repaint();
                    panel.repaint();
                    repaint();
                    pack();
                }
            }
        });
        Button3.addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    COLS = 12;
                    ROWS = 12;
                    BOMBS = 32;
                    game = new Game(COLS, ROWS, BOMBS); //инициализируем фасадный класс гейм
                    initFrame();
                    initPanel();
                    game.start();
                    label.repaint();
                    panel.repaint();
                    repaint();
                    pack();
                }
            }
        });
    }

    private void initPanel() {
        panel = new JPanel() { //инициализируем панель через анонимный класс
            @Override
            protected void paintComponent(Graphics g) { //переопределяем метод пейнткомпонент
                super.paintComponent(g); //вызываем его из родительского класса
                for (Coord coord :Ranges.getAllCoords()) {//с помощью цикла
                    g.drawImage((Image) game.getBox(coord).image, coord.x *IMAGE_SIZE, coord.y* IMAGE_SIZE, this); //задаем координаты для отрисовки, взяв их из фасадного класса с помощью переменной координат
                }
            }
        }; //конец анонимного класса

        panel.addMouseListener(new MouseAdapter() { //добавляем слушатель мышки (адаптер мышки)
            @Override
            public void mousePressed(MouseEvent e) { //переопределяем метод нажатия
                int x = e.getX() / IMAGE_SIZE; // если координату Х поделим на размер изображения, получим место, где мы находися
                int y = e.getY() / IMAGE_SIZE; // аналогично
                Coord coord = new Coord(x, y); // создаем переменную по полученным координатам
                if (e.getButton() == MouseEvent.BUTTON1) //проверка: если была нажата левая кнопка мыши
                    game.pressLeftButton (coord); //то на указанной координате вызываем специальный метод
                if (e.getButton() == MouseEvent.BUTTON3) //проверка: если была нажата правая кнопка мыши
                    game.pressRightButton (coord); //то на указанной координате вызываем специальный метод
                label.setText(getMessage()); //установим значение метки с помощью отдельного метода
                panel.repaint(); //обязательно перерисовываем панель
            }
        });

        panel.setPreferredSize(new Dimension(Ranges.getSize().x* IMAGE_SIZE, Ranges.getSize().y * IMAGE_SIZE));
        add(panel,BorderLayout.CENTER); //добавляем панель на фрейм
    }

    private String getMessage() { //метод, определяющий, какой текст отображать на метке
        switch (game.getState()){
            case PLAYED: return " Осталось бомб: " + game.getBombsRemain()+ "\n";
            case BOMBED: return " Игра завершена :( " +
                    "\n Время игры: "+game.getTime()+" c";
            case WINNER: return " \n Выигрыш!"+"Время игры: " +game.getTime()+ " c";
            default: return null;
        }
    }
    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //программа будет завершаться при закрытии окна
        setTitle("Kursovaya Sweeper"); //заголовок
        setResizable(false); //не даем разворачивать окно
        setVisible(true); //делаем окно видимым
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon")); //устанавливаем иконку окна
    }

    private void setImages() { //установка всех картинок на экране сразу
        for (Box box : Box.values()) { //цикл, перебирающий все картинки
            box.image = getImage(box.name().toLowerCase()); //для каждого экземпляра бокс устанавливаем картинку, передаем имя нашего элемента
        }
    }

    private Image getImage (String name) { //метод для нахождения картинок и их получения
        String filename = "img/" + name + ".png"; //задаем название файла
        ImageIcon icon = new ImageIcon (getClass().getResource(filename)); //присваиваем файл с изображением переменной айкон
// ImageIcon icon = new ImageIcon("res/img/bomb.png");
// ImageIcon icon = new ImageIcon(filename); //создаем изображение, найдя его по имени файла
        return icon.getImage(); //возвращаем переменную айкон и получаем картинку
    }
}