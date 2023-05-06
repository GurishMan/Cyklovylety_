import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends JFrame {
    private JTextField txtField;
    private JButton smazButton;
    private JTextArea txtArea;
    private JPanel panel;

    private List<Cyklovylety> list = new ArrayList<>();

    private int i = 1;

    private static final String SPLITTER = ",";

    private final JFileChooser jFileChooser = new JFileChooser(".");

    public static void main(String[] args) { new Main();}

    public Main(){
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        JMenu jMenu = new JMenu("Soubor");
        JMenuItem load = new JMenuItem("Načti");
        load.addActionListener(e -> nactiData());
        JMenuItem refresh = new JMenuItem("Refresh");
        refresh.addActionListener(e -> refresh());
        smazButton.addActionListener(e -> smaz());
        jMenu.add(refresh);
        jMenu.add(load);
        jMenuBar.add(jMenu);

        setVisible(true);
        setContentPane(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
    }

    public List<Cyklovylety> nactiData() {
        refresh();
        int result = jFileChooser.showOpenDialog(this);
        if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("Nesprávná možnost..");
            return null;
        }
        return scan(jFileChooser.getSelectedFile());
    }

    private List<Cyklovylety> scan(File file){
        List<Cyklovylety> list = new ArrayList<>();
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))){
            while(scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(SPLITTER);
                int cisla = Integer.parseInt(data[1]);
                LocalDate ld = LocalDate.now();
                list.add(new Cyklovylety(data[0], cisla, ld));
                txtArea.append((i++ +") " + data[0] + " (" + cisla + " km) \n"));
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Soubor nelze načíst");
        }
        return list;
    }

    private void smaz(){
        try{
            int lineNumbers = txtArea.getLineCount();
            int lineNumber = Integer.parseInt(txtField.getText());

            if (lineNumbers <= lineNumber){
                JOptionPane.showMessageDialog(null, "Řádek s číslem " + txtField.getText() + " neexistuje!");
            }
            list.remove(txtField.getText());
            smazRadek(txtArea, lineNumber);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Napište číslo řádku který chcete odstranit");
        }
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(jFileChooser.getSelectedFile())))){
            list.forEach(cyklovýlet -> {
                writer.println(cyklovýlet.toString());
            });
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Soubor neobsahuje žádný řádek " + txtField.getText());
        }
    }
    private void smazRadek(JTextArea textArea1, int lineNumber) {
        String texts = textArea1.getText();
        String[] lines = texts.split("\n");

        if (lineNumber >= 1 && lineNumber <= lines.length) {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < lines.length; i++) {
                if (i != lineNumber - 1) {
                    builder.append(lines[i]).append("\n");
                }
            }
            textArea1.setText(builder.toString());
        }
    }
    private void refresh(){
        txtArea.setText("");
        txtField.setText("");
    }
}

