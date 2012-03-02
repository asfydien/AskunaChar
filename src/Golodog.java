
import javax.microedition.lcdui.*;

public class Golodog implements CommandListener{
    Askuna midlet;
    
    private Display display;
    
    private String menu[] = {"Latin ka Sunda", "Serat Aksara Sunda", "Quiz", "Help", "About"};
    private Image[] icon = {null, null, null, null, null};
    
    List lsMenu = new List("AskunaChar v0.1", List.IMPLICIT, menu, null);
    
    private Command cmdBack = new Command("Back", Command.BACK, 1);
    private Command cmdExit = new Command("Exit", Command.EXIT, 1);
    
    public Golodog (Askuna midlet){
        this.midlet = midlet;
        this.display = Display.getDisplay(midlet);
        
        try{
            icon[0] = Image.createImage("/img/convert.png");
            icon[1] = Image.createImage("/img/write.png");
            icon[2] = Image.createImage("/img/quiz.png");
            icon[3] = Image.createImage("/img/help.png");
            icon[4] = Image.createImage("/img/about.png");

            lsMenu  = new List("AskunaChar v0.1", List.IMPLICIT, menu, icon);
        } catch(Exception ex) { }
        
        lsMenu.addCommand(cmdExit);
        lsMenu.setCommandListener(this);
        
        display.setCurrent(lsMenu);
    }
    
    protected void keyPressed(int keyCode) {

    }
    
    public void commandAction(Command c, Displayable d) {
        if (c.getCommandType() == Command.SCREEN) {
            switch (lsMenu.getSelectedIndex()){
                case 0:
                    display.setCurrent(new KanvasConv(midlet));
                    break;
                case 1:
                    display.setCurrent(new KanvasBuild(midlet));
                    break;
                case 2:
                    Alert al = new Alert("Punten ...", "Teu acan sayogi!", null, AlertType.CONFIRMATION);
                    display.setCurrent(al);
                    break;
                case 3:
                    showForm("Pitulung", "Konversi: Tekan tombol 'Input', masukan kalimat lalu tekan tombol 'Convert'" +
                             "\n\nTulis Sunda: Gunakan panah untuk navigasi, tekan 'Select' untuk memilih aksara dan kombinasi rarangkén, lalu pilih 'Apply'", cmdBack, null);
                    break;
                case 4:
                    showForm("Ihwal","AskunaChar v0.1\n© 2012 Sofyan\n\nUrl:\nhttp://code.google.com/p/askuna/\n\nImage by Fugue Icons", cmdBack, null);
                    break;
            }
        }
        
        if (c == cmdBack) display.setCurrent(lsMenu);

        if (c == cmdExit) midlet.exitMIDlet();
        
    }
    
    private void showForm(String j, String s, Command c1, Command c2){
        Form f = new Form(j);
        
        f.append(new StringItem("", s));
        f.addCommand(c1);
        if (c2!=null) f.addCommand(c2);
        f.setCommandListener(this);
        
        display.setCurrent(f);
    }
    
}
